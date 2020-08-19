package com.example.querydsl.common.model;

import java.util.List;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 * @description 排序查询对象
 * @author hx
 * @date  2019年3月26日
 */
public class DataQueryObjectSort implements DataQueryObject {
	
	protected String propertyName;
	
	protected boolean ascending = true;
	
	    protected List<String> propertyNames;
    
    protected List<Boolean> ascendings;


	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

    public List<String> getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(List<String> propertyNames) {
        this.propertyNames = propertyNames;
    }

    public List<Boolean> getAscendings() {
        return ascendings;
    }

    public void setAscendings(List<Boolean> ascendings) {
        this.ascendings = ascendings;
    }

	
}
