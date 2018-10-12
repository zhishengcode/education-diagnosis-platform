package com.thinkgem.jeesite.modules.edu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.edu.entity.Characterdict;
import com.thinkgem.jeesite.modules.edu.dao.CharacterdictDao;

/**
 * 特征Service
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Service
@Transactional(readOnly = true)
public class CharacterdictService extends CrudService<CharacterdictDao, Characterdict> {

	@Autowired
	private CharacterdictDao characterdictdao;

	public Characterdict get(String id) {
		return super.get(id);
	}

	public List<Characterdict> findList(Characterdict characterdict) {
		return super.findList(characterdict);
	}

	public Page<Characterdict> findPage(Page<Characterdict> page, Characterdict characterdict) {
		return super.findPage(page, characterdict);
	}

	@Transactional(readOnly = false)
	public void save(Characterdict characterdict) {
		super.save(characterdict);
	}

	@Transactional(readOnly = false)
	public String findconByTwo(String[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return characterdictdao.findconByTwo(map).toString();
	}

	@Transactional(readOnly = false)
	public List<Map<String,Object>> findconMapByTwo(String[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return characterdictdao.findconMapByTwo(map);
	}

	@Transactional(readOnly = false)
	public void delete(Characterdict characterdict) {
		super.delete(characterdict);
	}

	@Transactional(readOnly = false)
	public int countCharacterByName(Map<String, Object> map) {
		return characterdictdao.countCharacterByName(map);
	}

}