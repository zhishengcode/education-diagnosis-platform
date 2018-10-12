package com.thinkgem.jeesite.modules.edu.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Characterdict;
import com.thinkgem.jeesite.modules.edu.entity.PersonalityCharacteristics;
import com.thinkgem.jeesite.modules.edu.service.PersonalityCharacteristicsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特征管理下的特征维度
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/personalityCharacteristics")
public class PersonalityCharacteristicsController extends BaseController {
    @Autowired
    private PersonalityCharacteristicsService personalityCharacteristicsService;

    @RequiresPermissions("edu:personalityCharacteristics:view")
    @RequestMapping(value = {"list", ""})
    public String list(PersonalityCharacteristics personalityCharacteristics, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<PersonalityCharacteristics> page = personalityCharacteristicsService.findPage(new Page<PersonalityCharacteristics>(request, response), personalityCharacteristics);
        model.addAttribute("page", page);
        return "modules/edu/personalityCharacteristicsList";
    }
    @RequiresPermissions("edu:personalityCharacteristics:view")
    @RequestMapping(value = "form")
    public String form(String id,PersonalityCharacteristics personalityCharacteristics, Model model) {
        if(null !=id){
             personalityCharacteristics=personalityCharacteristicsService.get(id);
        }

        model.addAttribute("personalityCharacteristics", personalityCharacteristics);
        return "modules/edu/personalityCharacteristicsForm";
    }
    @RequiresPermissions("edu:personalityCharacteristics:edit")
    @RequestMapping(value = "characteristicName")
    @ResponseBody
    public String characterName(String characteristicName, String id,HttpServletRequest request ) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("characteristicName", characteristicName);
        if (id != null)
            map.put("id", id);
        int count = 0;
        count = personalityCharacteristicsService.countcharacteristicByName(map);
        if (count >= 1) {
            return "false";
        } else {
            return "true";
        }
    }
    @RequiresPermissions("edu:personalityCharacteristics:edit")
    @RequestMapping(value = "save")
    public String save(PersonalityCharacteristics personalityCharacteristics, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, personalityCharacteristics)){
            return form(personalityCharacteristics.getId(),personalityCharacteristics, model);
        }

        personalityCharacteristicsService.save(personalityCharacteristics);
        addMessage(redirectAttributes, "保存性格特征成功");
        return "redirect:"+ Global.getAdminPath()+"/edu/personalityCharacteristics/?repage";
    }
    @RequiresPermissions("edu:personalityCharacteristics:edit")
    @RequestMapping(value = "delete")
    public String delete(PersonalityCharacteristics personalityCharacteristics, RedirectAttributes redirectAttributes) {
        personalityCharacteristicsService.delete(personalityCharacteristics);
        addMessage(redirectAttributes, "删除性格特征成功");
        return "redirect:"+Global.getAdminPath()+"/edu/personalityCharacteristics/?repage";
    }

}
