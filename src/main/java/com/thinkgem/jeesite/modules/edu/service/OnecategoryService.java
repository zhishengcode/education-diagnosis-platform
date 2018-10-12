package com.thinkgem.jeesite.modules.edu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.entity.Onecategory;
import com.thinkgem.jeesite.modules.edu.dao.OnecategoryDao;

/**
 * 笔划类别Service
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Service
@Transactional(readOnly = true)
public class OnecategoryService extends CrudService<OnecategoryDao, Onecategory> {

	public Onecategory get(String id) {
		return super.get(id);
	}

	public List<Onecategory> findList(Onecategory onecategory) {
		return super.findList(onecategory);
	}

	public Page<Onecategory> findPage(Page<Onecategory> page, Onecategory onecategory) {
		return super.findPage(page, onecategory);
	}

	@Transactional(readOnly = false)
	public void save(Onecategory onecategory) {
		super.save(onecategory);
	}

	@Transactional(readOnly = false)
	public void delete(Onecategory onecategory) {
		super.delete(onecategory);
	}

}