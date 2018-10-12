/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.edu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.student.entity.Student;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 结论Entity
 * @author ShawnXiang
 * @version 2018-04-08
 */
public class Conclusion extends DataEntity<Conclusion> {

	private static final long serialVersionUID = 1L;
	private Teacher teacher;		// 测评老师
	private Student student;		// 学生
	private Date createtime;		// 生成时间
	private String conclusion;		// 结论
	private Integer status;		// 状态

	public Conclusion() {
		super();
	}

	public Conclusion(String id){
		super(id);
	}

	@JsonBackReference
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@JsonBackReference
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Length(min=0, max=500, message="结论长度必须介于 0 和 500 之间")
	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}