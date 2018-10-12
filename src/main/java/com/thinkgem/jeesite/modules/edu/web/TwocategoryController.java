package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.modules.edu.entity.*;
import com.thinkgem.jeesite.modules.edu.service.*;
import org.apache.commons.beanutils.PropertyUtils;
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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 笔划形态Controller
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/twocategory")
public class TwocategoryController extends BaseController {

	@Autowired
	private OnecategoryService onecategoryService;

	@Autowired
	private TwocategoryService twocategoryService;

	@Autowired
	private CharacterdictService characterdictService;
	@Autowired
	private CateChaService cateChaService;
	@Autowired
	private EduQualifierService eduQualifierService;

	@ModelAttribute
	public Twocategory get(@RequestParam(required=false) String id) {
		Twocategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = twocategoryService.get(id);
			CateCha cateCha =new CateCha();
			cateCha.setCategoryId(id);
			List charalist=cateChaService.findList(cateCha);
			entity.setCharalist( Collections3.extractToString(charalist, "characterId", ","));
		}
		if (entity == null){
			entity = new Twocategory();
		}
		return entity;
	}

	@RequiresPermissions("edu:twocategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(Twocategory twocategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Twocategory> page = twocategoryService.findPage(new Page<Twocategory>(request, response), twocategory);
		model.addAttribute("page", page);
		return "modules/edu/twocategoryList";
	}

	@RequiresPermissions("edu:twocategory:view")
	@RequestMapping(value = "form")
	public String form(Twocategory twocategory, Model model) {
		//笔划类别列表
		List onelist=onecategoryService.findList(new Onecategory());
		model.addAttribute("onelist",onelist);
		//allchara 性格特征列表
		List allchara=characterdictService.findList(new Characterdict());
		model.addAttribute("allchara", allchara);
		if(StringUtils.isNotBlank(twocategory.getId())){
			CateCha cateCha =new CateCha();
			cateCha.setCategoryId(twocategory.getId());
			List charalist=cateChaService.findList(cateCha);
			twocategory.setCharalist( Collections3.extractToString(charalist, "characterId", ","));
			String refi="";
			for(Object obj:charalist){
				try {
					refi+= PropertyUtils.getProperty(obj, "characterId")+";"+PropertyUtils.getProperty(obj, "eduqualifierId")+",";

				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}

			}
//			twocategory.setEduqualifierlist(refi.substring(0,refi.length()-1));
		}

		model.addAttribute("twocategory", twocategory);
//		model.addAttribute("eduqualifierlist", twocategory.getEduqualifierlist());
//		EduQualifier qualifier=new EduQualifier();
//		List eduQualifierList= eduQualifierService.findList(qualifier);
//		model.addAttribute("eduQualifierList", eduQualifierList);
		return "modules/edu/twocategoryForm";
	}

	@RequiresPermissions("edu:twocategory:edit")
	@RequestMapping(value = "save")
	public String save(Twocategory twocategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, twocategory)){
			return form(twocategory, model);
		}
		if(StringUtils.isBlank(twocategory.getId())){
			twocategory.preInsert();
			twocategory.setIsNewRecord(true);
		}
		String[] a=twocategory.getCharalist().split(",");
//		String[] a=twocategory.getEduqualifierlist().split(",");
		twocategoryService.save(twocategory);
		cateChaService.delete(twocategory.getId());
		for(int i=0;i<a.length;i++)
		{
			String[] fiers=a[i].split(";");
			CateCha chcha=new CateCha();
			chcha.setCategoryId(twocategory.getId());
			chcha.setCharacterId(fiers[0]);
//			chcha.setEduqualifierId(fiers[1]);
			cateChaService.save(chcha);
		}
		addMessage(redirectAttributes, "保存笔划形态成功");
		return "redirect:"+Global.getAdminPath()+"/edu/twocategory/?repage";
	}

	@RequiresPermissions("edu:twocategory:edit")
	@RequestMapping(value = "delete")
	public String delete(Twocategory twocategory, RedirectAttributes redirectAttributes) {
		twocategoryService.delete(twocategory);
		addMessage(redirectAttributes, "删除笔划形态成功");
		return "redirect:"+Global.getAdminPath()+"/edu/twocategory/?repage";
	}


}