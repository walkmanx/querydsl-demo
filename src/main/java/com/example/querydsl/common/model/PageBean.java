package com.example.querydsl.common.model;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 * @description 分页查询结果封装类
 * @author hx
 * @date  2019年3月26日
 */
public class PageBean<T> {

	private Integer page;
	
	private Integer size;
	
	private Long total;
	
	private List<T> content;
	
	public PageBean(Page<?> page, List<T> content) {
		this.size = page.getSize();
		this.page = page.getNumber();
		this.total = page.getTotalElements();
		this.content = content;
	}

	public PageBean() {
	}

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

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

}
