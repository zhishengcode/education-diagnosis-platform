package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.edu.entity.CateCha;
import com.thinkgem.jeesite.modules.edu.service.CateChaService;

/**
 * 性格特征与笔划形态关联Controller
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/cateCha")
public class CateChaController extends BaseController {

	@Autowired
	private CateChaService cateChaService;

	@ModelAttribute
	public CateCha get(@RequestParam(required=false) String id) {
		CateCha entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cateChaService.get(id);
		}
		if (entity == null){
			entity = new CateCha();
		}
		return entity;
	}

	@RequiresPermissions("edu:cateCha:view")
	@RequestMapping(value = {"list", ""})
	public String list(CateCha cateCha, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CateCha> page = cateChaService.findPage(new Page<CateCha>(request, response), cateCha);
		model.addAttribute("page", page);
		return "modules/edu/cateChaList";
	}

	@RequiresPermissions("edu:cateCha:view")
	@RequestMapping(value = "form")
	public String form(CateCha cateCha, Model model) {
		model.addAttribute("cateCha", cateCha);
		return "modules/edu/cateChaForm";
	}

	@RequiresPermissions("edu:cateCha:edit")
	@RequestMapping(value = "save")
	public String save(CateCha cateCha, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cateCha)){
			return form(cateCha, model);
		}
		cateChaService.save(cateCha);
		addMessage(redirectAttributes, "保存性格特征与笔划形态关联成功");
		return "redirect:"+Global.getAdminPath()+"/edu/cateCha/?repage";
	}

	@RequiresPermissions("edu:cateCha:edit")
	@RequestMapping(value = "delete")
	public String delete(CateCha cateCha, RedirectAttributes redirectAttributes) {
		cateChaService.delete(cateCha);
		addMessage(redirectAttributes, "删除性格特征与笔划形态关联成功");
		return "redirect:"+Global.getAdminPath()+"/edu/cateCha/?repage";
	}

}