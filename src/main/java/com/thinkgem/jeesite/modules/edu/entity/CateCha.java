package com.thinkgem.jeesite.modules.edu.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 性格特征与笔划形态关联Entity
 * @author ShawnXiang
 * @version 2018-04-08
 */
public class CateCha extends DataEntity<CateCha> {

	private static final long serialVersionUID = 1L;
	private String categoryId;		// 笔划形态id
	private String characterId;		// 性格特征id
	private String eduqualifierId; //修饰词Id

	public CateCha() {
		super();
	}

	public CateCha(String id){
		super(id);
	}

	@Length(min=1, max=32, message="笔划形态id长度必须介于 1 和 32 之间")
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Length(min=1, max=32, message="性格特征id长度必须介于 1 和 32 之间")
	public String getCharacterId() {
		return characterId;
	}

	public void setCharacterId(String characterId) {
		this.characterId = characterId;
	}
	@Length(min=1, max=32, message="修饰词id长度必须介于 1 和 32 之间")
	public String getEduqualifierId() {
		return eduqualifierId;
	}

	public void setEduqualifierId(String eduqualifierId) {
		this.eduqualifierId = eduqualifierId;
	}
}