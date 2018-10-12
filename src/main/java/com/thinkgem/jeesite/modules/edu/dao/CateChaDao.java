package com.thinkgem.jeesite.modules.edu.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.edu.entity.CateCha;

/**
 * 性格特征与笔划形态关联DAO接口
 * @author ShawnXiang
 * @version 2018-04-08
 */
@MyBatisDao
public interface CateChaDao extends CrudDao<CateCha> {

}