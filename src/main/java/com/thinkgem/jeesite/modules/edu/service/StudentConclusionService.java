package com.thinkgem.jeesite.modules.edu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.entity.StudentConclusion;
import com.thinkgem.jeesite.modules.edu.dao.StudentConclusionDao;

/**
 * 结论Service
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Service
@Transactional(readOnly = true)
public class StudentConclusionService extends CrudService<StudentConclusionDao, StudentConclusion> {

	@Autowired
	private StudentConclusionDao studentConclusionDao;

	public StudentConclusion get(String id) {
		return super.get(id);
	}

	public List<StudentConclusion> findList(StudentConclusion studentConclusion) {
		return super.findList(studentConclusion);
	}

	public Page<StudentConclusion> findPage(Page<StudentConclusion> page, StudentConclusion studentConclusion) {
		return super.findPage(page, studentConclusion);
	}

	public Page<StudentConclusion> findPageByConclusion(Page<StudentConclusion> page, StudentConclusion studentConclusion) {
		return super.findPageByConclusion(page, studentConclusion);
	}



	@Transactional(readOnly = false)
	public void save(StudentConclusion studentConclusion) {
		super.save(studentConclusion);
	}

	@Transactional(readOnly = false)
	public void delete(StudentConclusion studentConclusion) {
		super.delete(studentConclusion);
	}

	@Transactional(readOnly = false)
	public int countStudentConclusionByNo(Map<String, Object> map) {
		return studentConclusionDao.countStudentConclusionByNo(map);
	}

	public List<Map<String,Object>> selectConclusionByCharacterIds(List<String> list) {
		return studentConclusionDao.selectConclusionByCharacterIds(list);
	}

	/**
	 * 返回构建雷达图的json的list的Service层
	 * @param characterIdslist
	 * @return
	 */
	public List<Map<String,Object>> selectEchartsJsonByCharacterIds(List<String> characterIdslist) {
		return studentConclusionDao.selectEchartsJsonByCharacterIds(characterIdslist);
	}

	/**
	 * 返回构建正负条形图的json的list的Service层
	 * @param characterIdslist
	 * @return
	 */
	public List<Map<String,Object>> selectColumnEchartsJsonByCharacterIds(List<String> characterIdslist){
		return studentConclusionDao.selectColumnEchartsJsonByCharacterIds(characterIdslist);
	}

}