package com.thinkgem.jeesite.modules.edu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.entity.Twocategory;
import com.thinkgem.jeesite.modules.edu.dao.TwocategoryDao;

/**
 * 笔划形态Service
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Service
@Transactional(readOnly = true)
public class TwocategoryService extends CrudService<TwocategoryDao, Twocategory> {

	public Twocategory get(String id) {
		return super.get(id);
	}

	public List<Twocategory> findList(Twocategory twocategory) {
		return super.findList(twocategory);
	}

	public List<Twocategory> findListByStudentConclusion(Twocategory twocategory) {
		return super.findListByStudentConclusion(twocategory);
	}

	public Page<Twocategory> findPage(Page<Twocategory> page, Twocategory twocategory) {
		return super.findPage(page, twocategory);
	}

	@Transactional(readOnly = false)
	public void save(Twocategory twocategory) {
		super.save(twocategory);
	}

	@Transactional(readOnly = false)
	public void delete(Twocategory twocategory) {
		super.delete(twocategory);
	}

}