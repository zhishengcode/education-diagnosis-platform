package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import com.thinkgem.jeesite.modules.edu.entity.Teacher;
import com.thinkgem.jeesite.modules.edu.service.TeacherService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 教师信息Controller
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/teacher")
public class TeacherController extends BaseController {

	@Autowired
	private TeacherService teacherService;

	@ModelAttribute
	public Teacher get(@RequestParam(required=false) String id) {
		Teacher entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = teacherService.get(id);
		}
		if (entity == null){
			entity = new Teacher();
		}
		return entity;
	}

	@RequiresPermissions("edu:teacher:view")
	@RequestMapping(value = {"list", ""})
	public String list(Teacher teacher, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Teacher> page = teacherService.findPage(new Page<Teacher>(request, response), teacher);
		model.addAttribute("page", page);
		return "modules/edu/teacherList";
	}

	@RequiresPermissions("edu:teacher:view")
	@RequestMapping(value = "form")
	public String form(Teacher teacher, Model model) {
		model.addAttribute("teacher", teacher);
		return "modules/edu/teacherForm";
	}

	@RequiresPermissions("edu:teacher:edit")
	@RequestMapping(value = "save")
	public String save(Teacher teacher, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, teacher)){
			return form(teacher, model);
		}
		teacher.setCreatetime(new Date());
		teacher.setStatus(0L);
		teacherService.save(teacher);
		addMessage(redirectAttributes, "保存教师信息成功");
		return "redirect:"+Global.getAdminPath()+"/edu/teacher/?repage";
	}

	@RequiresPermissions("edu:teacher:edit")
	@RequestMapping(value = "delete")
	public String delete(Teacher teacher, RedirectAttributes redirectAttributes) {
		teacherService.delete(teacher);
		addMessage(redirectAttributes, "删除教师信息成功");
		return "redirect:"+Global.getAdminPath()+"/edu/teacher/?repage";
	}

	/**
	 * 获取教师的JSON数据。
	 * @param techId 排除的ID
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String techId,
											  @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Teacher> list = teacherService.findList(null);
		for (int i=0; i<list.size(); i++){
			Teacher e = list.get(i);
			if (StringUtils.isBlank(techId) || techId!=null && !techId.equals(e.getId())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}