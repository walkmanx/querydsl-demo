package com.example.querydsl.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Title: 商品类别实体</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Version:zhuoyuan V2.0</p>
 *
 * @author gc
 * @description
 * @date 2020/8/18 上午 9:10
 */
@Entity
@Table(name = "good_types")
@Data
public class GoodTypeBean  implements Serializable {

    //类型编号
    @Id
    @GeneratedValue
    @Column(name = "tgt_id")
    private Long id;
    //类型名称
    @Column(name = "tgt_name")
    private String name;
    //是否显示
    @Column(name = "tgt_is_show")
    private int isShow;
    //排序
    @Column(name = "tgt_order")
    private int order;

}
