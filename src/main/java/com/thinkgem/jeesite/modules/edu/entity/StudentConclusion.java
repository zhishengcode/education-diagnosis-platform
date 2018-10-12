/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.edu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.student.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 结论Entity
 * @author ShawnXiang
 * @version 2018-04-08
 */
public class StudentConclusion extends DataEntity<StudentConclusion> {

	private static final long serialVersionUID = 1L;
	private String student;		// 测评编号
	private Long evaluationType;
	private User teacher;		// 教师
	private String characterids;		// 性格特征Id集合
	private String charactervalues;		// 性格特征名称集合
	private String conclustion;		// 结论
	private Long status;		// 状态位
	private Date createtime;		// 创建时间
	private List<String> values;
	private String conclusionName;//名称
	private String echartsPic; //echarts的图片的编码
	private String columnEchartsPic; //echarts的正负条形图的编码
	private String echartsScore;
	private String school;	//学校
	private String grade;	//班级
	private String age;		//年龄
	private String orgId;    //机构Id

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEchartsScore() {
		return echartsScore;
	}

	public void setEchartsScore(String echartsScore) {
		this.echartsScore = echartsScore;
	}

	public String getColumnEchartsPic() {
		return columnEchartsPic;
	}

	public void setColumnEchartsPic(String columnEchartsPic) {
		this.columnEchartsPic = columnEchartsPic;
	}

	public List<String> getValues() {
		List<String> list = Lists.newArrayList();
		if (charactervalues != null){
			for (String s : StringUtils.split(charactervalues, ",")) {
				list.add(s);
			}
		}
		return list;
	}

	public void setValues(List<String> Values) {
		this.values = Values;
	}

	public StudentConclusion() {
		super();
	}

	public StudentConclusion(String id){
		super(id);
	}

	@JsonBackReference
	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public Long getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(Long evaluationType) {
		this.evaluationType = evaluationType;
	}

	@JsonBackReference
	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	@Length(min=0, max=128, message="性格特征Id集合长度必须介于 0 和 128 之间")
	public String getCharacterids() {
		return characterids;
	}

	public void setCharacterids(String characterids) {
		this.characterids = characterids;
	}

	//	@Length(min=0, max=200, message="性格特征名称集合长度必须介于 0 和 200 之间")
	public String getCharactervalues() {
		return charactervalues;
	}

	public void setCharactervalues(String charactervalues) {
		this.charactervalues = charactervalues;
	}

	//	@Length(min=0, max=200, message="结论长度必须介于 0 和 200 之间")
	public String getConclustion() {
		return conclustion;
	}

	public void setConclustion(String conclustion) {
		this.conclustion = conclustion;
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

	public String getConclusionName() {
		return conclusionName;
	}

	public void setConclusionName(String conclusionName) {
		this.conclusionName = conclusionName;
	}

//	@JsonIgnore
	public String getEchartsPic() {
		return echartsPic;
	}

	public void setEchartsPic(String echartsPic) {
		this.echartsPic = echartsPic;
	}
}