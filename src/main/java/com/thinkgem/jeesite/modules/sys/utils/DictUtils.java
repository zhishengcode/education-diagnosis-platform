/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.edu.entity.Grade;
import com.thinkgem.jeesite.modules.edu.entity.Onecategory;
import com.thinkgem.jeesite.modules.edu.entity.Twocategory;
import com.thinkgem.jeesite.modules.edu.service.GradeService;
import com.thinkgem.jeesite.modules.edu.service.TwocategoryService;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";

	public static final String CACHE_GRADE_MAP = "gradeMap";

	private static TwocategoryService twocategoryService = SpringContextHolder.getBean(TwocategoryService.class);

	private static GradeService gradeService = SpringContextHolder.getBean(GradeService.class);
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	public static List<Twocategory> getTwochara(String key){
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(key)){
			Onecategory one=new Onecategory();
			one.setId(key);
			 Twocategory two=new Twocategory();
			 two.setOnecategoryid(one);
			List twolist=twocategoryService.findList(two);
				return twolist;
		}else
			return null;
	}

	public static List<Grade> getGradeList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<Grade>> gradeMap = (Map<String, List<Grade>>)CacheUtils.get(CACHE_GRADE_MAP);

//		if (gradeMap==null){
			gradeMap = Maps.newHashMap();
			for (Grade grade : gradeService.findGradeList()){
				List<Grade> gradeList = gradeMap.get(grade.getName());
				if (gradeList != null){
					gradeList.add(grade);
				}else{
					gradeMap.put(grade.getName(), Lists.newArrayList(grade));
				}
			}
			CacheUtils.put(CACHE_GRADE_MAP, gradeMap);
//		}

		List<Grade> gradeList = gradeMap.get(type);
		if (gradeList == null){
			gradeList = Lists.newArrayList();
		}
		return gradeList;
	}

	public static List<Twocategory> getTwocharaByStudentConclusion(String key){
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(key)){
			Onecategory one=new Onecategory();
			one.setId(key);
			Twocategory two=new Twocategory();
			two.setOnecategoryid(one);
			List twolist=twocategoryService.findListByStudentConclusion(two);
			return twolist;
		}else
			return null;
	}
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	public static List<Dict> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList(new Dict())){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getDictList(type));
	}
	
}
