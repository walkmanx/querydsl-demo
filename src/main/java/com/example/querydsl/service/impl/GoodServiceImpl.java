package com.example.querydsl.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.querydsl.common.model.PageBean;
import com.example.querydsl.domain.GoodInfoBean;
import com.example.querydsl.domain.QGoodInfoBean;
import com.example.querydsl.domain.QGoodTypeBean;
import com.example.querydsl.model.GoodDTO;
import com.example.querydsl.model.GoodDtoPage;
import com.example.querydsl.repository.GoodInfoRepository;
import com.example.querydsl.service.GoodService;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimpleTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import java.util.List;

/**
 * <p>Title: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Version:zhuoyuan V2.0</p>
 *
 * @author gc
 * @description
 * @date 2020/8/18 上午 10:13
 */
@Service
@Slf4j
public class GoodServiceImpl implements GoodService {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    GoodInfoRepository goodInfoRepository;

    QGoodInfoBean goodInfoBean = QGoodInfoBean.goodInfoBean;

    QGoodTypeBean goodTypeBean = QGoodTypeBean.goodTypeBean;

    @Override
    public List<GoodDTO> selectWithQueryDSL() {

        // 查询字段-select()
        List<String> titleList = jpaQueryFactory.select(goodInfoBean.title).from(goodInfoBean).orderBy(getOrder(Order.ASC,goodInfoBean.title)).fetch();

        log.info("查询字段-select() : " + JSON.toJSONString(titleList));

        //查询实体-selectFrom()
        List<GoodInfoBean> goodInfoBeanList = jpaQueryFactory.selectFrom(goodInfoBean).orderBy(getOrder("asc","title")).fetch();

        log.info("查询实体-selectFrom() : " + JSON.toJSONString(goodInfoBeanList));

        //去重查询-selectDistinct()
        List<String> distinctTitleList = jpaQueryFactory.selectDistinct(goodInfoBean.title).from(goodInfoBean).orderBy(getDefaultOrder()).fetch();

        log.info("去重查询-selectDistinct() : " + JSON.toJSONString(distinctTitleList));

        //获取首个查询结果-fetchFirst()
        GoodInfoBean firstMember = jpaQueryFactory.selectFrom(goodInfoBean).orderBy(getDefaultOrder()).fetchFirst();

        log.info("获取首个查询结果-fetchFirst() : " + JSON.toJSONString(firstMember));

        //获取唯一查询结果-fetchOne()
        //当fetchOne()根据查询条件从数据库中查询到多条匹配数据时，会抛`NonUniqueResultException`。
        try {
            GoodInfoBean anotherFirstMember = jpaQueryFactory.selectFrom(goodInfoBean).orderBy(getDefaultOrder()).fetchOne();
            log.info("获取唯一查询结果-fetchOne() : " + JSON.toJSONString(anotherFirstMember));

        }catch (NonUniqueResultException e){
            log.info("获取唯一查询结果-fetchOne() : " + e.getMessage());
        }

        // //查询并将结果封装至dto中
        return jpaQueryFactory.select(
                Projections.bean(
                        //返回自定义实体的类型
                        GoodDTO.class,
                        goodInfoBean.id,
                        goodInfoBean.price,
                        goodInfoBean.title,
                        goodInfoBean.unit,
                        //使用别名对应dto内的typeName
                        goodTypeBean.name.as("typeName"),
                        //使用别名对应dto内的typeId
                        goodTypeBean.id.as("typeId")
                )
        )
                .from(goodInfoBean,goodTypeBean)
                .where(goodInfoBean.typeId.eq(goodTypeBean.id))
                .orderBy(getDefaultOrder())
                .fetch();
    }

    @Override
    public List<GoodInfoBean> list() {
        return  jpaQueryFactory.selectFrom(goodInfoBean).orderBy(getDefaultOrder()).fetch();
    }

    @Override
    public PageBean<GoodInfoBean> page(GoodDtoPage goodDtoPage) {
        PageBean<GoodInfoBean> pageBean = new PageBean<GoodInfoBean>();

        int offset = goodDtoPage.getPage() * goodDtoPage.getSize();

        JPAQuery<GoodInfoBean> query = jpaQueryFactory.selectFrom(goodInfoBean).where(this.getPredicate(goodDtoPage)).offset(offset).limit(goodDtoPage.getSize()).orderBy(getDefaultOrder());

        QueryResults<GoodInfoBean> results = query.fetchResults();

        pageBean.setContent(results.getResults());
        pageBean.setTotal(results.getTotal());
        pageBean.setPage(goodDtoPage.getPage());
        pageBean.setSize(new Long(results.getLimit()).intValue());

        return  pageBean;
    }

    @Override
    public List<GoodInfoBean> leftJoin() {
        //以左关联为例-left join
        return jpaQueryFactory.selectFrom(goodInfoBean).leftJoin(goodTypeBean).on(goodInfoBean.typeId.eq(goodTypeBean.id)).where(goodInfoBean.price.between(1,10)).fetch();
    }

    @Override
    public double avg() {
        return jpaQueryFactory.select(goodInfoBean.price.avg()).from(goodInfoBean).fetchOne();
    }

    @Override
    public List<GoodInfoBean> query() {
        BooleanBuilder builder1 = new BooleanBuilder();
        builder1.and(goodInfoBean.title.ne(""));
        return Lists.newArrayList(goodInfoRepository.findAll(builder1,getDefaultOrder()));
    }

    @Override
    public List<GoodInfoBean> subselect() {
        return jpaQueryFactory.selectFrom(goodInfoBean).where(goodInfoBean.typeId.in(JPAExpressions.select(goodTypeBean.id).from(goodTypeBean))).fetch();
    }

    @Override
    public List<GoodInfoBean> convertList() {
        return jpaQueryFactory.selectFrom(goodInfoBean).orderBy(getDefaultOrder()).fetch();
    }

    @Override
    public QueryResults<GoodInfoBean> findByPage(Predicate predicate, Pageable pageable) {
        JPAQuery<GoodInfoBean> query = jpaQueryFactory.selectFrom(goodInfoBean).where(predicate).offset(pageable.getOffset()).limit(new Long(pageable.getPageSize()));
        for(Sort.Order order : pageable.getSort()){
            query.orderBy(getOrder(order.getDirection().name(),order.getProperty()));
        }
        return query.fetchResults();
    }

    /**
     * 构建查询条件
     * @return
     */
    private BooleanBuilder getPredicate(GoodDtoPage goodDtoPage){
        BooleanBuilder builder1 = new BooleanBuilder();

        if(goodInfoBean != null && goodDtoPage != null){
            if(!StringUtils.isEmpty(goodDtoPage.getTitle())){
                builder1.and(goodInfoBean.title.like("%"+goodDtoPage.getTitle()+"%"));
            }
            if(goodDtoPage.getPrice() != null){
                builder1.and(goodInfoBean.price.eq(goodDtoPage.getPrice()));
            }
        }
        return builder1;
    }

    private OrderSpecifier getDefaultOrder(){
        return this.getOrder(Order.ASC,goodInfoBean.title);
    }

    private OrderSpecifier getOrder(String direction,String propertyName){
        return this.getOrder(Order.valueOf(direction.toUpperCase()),new PathBuilder<>(Entity.class, "goodInfoBean").get(propertyName));
    }

    private OrderSpecifier getOrder(Order order, Path path){
        SimpleTemplate<String> simpleTemplate = Expressions.simpleTemplate(String.class, "convert_gbk({0})", path);
        return new OrderSpecifier<String>(order, simpleTemplate);
    }
}
