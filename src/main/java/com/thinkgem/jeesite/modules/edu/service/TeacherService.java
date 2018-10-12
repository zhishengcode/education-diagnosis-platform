package com.thinkgem.jeesite.modules.edu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.entity.Teacher;
import com.thinkgem.jeesite.modules.edu.dao.TeacherDao;

/**
 * 教师信息Service
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Service
@Transactional(readOnly = true)
public class TeacherService extends CrudService<TeacherDao, Teacher> {

	public Teacher get(String id) {
		return super.get(id);
	}

	public List<Teacher> findList(Teacher teacher) {
		return super.findList(teacher);
	}

	public Page<Teacher> findPage(Page<Teacher> page, Teacher teacher) {
		return super.findPage(page, teacher);
	}

	@Transactional(readOnly = false)
	public void save(Teacher teacher) {
		super.save(teacher);
	}

	@Transactional(readOnly = false)
	public void delete(Teacher teacher) {
		super.delete(teacher);
	}

}