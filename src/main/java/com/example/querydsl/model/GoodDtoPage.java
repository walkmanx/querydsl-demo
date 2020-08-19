package com.example.querydsl.model;

import com.example.querydsl.common.model.DataQueryObjectPage;
import lombok.Data;

/**
 * <p>Title: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Version:zhuoyuan V2.0</p>
 *
 * @author gc
 * @description
 * @date 2020/8/19 上午 9:32
 */
@Data
public class GoodDtoPage extends DataQueryObjectPage {

    private String title;

    private Double price;

    private String type;

    public GoodDtoPage(){
        this.propertyName = "title";
    }

}
