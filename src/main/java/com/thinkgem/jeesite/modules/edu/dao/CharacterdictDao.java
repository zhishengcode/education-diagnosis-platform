/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.edu.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.edu.entity.Characterdict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 特征DAO接口
 * @author ShawnXiang
 * @version 2018-04-08
 */
@MyBatisDao
public interface CharacterdictDao extends CrudDao<Characterdict> {

    List<String> findconByTwo(Map map);

    List<Map<String,Object>> findconMapByTwo(Map map);

    /**
     * 增加用于判断去重
     * @param map
     * @return
     * @author ShawnXiang
     * @version 2017-11-02
     */
    int countCharacterByName(Map<String ,Object> map);
}