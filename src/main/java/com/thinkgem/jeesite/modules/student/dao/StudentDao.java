/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.student.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.student.entity.Student;

/**
 * 学员信息DAO接口
 * @author wlh
 * @version 2017-10-15
 */
@MyBatisDao
public interface StudentDao extends CrudDao<Student> {
	
}