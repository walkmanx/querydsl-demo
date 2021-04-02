package com.example.querydsl.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Title: 商品基本信息实体</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Version:zhuoyuan V2.0</p>
 *
 * @author gc
 * @description
 * @date 2020/8/18 上午 9:09
 */
@Entity
@Table(name = "good_infos")
@Data
public class GoodInfoBean implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "tg_id")
    @GeneratedValue
    private Long id;

    /**
     * 标题
     */
    @Column(name = "tg_title")
    private String title;

    /**
     * 价格
     */
    @Column(name = "tg_price")
    private double price;

    /**
     * 单位
     */
    @Column(name = "tg_unit")
    private String unit;

    /**
     * 排序
     */
    @Column(name = "tg_order")
    private int order;

    /**
     * 类型编号
     */
    @Column(name = "tg_type_id")
    private Long typeId;

}
