/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.edu.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.edu.entity.StudentConclusion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 结论DAO接口
 * @author ShawnXiang
 * @version 2018-04-08
 */
@MyBatisDao
public interface StudentConclusionDao extends CrudDao<StudentConclusion> {
    /**
     * 增加用于判断去重
     * @param map
     * @return
     * @author ShawnXiang
     * @version 2018-04-08
     */
    int countStudentConclusionByNo(Map<String ,Object> map);

    /**
     * 用于返回按照特征维度分组的性格特征名称的集合
     * @param list
     * @return
     */
    List<Map<String,Object>> selectConclusionByCharacterIds(List<String> list);


    /**
     * 返回构建雷达图的json的list数据接口
     * @param characterIdslist
     * @return
     */
    List<Map<String,Object>> selectEchartsJsonByCharacterIds(List<String> characterIdslist);


    /**
     * 返回构建正负条形图的json的list数据接口
     * @param characterIdslist
     * @return
     */
    List<Map<String,Object>> selectColumnEchartsJsonByCharacterIds(List<String> characterIdslist);

}