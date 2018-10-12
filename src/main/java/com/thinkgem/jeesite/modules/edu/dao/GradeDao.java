package com.thinkgem.jeesite.modules.edu.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.edu.entity.Grade;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface GradeDao extends CrudDao<Grade> {
    List<Grade> findGradeList();
}
