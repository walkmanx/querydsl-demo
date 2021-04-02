package com.example.querydsl.domain;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * <p>Title: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Version:zhuoyuan V2.0</p>
 *
 * @author gc
 * @description
 * @date 2021/3/31 上午 10:41
 */
@Data
@MappedSuperclass
public class GoodBaseBean {

    /**
     * 创建人Id
     */
    protected String createUserId;

    /**
     * 创建人名称
     */
    protected String createUserName;

    /**
     * 创建时间
     */
    protected Date createDate;

    /**
     * 更新人Id
     */
    protected String updateUserId;

    /**
     * 更新人名称
     */
    protected String updateUserName;

    /**
     * 更新时间
     */
    protected Date updateDate;

}
