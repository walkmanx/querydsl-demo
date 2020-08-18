package com.example.querydsl.config;

import org.hibernate.dialect.MySQL55Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MySQLChineseDialect extends MySQL55Dialect {
	
	 public MySQLChineseDialect(){
        super();
        registerFunction("convert_gbk", new SQLFunctionTemplate(StandardBasicTypes.STRING, "convert(?1 using gbk)"));
    }

}
