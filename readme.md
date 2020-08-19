## SQL拼接的例子

        /**
        	 * 动态查询
        	 * @param pageable
        	 * @param isSelect	是否确选（只要非空，都相当于加了条件）
        	 * @param titleLike	根据title模糊查询
        	 * @param teacherId 根据老师的id查询
        	 * @return
        	 */
        	Page<SubjectToTeacherVO> findAllSubjectToTeacherVO(Pageable pageable, Boolean isSelect, String titleLike,
        			String teacherId, String majorId, String studentId){
        		//条件组合
        		StringBuffer where = new StringBuffer(" where 1=1 ");
        		StringBuffer having = new StringBuffer();
        		if(isSelect != null){
        			if(isSelect)
        				having.append(" having max(se.isSelection)>0 ");
        			else
        				having.append(" having ((max(se.isSelection) is null) or max(se.isSelection)<=0) ");
        		}
        		if(!StringUtils.isEmpty(titleLike))
        			where.append(" and su.title like :titleLike");
        		if(!StringUtils.isEmpty(teacherId))
        			where.append(" and su.teacher.id=:teacherId");
        		if(!StringUtils.isEmpty(majorId))
        			where.append(" and su.major.id=:majorId");
        		if(!StringUtils.isEmpty(studentId)){
        			where.append(" and su.major.id=(select stu.major.id from Student stu where stu.id=:studentId)");
        		}
        		//主jpql 由于不能使用 if(exp1,rsul1,rsul2)只能用case when exp1 then rsul1 else rsul2 end
        		String jpql = "select new cn.edu.glut.vo.SubjectToTeacherVO(su.id, su.title, cast(count(se.id) as int) as guysNum, max(se.isSelection) as choose, "
        				+ " (select ss.nickname from Selection as sel left join sel.student as ss where sel.subject.id=su.id and sel.isSelection=1) as stuName, "
        				+ " (select t.nickname from Teacher t where t.id=su.teacher.id) as teacherName, "
        				+ " ma.id as majorId, ma.name as majorName) "
        				+ " from Subject as su left join su.selections as se"
        				+ " left join su.major as ma "
        				+ where
        				+ " group by su.id "
        				+ having;
        		
        		String countJpql = null;
        		if(isSelect != null)
        			countJpql = "select count(*) from Subject su left join su.selections as se left join se.student as s"
        					+ where
        					+ " and su.id in(select s.id from Subject s left join s.selections se group by s.id "
        					+  having
        					+ " )";
        		else
        			countJpql = "select count(*) from Subject su left join su.selections as se left join se.student as s" + where;
        		Query query = em.createQuery(jpql, SubjectToTeacherVO.class);
        		Query countQuery = em.createQuery(countJpql);
        //		pageable.getPageNumber()==0 ? pageable.getOffset() :  pageable.getOffset()-5
        		if(null != pageable){
        			query.setFirstResult(pageable.getOffset());
        			query.setMaxResults(pageable.getPageSize());
        		}
        		if(!StringUtils.isEmpty(titleLike)){
        			query.setParameter("titleLike", "%"+titleLike+"%");
        			countQuery.setParameter("titleLike", "%"+titleLike+"%");
        		}
        		if(!StringUtils.isEmpty(teacherId)){
        			query.setParameter("teacherId", teacherId);
        			countQuery.setParameter("teacherId", teacherId);
        		}
        		if(!StringUtils.isEmpty(majorId)){
        			query.setParameter("majorId", majorId);
        			countQuery.setParameter("majorId", majorId);
        		}
        		if(!StringUtils.isEmpty(studentId)){
        			query.setParameter("studentId", studentId);
        			countQuery.setParameter("studentId", studentId);
        		}
        		List<SubjectToTeacherVO> voList = query.getResultList();
        		return new PageImpl<SubjectToTeacherVO>(voList, pageable, Integer.valueOf(countQuery.getSingleResult().toString()));
        	}

## 使用QueryDSL的例子

        /**
             * 根据部门的id查询用户的基本信息+用户所属部门信息，并且使用UserDeptDTO进行封装返回给前端展示
             * @param departmentId
             * @return
             */
            public List<UserDeptDTO> findByDepatmentIdDTO(int departmentId) {
                QUser user = QUser.user;
                QDepartment department = QDepartment.department;
                //直接返回
                return jpaQueryFactory
                        //投影只去部分字段
                        .select(
                                user.username,
                                user.nickName,
                                user.birthday,
                                department.deptName,
                                department.createDate
         
                        )
                        .from(user)
                        //联合查询
                        .join(user.department, department)
                        .where(department.deptId.eq(departmentId))
                        .fetch()
                        //lambda开始
                        .stream()
                        .map(tuple ->
                                //需要做类型转换，所以使用map函数非常适合
                                UserDeptDTO.builder()
                                        .username(tuple.get(user.username))
                                        .nickname(tuple.get(user.nickName))
                                        .birthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tuple.get(user.birthday)))
                                        .deptName(tuple.get(department.deptName))
                                        .deptBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tuple.get(department.createDate)))
                                        .build()
                        )
                        .collect(Collectors.toList());
            }


## QueryDSL

    QueryDSL仅仅是一个通用的查询框架，专注于通过Java API构建类型安全的SQL查询。
    
    Querydsl可以通过一组通用的查询API为用户构建出适合不同类型ORM框架或者是SQL的查询语句，也就是说QueryDSL是基于各种ORM框架以及SQL之上的一个通用的查询框架。
    
    借助QueryDSL可以在任何支持的ORM框架或者SQL平台上以一种通用的API方式来构建查询。目前QueryDSL支持的平台包括JPA,JDO,SQL,Mongodb 等等。。。
                
## 引入QueryDsl

1. pom中引入依赖

        <!-- querydsl -->
        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-apt</artifactId>
            <scope>provided</scope>
        </dependency>



2. 添加maven插件

    添加这个插件是为了让程序自动生成query type(查询实体，命名方式为："Q"+对应实体名)。

    注：在使用过程中,如果遇到query type无法自动生成的情况，用maven更新一下项目即可解决(右键项目->Maven->Update Project)， 或者之间终端输入 mvn clean compile 编译一下就会自动生成Q 类
    
        <plugin>
            <groupId>com.mysema.maven</groupId>
            <artifactId>apt-maven-plugin</artifactId>
            <version>1.1.3</version>
            <executions>
                <execution>
                    <goals>
                        <goal>process</goal>
                    </goals>
                    <configuration>
                        <outputDirectory>target/generated-sources/java</outputDirectory>
                        <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                    </configuration>
                </execution>
            </executions>
        </plugin> 

3. 引入JPAQueryFactory

		@Configuration
		public class QueryDSLConfig {
		
		    @Bean
		    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
		        return new JPAQueryFactory(entityManager);
		    }
		
		}