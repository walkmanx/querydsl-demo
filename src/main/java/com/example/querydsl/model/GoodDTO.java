package com.example.querydsl.model;

import lombok.Data;

/**
 * <p>Title: 商品dto</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Version:zhuoyuan V2.0</p>
 *
 * @author gc
 * @description
 * @date 2020/8/18 上午 9:14
 */
@Data
public class GoodDTO {

    //主键
    private Long id;
    //标题
    private String title;
    //单位
    private String unit;
    //价格
    private double price;
    //类型名称
    private String typeName;
    //类型编号
    private Long typeId;
}
