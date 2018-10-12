package com.thinkgem.jeesite.modules.edu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.entity.Conclusion;
import com.thinkgem.jeesite.modules.edu.dao.ConclusionDao;

/**
 * 结论Service
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Service
@Transactional(readOnly = true)
public class ConclusionService extends CrudService<ConclusionDao, Conclusion> {

	public Conclusion get(String id) {
		return super.get(id);
	}

	public List<Conclusion> findList(Conclusion conclusion) {
		return super.findList(conclusion);
	}

	public Page<Conclusion> findPage(Page<Conclusion> page, Conclusion conclusion) {
		return super.findPage(page, conclusion);
	}

	@Transactional(readOnly = false)
	public void save(Conclusion conclusion) {
		super.save(conclusion);
	}

	@Transactional(readOnly = false)
	public void delete(Conclusion conclusion) {
		super.delete(conclusion);
	}

}