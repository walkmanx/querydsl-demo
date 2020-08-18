package com.example.querydsl.controller;

import com.example.querydsl.domain.GoodInfoBean;
import com.example.querydsl.model.GoodDTO;
import com.example.querydsl.service.GoodService;
import com.google.common.collect.Lists;
import com.querydsl.core.QueryResults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Version:zhuoyuan V2.0</p>
 *
 * @author gc
 * @description
 * @date 2020/8/18 上午 9:22
 */
@RestController
public class GoodController {

    @Resource
    private GoodService goodService;

    @GetMapping(value = "/selectWithQueryDSL")
    public List<GoodDTO> selectWithQueryDSL(){
        return goodService.selectWithQueryDSL();
    }

    @GetMapping(value = "/list")
    public List<GoodInfoBean> list(){
        return goodService.list();
    }

    @GetMapping(value = "/page")
    public QueryResults<GoodInfoBean> page(){
        return goodService.page();
    }

    @GetMapping(value = "/leftjoin")
    public List<GoodInfoBean> leftJoin(){
        return goodService.leftJoin();
    }

    @GetMapping(value = "/avg")
    public double avg(){
        return goodService.avg();
    }

    @GetMapping(value = "/query")
    public List<GoodInfoBean> query(){
        return goodService.query();
    }

    @GetMapping(value = "/subselect")
    public List<GoodInfoBean> subselect(){
        return goodService.subselect();
    }

    @GetMapping(value = "/convertList")
    public List<GoodInfoBean> convertList(){
        return goodService.convertList();
    }

}
