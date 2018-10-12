/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.student.entity;

import com.thinkgem.jeesite.modules.edu.entity.Teacher;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学员信息Entity
 * @author wlh
 * @version 2017-10-15
 */
public class Student extends DataEntity<Student> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 学员姓名
	private String age;		// 学员年龄
	private String gender;		// 性别：'0:男 1：女'
	private String mobile;		// 手机号
	private Teacher teacher;		// 授课教师
	private String address;		// 地址
	private String status;		// 删除标志：'0:正常 1：删除'
	private Date createtime;		// 创建时间
	
	public Student() {
		super();
	}

	public Student(String id){
		super(id);
	}

	@Length(min=0, max=128, message="学员姓名长度必须介于 0 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=5, message="学员年龄长度必须介于 0 和 5 之间")
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	@Length(min=0, max=1, message="性别：'0:男 1：女'长度必须介于 0 和 1 之间")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Length(min=0, max=11, message="手机号长度必须介于 0 和 11 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@JsonBackReference
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@Length(min=0, max=128, message="地址长度必须介于 0 和 128 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=1, message="删除标志：'0:正常 1：删除'长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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