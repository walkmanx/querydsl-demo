package com.example.querydsl.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.querydsl.domain.GoodInfoBean;
import com.example.querydsl.domain.QGoodInfoBean;
import com.example.querydsl.domain.QGoodTypeBean;
import com.example.querydsl.model.GoodDTO;
import com.example.querydsl.repository.GoodInfoRepository;
import com.example.querydsl.service.GoodService;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.querydsl.repository.GoodInfoRepository;

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
        List<String> titleList = jpaQueryFactory.select(goodInfoBean.title).from(goodInfoBean).fetch();

        log.info("查询字段-select() : " + JSON.toJSONString(titleList));

        //查询实体-selectFrom()
        List<GoodInfoBean> goodInfoBeanList = jpaQueryFactory.selectFrom(goodInfoBean).fetch();

        log.info("查询实体-selectFrom() : " + JSON.toJSONString(goodInfoBeanList));

        //去重查询-selectDistinct()
        List<String> distinctTitleList = jpaQueryFactory.selectDistinct(goodInfoBean.title).from(goodInfoBean).fetch();

        log.info("去重查询-selectDistinct() : " + JSON.toJSONString(distinctTitleList));

        //获取首个查询结果-fetchFirst()
        GoodInfoBean firstMember = jpaQueryFactory.selectFrom(goodInfoBean).fetchFirst();

        log.info("获取首个查询结果-fetchFirst() : " + JSON.toJSONString(firstMember));

        //获取唯一查询结果-fetchOne()
        //当fetchOne()根据查询条件从数据库中查询到多条匹配数据时，会抛`NonUniqueResultException`。
        try {
            GoodInfoBean anotherFirstMember = jpaQueryFactory.selectFrom(goodInfoBean).fetchOne();
            log.info("获取唯一查询结果-fetchOne() : " + JSON.toJSONString(anotherFirstMember));

        }catch (NonUniqueResultException e){
            log.info("获取唯一查询结果-fetchOne() : " + e.getMessage());
        }

        // //查询并将结果封装至dto中
        return jpaQueryFactory.select(
                Projections.bean(
                        GoodDTO.class,//返回自定义实体的类型
                        goodInfoBean.id,
                        goodInfoBean.price,
                        goodInfoBean.title,
                        goodInfoBean.unit,
                        goodTypeBean.name.as("typeName"),//使用别名对应dto内的typeName
                        goodTypeBean.id.as("typeId")//使用别名对应dto内的typeId
                )
        )
                .from(goodInfoBean,goodTypeBean)
                .where(this.getPredicate())
                .orderBy(goodInfoBean.title.asc())
                .fetch();
    }

    @Override
    public List<GoodInfoBean> list() {
        return  jpaQueryFactory.selectFrom(goodInfoBean).fetch();
    }

    @Override
    public QueryResults<GoodInfoBean> page() {
        return  jpaQueryFactory.selectFrom(goodInfoBean).orderBy(goodInfoBean.order.asc()).offset(0).limit(5).fetchResults();
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
        OrderSpecifier<Integer> order = new OrderSpecifier<>(Order.ASC, goodInfoBean.order);
        return Lists.newArrayList(goodInfoRepository.findAll(goodInfoBean.title.ne("菜花"),order));
    }

    @Override
    public List<GoodInfoBean> subselect() {
        return jpaQueryFactory.selectFrom(goodInfoBean).where(goodInfoBean.typeId.in(JPAExpressions.select(goodTypeBean.id).from(goodTypeBean))).fetch();
    }

    @Override
    public List<GoodInfoBean> convertList() {

        SimpleTemplate<String> simpleTemplate = Expressions.simpleTemplate(String.class, "convert_gbk({0})", goodInfoBean.title);

        log.info(JSON.toJSONString(simpleTemplate));

        return jpaQueryFactory.selectFrom(goodInfoBean).orderBy(new OrderSpecifier(Order.ASC,simpleTemplate)).fetch();
    }

    /**
     * 构建查询条件
     * @return
     */
    private BooleanBuilder getPredicate(){
        BooleanBuilder builder1 = new BooleanBuilder();
        BooleanBuilder builder2 = new BooleanBuilder();

        if(goodInfoBean != null){
            builder1.and(goodInfoBean.typeId.eq(goodTypeBean.id));

            builder2.or(goodInfoBean.title.eq("油菜"));
            builder2.or(goodInfoBean.title.eq("菜花"));

            builder1.and(builder2);
        }
        return builder1;
    }
}
