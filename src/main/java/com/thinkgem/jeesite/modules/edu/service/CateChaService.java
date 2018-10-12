package com.thinkgem.jeesite.modules.edu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.entity.CateCha;
import com.thinkgem.jeesite.modules.edu.dao.CateChaDao;

/**
 * 性格特征与笔划形态关联Service
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Service
@Transactional(readOnly = true)
public class CateChaService extends CrudService<CateChaDao, CateCha> {

	private CateChaDao cateChaDao;
	public CateCha get(String id) {
		return super.get(id);
	}

	public List<CateCha> findList(CateCha cateCha) {
		return super.findList(cateCha);
	}

	public Page<CateCha> findPage(Page<CateCha> page, CateCha cateCha) {
		return super.findPage(page, cateCha);
	}

	@Transactional(readOnly = false)
	public void save(CateCha cateCha) {
		super.save(cateCha);
	}

	@Transactional(readOnly = false)
	public void delete(String id) {
		CateCha cateCha=	new CateCha();
		cateCha.setId(id);
		super.delete(cateCha);
	}

}