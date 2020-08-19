package com.example.querydsl.common.model;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 * @description 分页查询对象
 * @author hx
 * @date  2019年3月26日
 */
public class DataQueryObjectPage extends DataQueryObjectSort {
	
	protected Integer page = 0;
	
	protected Integer size = 10;
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}


}
