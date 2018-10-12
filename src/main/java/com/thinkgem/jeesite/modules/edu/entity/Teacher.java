/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.edu.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 教师信息Entity
 * @author ShawnXiang
 * @version 2018-04-08
 */
public class Teacher extends DataEntity<Teacher> {

	private static final long serialVersionUID = 1L;
	private String name;		// 教师姓名
	private String mobile;		// 教师手机号
	private Long status;		// 状态位
	private Date createtime;		// 创建时间

	public Teacher() {
		super();
	}

	public Teacher(String id){
		super(id);
	}

	@Length(min=0, max=50, message="教师姓名长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}