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