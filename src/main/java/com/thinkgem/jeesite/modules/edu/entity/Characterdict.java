/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.edu.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 特征Entity
 * @author ShawnXiang
 * @version 2018-04-08
 */
public class Characterdict extends DataEntity<Characterdict> {

	private static final long serialVersionUID = 1L;
	private String characterName;		// 性格特征名称
	private String characterValue;		// 性格特征值
	private String sort;		// 排序
	private String personalityCharacteristicsId;//性格特征维度Id

	public String getPersonalityCharacteristicsId() {
		return personalityCharacteristicsId;
	}

	public void setPersonalityCharacteristicsId(String personalityCharacteristicsId) {
		this.personalityCharacteristicsId = personalityCharacteristicsId;
	}

	public Characterdict() {
		super();
	}

	public Characterdict(String id){
		super(id);
	}

	@Length(min=0, max=128, message="性格特征名称长度必须介于 0 和 128 之间")
	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	@Length(min=0, max=11, message="性格特征值长度必须介于 0 和 11 之间")
	public String getCharacterValue() {
		return characterValue;
	}

	public void setCharacterValue(String characterValue) {
		this.characterValue = characterValue;
	}

	@Length(min=0, max=11, message="排序长度必须介于 0 和 11 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}