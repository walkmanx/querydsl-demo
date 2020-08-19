package com.example.querydsl.service;

import com.example.querydsl.common.model.PageBean;
import com.example.querydsl.domain.GoodInfoBean;
import com.example.querydsl.model.GoodDTO;
import com.example.querydsl.model.GoodDtoPage;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>Title: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Version:zhuoyuan V2.0</p>
 *
 * @author gc
 * @description
 * @date
 */
public interface GoodService {

    /**
     * 自定义查询
     * @return
     */
    List<GoodDTO> selectWithQueryDSL();

    /**
     * 查询集合数据
     * @return
     */
    List<GoodInfoBean> list();

    /**
     * 查询分页数据
     * @return
     */
    PageBean<GoodInfoBean> page(GoodDtoPage doodDtoPage);

    /**
     * 左连接查询
     * @return
     */
    List<GoodInfoBean> leftJoin();

    /**
     * 使用Mysql聚合函数
     * @return
     */
    double avg();

    /**
     * 基于Spring Data提供的QueryDslPredicateExecutor<T>的Spring-data风格
     *
     * @return
     */
    List<GoodInfoBean> query();

    /**
     * 子查询
     *
     * @return
     */
    List<GoodInfoBean> subselect();

    List<GoodInfoBean> convertList();

    /**
     * 分页查询
     * @param predicate
     * @param pageable
     * @return
     */
    QueryResults<GoodInfoBean> findByPage(Predicate predicate, Pageable pageable);



}
