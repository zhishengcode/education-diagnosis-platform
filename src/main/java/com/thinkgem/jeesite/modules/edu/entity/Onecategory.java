/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.edu.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 笔划类别Entity
 * @author ShawnXiang
 * @version 2018-04-08
 */
public class Onecategory extends DataEntity<Onecategory> {

	private static final long serialVersionUID = 1L;
	private String firstcategoryname;		// 分类名称
	private String sort;					//排序列

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Onecategory() {
		super();
	}

	public Onecategory(String id){
		super(id);
	}

	@Length(min=0, max=20, message="分类名称长度必须介于 0 和 20 之间")
	public String getFirstcategoryname() {
		return firstcategoryname;
	}

	public void setFirstcategoryname(String firstcategoryname) {
		this.firstcategoryname = firstcategoryname;
	}

}