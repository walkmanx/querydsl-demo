package com.example.querydsl.repository;

import com.example.querydsl.domain.GoodInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>Title: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Version:zhuoyuan V2.0</p>
 *
 * @author gc
 * @description
 * @date 2020/8/18 下午 15:43
 */
@Repository
public interface GoodInfoRepository extends JpaRepository<GoodInfoBean,Long>,QuerydslPredicateExecutor<GoodInfoBean> {

}
