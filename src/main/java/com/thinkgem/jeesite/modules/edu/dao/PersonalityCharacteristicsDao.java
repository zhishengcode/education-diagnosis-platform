package com.thinkgem.jeesite.modules.edu.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.edu.entity.PersonalityCharacteristics;

import java.util.List;
import java.util.Map;

/**
 * @author ShawnXiang
 * @version 2018-04-08
 */
@MyBatisDao
public interface PersonalityCharacteristicsDao extends CrudDao<PersonalityCharacteristics>{
     int countcharacteristicByName(Map<String, Object> map);

     List<PersonalityCharacteristics> selectCharacteristicScoreByName(Map<String, Object> map);
}
