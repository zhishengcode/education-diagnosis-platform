package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.entity.PersonalityCharacteristics;
import com.thinkgem.jeesite.modules.edu.service.PersonalityCharacteristicsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.edu.entity.Characterdict;
import com.thinkgem.jeesite.modules.edu.service.CharacterdictService;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特征管理下的类别特征Controller
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/characterdict")
public class CharacterdictController extends BaseController {

	@Autowired
	private CharacterdictService characterdictService;
	@Autowired
	private PersonalityCharacteristicsService personalityCharacteristicsService;

	@ModelAttribute
	public Characterdict get(@RequestParam(required=false) String id) {
		Characterdict entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = characterdictService.get(id);
		}
		if (entity == null){
			entity = new Characterdict();
		}
		return entity;
	}

	@RequiresPermissions("edu:characterdict:view")
	@RequestMapping(value = {"list", ""})
	public String list(Characterdict characterdict, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Characterdict> page = characterdictService.findPage(new Page<Characterdict>(request, response), characterdict);
		model.addAttribute("page", page);
		return "modules/edu/characterdictList";
	}

	@RequiresPermissions("edu:characterdict:view")
	@RequestMapping(value = "form")
	public String form(Characterdict characterdict, Model model) {
		PersonalityCharacteristics pc=new PersonalityCharacteristics();
		List list=personalityCharacteristicsService.findList(pc);
		model.addAttribute("characterdict", characterdict);
		model.addAttribute("personalityList", list);
		return "modules/edu/characterdictForm";
	}

	@RequiresPermissions("edu:characterdict:edit")
	@RequestMapping(value = "save")
	public String save(Characterdict characterdict, Model model, RedirectAttributes redirectAttributes) {
		/**
		 * 用于判断是否有重复的
		 */
//		Map<String ,Object> map=new HashMap<String ,Object >();
//		map.put ("characterName",characterdict.getCharacterName());
//		if(characterdict.getId ()!=null)
//			map.put ("id",characterdict.getId ());
//		System.out.println("characterdictService.countCharacterByName(map)---"+characterdictService.countCharacterByName(map));
//		int count = 0;
//		count = characterdictService.countCharacterByName(map);
//		if(count>=1){
//			model.addAttribute("countCharacterByName", count);
//			model.addAttribute("characterdict", characterdict);
//			addMessage(redirectAttributes, "性格特征重复输入！");
//			return "redirect:"+Global.getAdminPath()+"/edu/characterdict/form";
//		}

		if (!beanValidator(model, characterdict)){
			return form(characterdict, model);
		}

		characterdictService.save(characterdict);
		addMessage(redirectAttributes, "保存类别特征成功");
		return "redirect:"+Global.getAdminPath()+"/edu/characterdict/?repage";
	}

	@RequiresPermissions("edu:characterdict:edit")
	@RequestMapping(value = "delete")
	public String delete(Characterdict characterdict, RedirectAttributes redirectAttributes) {
		characterdictService.delete(characterdict);
		addMessage(redirectAttributes, "删除类别特征成功");
		return "redirect:"+Global.getAdminPath()+"/edu/characterdict/?repage";
	}

	@RequiresPermissions("edu:characterdict:edit")
	@RequestMapping(value = "characterName")
	@ResponseBody
	public String characterName(String characterName, String id,HttpServletRequest request ) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("characterName", characterName);
		if (id != null)
			map.put("id", id);
		int count = 0;
		count = characterdictService.countCharacterByName(map);
		if (count >= 1) {
			return "false";
		} else {
			return "true";
		}
	}
}