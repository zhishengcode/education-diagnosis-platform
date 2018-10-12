/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.edu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 笔划形态Entity
 * @author ShawnXiang
 * @version 2018-04-08
 */
public class Twocategory extends DataEntity<Twocategory> {

	private static final long serialVersionUID = 1L;
	private Onecategory onecategoryid;		// 笔划类别ID
	private String twocategoryname;		// 笔划形态名称
	private String twocategoryvalue;		// 笔划形态数值
	private String characterid;		// 性格特征ID
	private String status;		// 状态位

	private String charalist;
	private String eduqualifierlist;//修饰词集合
	@JsonIgnore
	public String getCharalist() {
		return charalist;
	}

	public void setCharalist(String charalist) {
		this.charalist = charalist;
	}

	public Twocategory() {
		super();
	}

	public Twocategory(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="笔划类别ID不能为空")
	public Onecategory getOnecategoryid() {
		return onecategoryid;
	}

	public void setOnecategoryid(Onecategory onecategoryid) {
		this.onecategoryid = onecategoryid;
	}

	@Length(min=0, max=50, message="笔划形态名称长度必须介于 0 和 50 之间")
	public String getTwocategoryname() {
		return twocategoryname;
	}

	public void setTwocategoryname(String twocategoryname) {
		this.twocategoryname = twocategoryname;
	}

	@Length(min=0, max=50, message="笔划形态数值长度必须介于 0 和 50 之间")
	public String getTwocategoryvalue() {
		return twocategoryvalue;
	}

	public void setTwocategoryvalue(String twocategoryvalue) {
		this.twocategoryvalue = twocategoryvalue;
	}

	@Length(min=0, max=11, message="性格特征ID长度必须介于 0 和 11 之间")
	public String getCharacterid() {
		return characterid;
	}

	public void setCharacterid(String characterid) {
		this.characterid = characterid;
	}

	@Length(min=0, max=11, message="状态位长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonIgnore
	public String getEduqualifierlist() {
		return eduqualifierlist;
	}

	public void setEduqualifierlist(String eduqualifierlist) {
		this.eduqualifierlist = eduqualifierlist;
	}
}