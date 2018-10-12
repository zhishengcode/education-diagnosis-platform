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
import com.thinkgem.jeesite.modules.edu.entity.Conclusion;
import com.thinkgem.jeesite.modules.edu.service.ConclusionService;

/**
 * 结论Controller
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/conclusion")
public class ConclusionController extends BaseController {

	@Autowired
	private ConclusionService conclusionService;

	@ModelAttribute
	public Conclusion get(@RequestParam(required=false) String id) {
		Conclusion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = conclusionService.get(id);
		}
		if (entity == null){
			entity = new Conclusion();
		}
		return entity;
	}

	@RequiresPermissions("edu:conclusion:view")
	@RequestMapping(value = {"list", ""})
	public String list(Conclusion conclusion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Conclusion> page = conclusionService.findPage(new Page<Conclusion>(request, response), conclusion);
		model.addAttribute("page", page);
		return "modules/edu/conclusionList";
	}

	@RequiresPermissions("edu:conclusion:view")
	@RequestMapping(value = "form")
	public String form(Conclusion conclusion, Model model) {
		model.addAttribute("conclusion", conclusion);
		return "modules/edu/conclusionForm";
	}

	@RequiresPermissions("edu:conclusion:edit")
	@RequestMapping(value = "save")
	public String save(Conclusion conclusion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, conclusion)){
			return form(conclusion, model);
		}
		conclusionService.save(conclusion);
		addMessage(redirectAttributes, "保存结论成功");
		return "redirect:"+Global.getAdminPath()+"/edu/conclusion/?repage";
	}

	@RequiresPermissions("edu:conclusion:edit")
	@RequestMapping(value = "delete")
	public String delete(Conclusion conclusion, RedirectAttributes redirectAttributes) {
		conclusionService.delete(conclusion);
		addMessage(redirectAttributes, "删除结论成功");
		return "redirect:"+Global.getAdminPath()+"/edu/conclusion/?repage";
	}

}