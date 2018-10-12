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
import com.thinkgem.jeesite.modules.edu.entity.Onecategory;
import com.thinkgem.jeesite.modules.edu.service.OnecategoryService;

/**
 * 笔划类别Controller
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/onecategory")
public class OnecategoryController extends BaseController {

	@Autowired
	private OnecategoryService onecategoryService;

	@ModelAttribute
	public Onecategory get(@RequestParam(required=false) String id) {
		Onecategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = onecategoryService.get(id);
		}
		if (entity == null){
			entity = new Onecategory();
		}
		return entity;
	}

	@RequiresPermissions("edu:onecategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(Onecategory onecategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Onecategory> page = onecategoryService.findPage(new Page<Onecategory>(request, response), onecategory);
		model.addAttribute("page", page);
		return "modules/edu/onecategoryList";
	}

	@RequiresPermissions("edu:onecategory:view")
	@RequestMapping(value = "form")
	public String form(Onecategory onecategory, Model model) {
		model.addAttribute("onecategory", onecategory);
		return "modules/edu/onecategoryForm";
	}

	@RequiresPermissions("edu:onecategory:edit")
	@RequestMapping(value = "save")
	public String save(Onecategory onecategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, onecategory)){
			return form(onecategory, model);
		}
		onecategoryService.save(onecategory);
		addMessage(redirectAttributes, "保存分类成功");
		return "redirect:"+Global.getAdminPath()+"/edu/onecategory/?repage";
	}

	@RequiresPermissions("edu:onecategory:edit")
	@RequestMapping(value = "delete")
	public String delete(Onecategory onecategory, RedirectAttributes redirectAttributes) {
		onecategoryService.delete(onecategory);
		addMessage(redirectAttributes, "删除分类成功");
		return "redirect:"+Global.getAdminPath()+"/edu/onecategory/?repage";
	}

}