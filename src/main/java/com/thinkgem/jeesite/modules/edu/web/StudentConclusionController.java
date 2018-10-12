package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.office.CustomXWPFDocument;
import com.thinkgem.jeesite.common.pdf.Converter;
import com.thinkgem.jeesite.common.pdf.DocUtil;
import com.thinkgem.jeesite.common.office.XwpfTUtil;
import com.thinkgem.jeesite.common.pdf.OfficeToPDF;
import com.thinkgem.jeesite.modules.edu.entity.*;
import com.thinkgem.jeesite.modules.edu.service.*;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * 结论Controller
 * @author ShawnXiang
 * @version 2018-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/studentConclusion")
public class StudentConclusionController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(StudentConclusionController.class);

	@Autowired
	private StudentConclusionService studentConclusionService;
	@Autowired
	private OnecategoryService onecategoryService;
	@Autowired
	private CharacterdictService characterdictService;
	@Autowired
	private ConclusionTwoCategoryRelateService conclusionTwoCategoryRelateService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private PersonalityCharacteristicsService personalityCharacteristicsService;

    Converter converter;

	/*@Autowired
	private ConclusionService conclusionService;*/
	@ModelAttribute
	public StudentConclusion get(@RequestParam(required=false) String id) {
		StudentConclusion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentConclusionService.get(id);
		}
		if (entity == null){
			entity = new StudentConclusion();
		}
		return entity;
	}

	@RequiresPermissions("edu:studentConclusion:view")
	@RequestMapping(value = {"list", ""})
	public String list(StudentConclusion studentConclusion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentConclusion> page = studentConclusionService.findPageByConclusion(new Page<StudentConclusion>(request, response), studentConclusion);
		model.addAttribute("page", page);
		return "modules/edu/studentConclusionList";
	}

	@RequiresPermissions("edu:studentConclusion:view")
	@RequestMapping(value = "form")
	public String form(StudentConclusion studentConclusion, Model model) {
		if(studentConclusion.getTeacher()==null){
			studentConclusion.setTeacher(UserUtils.getUser());
		}
		model.addAttribute("studentConclusion", studentConclusion);
		List<Onecategory> onelist=onecategoryService.findList(new Onecategory());
		model.addAttribute("onelist",onelist);
		if(studentConclusion.getEchartsScore()!=null){
			System.out.println(""+studentConclusion.getEchartsScore());
			System.out.println("echartsScore in form->" + JSON.toJSONString(studentConclusion.getEchartsScore()));
			model.addAttribute("echartsScore",JSON.toJSONString(studentConclusion.getEchartsScore()));
		}

//		model.addAttribute("initScoreJsons",scoreJson);
		return "modules/edu/studentConclusionForm";
	}

	@RequiresPermissions("edu:studentConclusion:edit")
	@RequestMapping(value = "save")
	public String save(StudentConclusion studentConclusion, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, studentConclusion)){
			return form(studentConclusion, model);
		}

		String id = request.getParameter("id");
		String[] ids = request.getParameterValues("values");
		StringBuffer sb = new StringBuffer();
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < ids.length; i++){
			sb.append(ids[i]);
			sb.append(",");
			list.add(ids[i]);
		}
		List<Map<String,Object>> conMapList = characterdictService.findconMapByTwo(ids);
		for(Map<String,Object> conMap:conMapList){
			conMap.containsKey("");
		}
		String con=characterdictService.findconByTwo(ids);
		String aa=con.substring(1);
		studentConclusion.setCharactervalues(StringUtils.join(ids, ","));
		studentConclusion.setConclustion(aa.substring(0,aa.length()-1));
		studentConclusion.setCreatetime(new Date());
		studentConclusion.setStatus(0L);
		studentConclusion.setOrgId(UserUtils.getUser().getOffice().getId());
//		studentConclusionService.save(studentConclusion);

		for(int i = 0; i < ids.length; i++){
			ConclusionTwoCategoryRelate conclusionTwoCategoryRelate = new ConclusionTwoCategoryRelate();
			conclusionTwoCategoryRelate.setConclusionId(studentConclusion.getId());
			conclusionTwoCategoryRelate.setTwoCategoryId(ids[i]);
			conclusionTwoCategoryRelateService.save(conclusionTwoCategoryRelate);
			conclusionTwoCategoryRelate = null;
		}

		String basePdfPath = Global.getConfig("basePdfPath");
		String pdfFilesPath = request.getSession().getServletContext().getRealPath("/")+basePdfPath+"/";

        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmm");
        String conclusionName = studentConclusion.getStudent()+"_conclusion_"+sdf.format(new Date());
        String filePdfName = conclusionName + ".pdf";
        String fileDocName = conclusionName + ".doc";

        Map<String, Object> map = new HashMap<String, Object>();


        //测评编号
        map.put("${no}", studentConclusion.getStudent());
		map.put("${noRadar}", studentConclusion.getStudent());
		map.put("${noCateg}", studentConclusion.getStudent());
		map.put("${noCour1}",studentConclusion.getStudent());
		map.put("${noCour2}",studentConclusion.getStudent());
		map.put("${noFam}",studentConclusion.getStudent());
        //测评老师
        User user = systemService.getUser(studentConclusion.getTeacher().getId());
		String teacherName = "平台管理员";
        if(user!=null){
			teacherName = user.getName();
		}
        map.put("${teacher}", teacherName);
		map.put("${teacherRadar}", teacherName);
		map.put("${teaCateg}", teacherName);
		map.put("${teaCour1}", teacherName);
		map.put("${teaCour2}", teacherName);
		map.put("${teaFam}", teacherName);

        //测评日期
        SimpleDateFormat createTimeSimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String createTime =  createTimeSimpleDateFormat.format(studentConclusion.getCreatetime());
        map.put("${date}",createTime);
		map.put("${dateRadar}",createTime);
		map.put("${dateCateg}",createTime);
		map.put("${dateCour1}",createTime);
		map.put("${dateCour2}",createTime);
		map.put("${dateFam}",createTime);
        //测评学生姓名
        map.put("${student}",studentConclusion.getConclusionName());
		map.put("${stuCour1}",studentConclusion.getConclusionName());
		map.put("${stuCour2}",studentConclusion.getConclusionName());
		map.put("${stuFam}",studentConclusion.getConclusionName());

        //学校名称
        if(studentConclusion.getSchool()!=null){
			map.put("${school}",studentConclusion.getSchool());
		}else{
			map.put("${school}","");
		}
		//班级名称
		String grade = "";
		if(studentConclusion.getGrade().equals("0")){
			grade = "一年级学生";
		}else if(studentConclusion.getGrade().equals("1")){
			grade = "二年级学生";
		}else if(studentConclusion.getGrade().equals("2")) {
			grade = "三年级学生";
		}else if(studentConclusion.getGrade().equals("3")) {
			grade = "四年级学生";
		}else if(studentConclusion.getGrade().equals("4")) {
			grade = "五年级学生";
		}else if(studentConclusion.getGrade().equals("5")) {
			grade = "六年级学生";
		}else if(studentConclusion.getGrade().equals("6")) {
			grade = "初一学生";
		}else if(studentConclusion.getGrade().equals("7")) {
			grade = "初二学生";
		}else if(studentConclusion.getGrade().equals("8")) {
			grade = "初三学生";
		}else if(studentConclusion.getGrade().equals("9")) {
			grade = "高一学生";
		}else if(studentConclusion.getGrade().equals("10")) {
			grade = "高二学生";
		}else if(studentConclusion.getGrade().equals("11")) {
			grade = "高三学生";
		}
		if(studentConclusion.getGrade()!=null){
			map.put("${grade}",grade);
			map.put("${grade1}",grade);
			map.put("${gradeFam}",grade);
		}else{
			map.put("${grade}","");
		}
		map.put("${studentRadar}",studentConclusion.getConclusionName());
		map.put("${stuCateg}",studentConclusion.getConclusionName());
        if(studentConclusion.getAge()!=null){
			map.put("${ageRadar}",studentConclusion.getAge());
			map.put("${ageCateg}",studentConclusion.getAge());
			map.put("${ageCour1}",studentConclusion.getAge());
			map.put("${ageCour2}",studentConclusion.getAge());
			map.put("${ageFam}",studentConclusion.getAge());
		}else{
			map.put("${ageRadar}","");
			map.put("${ageCateg}","");
			map.put("${ageCour1}","");
			map.put("${ageCour2}","");
			map.put("${ageFam}","");
		}


		//Begin-读取雷达图图片信息，返回图片保存路径，并插入到图像分析结果的表格中
		String echartsPicPath = Global.getConfig("echartsPicPath");
		String echartsPicFilePath = request.getSession().getServletContext().getRealPath("/")+echartsPicPath+"/";

		XwpfTUtil xwpfTUtil = new XwpfTUtil();
		String picBase64Info = request.getParameter("echartsPic");
		String twocategoryIdlist = request.getParameter("twocategoryIdlist");
		System.out.println("twocategoryIdlist=="+twocategoryIdlist);
		picBase64Info = picBase64Info.replaceAll(" ", "+");
		Map<String,Object> echartMap = getEcharts(echartsPicFilePath,picBase64Info);
		map.put("${radar}",echartMap);

		String picColBase64Info = request.getParameter("columnEchartsPic");
		picColBase64Info = picColBase64Info.replaceAll(" ", "+");
		Map<String,Object> colEchartMap = getEcharts(echartsPicFilePath,picColBase64Info);
		map.put("${column}",colEchartMap);
		//End-读取雷达图图片信息，返回图片保存路径，并插入到图像分析结果的表格中

		//保存雷达图和正负条形图的分值信息组合信息，用于编辑并打开时候的初始化;
		String recordEchartsJSON = getEditRecordEchartsJSON(studentConclusion.getCharactervalues());
		//拆分平均分数
		Map<String, Object> splitAvgMap = parseObject(recordEchartsJSON);
		String diagnosisAverageScore = "";
		for (Object obj : splitAvgMap.keySet()){
			if(obj.toString().equals("diagnosisAvgScore")){
				String splitStr = splitAvgMap.get(obj).toString();
				diagnosisAverageScore = splitStr.substring(splitStr.indexOf(":\"")+2,splitStr.length()-2);
			}
		}
		System.out.println("诊断平均分数值，diagnosisAverageScore:" + diagnosisAverageScore);
		map.put("${avgRadar}",diagnosisAverageScore);
		System.out.println("recordEchartsJSON->" + recordEchartsJSON);
		studentConclusion.setEchartsScore(recordEchartsJSON);
		studentConclusionService.save(studentConclusion);

        //测评结论
		List<Map<String,Object>> characterListMap = studentConclusionService.selectConclusionByCharacterIds(list);
		String dimension;
		Set<String> characterSet = new HashSet<String>();
		for(Map<String,Object> sMap:characterListMap){
			dimension = Global.getConfig("dimension"+sMap.get("sort").toString());
			if(sMap.containsKey("characterName") && sMap.containsValue( dimension)){
				for (String key : sMap.keySet())
				{
					if(key!=null && key.equals("characterDimension")){
						System.out.println("标签===="+"${dimension"+sMap.get("sort").toString()+"}");
						map.put("${dimension"+sMap.get("sort").toString()+"}", sMap.get("sort").toString()+"、"+dimension+"："+filterNumber(sMap.get("characterDimension").toString()));
						System.out.println(sMap.get("sort").toString()+"、"+dimension+"====="+filterNumber(sMap.get("characterDimension").toString()));
						characterSet.add(filterNumber(sMap.get("characterDimension").toString()));
					}

				}
			}
		}

		String characterValue = StringUtils.join(characterSet.toArray(), ";");
		String templatePath = "";
		if(isOSLinux()){
			templatePath = Global.getConfig("downLoadConclusionTemplateByLinuxPath");
		}else{
			templatePath = Global.getConfig("downLoadConclusionTemplatePath");
		}
		System.out.println("templatePath-->" + templatePath);
		System.out.println("characterValue===>" + characterValue);

        String source = characterValue;
        //社交型学生（A型）关键词频率
        int socialFrequency = getTotalFrequency(source, Global.getConfig("socialKey"));
        //活动型学生（B型）关键词频率
        int activityFrequency = getTotalFrequency(source,Global.getConfig("activityKey"));
        //慎重型学生（C型）关键词频率
        int conscientiousFrequency = getTotalFrequency(source,Global.getConfig("conscientiousKey"));
        //依赖型学生（D型）关键词频率
        int dependentFrequency = getTotalFrequency(source,Global.getConfig("dependentKey"));
        //自卑型学生（E型)关键词频率
        int inferiorityFrequency = getTotalFrequency(source,Global.getConfig("inferiorityKey"));
        //认真型学生（F型)关键词频率
        int careFullFrequency = getTotalFrequency(source,Global.getConfig("careFullKey"));

        Map<String,Integer> characterTypeMap = new LinkedHashMap<String, Integer>();
        characterTypeMap.put(Global.getConfig("socialType"),socialFrequency);
        characterTypeMap.put(Global.getConfig("activityType"),activityFrequency);
        characterTypeMap.put(Global.getConfig("conscientiousType"),conscientiousFrequency);
        characterTypeMap.put(Global.getConfig("dependentType"),dependentFrequency);
        characterTypeMap.put(Global.getConfig("inferiorityType"),inferiorityFrequency);
        characterTypeMap.put(Global.getConfig("careFullType"),careFullFrequency);
        String characterType = getMaxKeyMap(characterTypeMap);
        System.out.println("characterType->" + characterType);
        String socialType = Global.getConfig("socialType");
        String activityType = Global.getConfig("activityType");
        String conscientiousType = Global.getConfig("conscientiousType");
        String dependentType = Global.getConfig("dependentType");
        String inferiorityType = Global.getConfig("inferiorityType");
        String careFullType = Global.getConfig("careFullType");
        if(characterType.equals(socialType)){
            if(isOSLinux()){
				if(studentConclusion.getGrade().equals("0")) {
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath1");
				}else if(studentConclusion.getGrade().equals("1")){
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath2");
                }else if(studentConclusion.getGrade().equals("2")){
                    templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath3");
				}else if(studentConclusion.getGrade().equals("3")){
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath4");
				}else if(studentConclusion.getGrade().equals("4")){
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath5");
				}else if(studentConclusion.getGrade().equals("5")){
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath6");
				}else if(studentConclusion.getGrade().equals("6")){
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath7");
				}else if(studentConclusion.getGrade().equals("7")){
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath8");
				}else if(studentConclusion.getGrade().equals("8")){
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath9");
				}else if(studentConclusion.getGrade().equals("9")){
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath10");
				}else if(studentConclusion.getGrade().equals("10")){
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath11");
				}else if(studentConclusion.getGrade().equals("11")){
					templatePath = Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath12");
                }else{
					templatePath= Global.getConfig("downLoadConclusionSocialTemplateByLinuxPath");
				}

            }else{
				if(studentConclusion.getGrade().equals("0")) {
					templatePath = Global.getConfig("downLoadConclusionSocialTemplatePath1");
				}else{
					templatePath = Global.getConfig("downLoadConclusionSocialTemplatePath");
				}
            }
			map.put("${category}","社交型学生");
        }else if(characterType.equals(activityType)){
            if(isOSLinux()){
				if(studentConclusion.getGrade().equals("0")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath1");
				}else if(studentConclusion.getGrade().equals("1")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath2");
                }else if(studentConclusion.getGrade().equals("2")){
                    templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath3");
				}else if(studentConclusion.getGrade().equals("3")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath4");
				}else if(studentConclusion.getGrade().equals("4")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath5");
				}else if(studentConclusion.getGrade().equals("5")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath6");
				}else if(studentConclusion.getGrade().equals("6")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath7");
				}else if(studentConclusion.getGrade().equals("7")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath8");
				}else if(studentConclusion.getGrade().equals("8")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath9");
				}else if(studentConclusion.getGrade().equals("9")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath10");
				}else if(studentConclusion.getGrade().equals("10")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath11");
				}else if(studentConclusion.getGrade().equals("11")){
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath12");
				}else{
					templatePath= Global.getConfig("downLoadConclusionActivityTemplateByLinuxPath");
				}
            }else{
                templatePath= Global.getConfig("downLoadConclusionActivityTemplatePath");
            }
			map.put("${category}","活动型学生");
        }else if(characterType.equals(conscientiousType)){
            if(isOSLinux()){
				if(studentConclusion.getGrade().equals("0")) {
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath1");
				}else if(studentConclusion.getGrade().equals("1")){
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath2");
                }else if(studentConclusion.getGrade().equals("2")){
                    templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath3");
				}else if(studentConclusion.getGrade().equals("3")){
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath4");
				}else if(studentConclusion.getGrade().equals("4")){
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath5");
				}else if(studentConclusion.getGrade().equals("5")){
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath6");
				}else if(studentConclusion.getGrade().equals("6")){
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath7");
				}else if(studentConclusion.getGrade().equals("7")){
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath8");
				}else if(studentConclusion.getGrade().equals("8")){
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath9");
				}else if(studentConclusion.getGrade().equals("9")){
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath10");
				}else if(studentConclusion.getGrade().equals("10")){
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath11");
				}else if(studentConclusion.getGrade().equals("11")){
					templatePath = Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath12");
				}else{
					templatePath= Global.getConfig("downLoadConclusionConscientiousTemplateByLinuxPath");
				}
            }else{
                templatePath= Global.getConfig("downLoadConclusionConscientiousTemplatePath");
            }
			map.put("${category}","慎重型学生");
        }else if(characterType.equals(dependentType)){
            if(isOSLinux()){
				if(studentConclusion.getGrade().equals("0")) {
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath1");
				}else if(studentConclusion.getGrade().equals("1")){
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath2");
                }else if(studentConclusion.getGrade().equals("2")){
                    templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath3");
				}else if(studentConclusion.getGrade().equals("3")){
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath4");
				}else if(studentConclusion.getGrade().equals("4")){
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath5");
				}else if(studentConclusion.getGrade().equals("5")){
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath6");
				}else if(studentConclusion.getGrade().equals("6")){
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath7");
				}else if(studentConclusion.getGrade().equals("7")){
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath8");
				}else if(studentConclusion.getGrade().equals("8")){
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath9");
				}else if(studentConclusion.getGrade().equals("9")){
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath10");
				}else if(studentConclusion.getGrade().equals("10")){
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath11");
				}else if(studentConclusion.getGrade().equals("11")){
					templatePath = Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath12");
				}else{
					templatePath= Global.getConfig("downLoadConclusionDependentTemplateByLinuxPath");
				}
            }else{
                templatePath= Global.getConfig("downLoadConclusionDependentTemplatePath");
            }
			map.put("${category}","依赖型学生");
        }else if(characterType.equals(inferiorityType)){
            if(isOSLinux()){
				if(studentConclusion.getGrade().equals("0")) {
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath1");
				}else if(studentConclusion.getGrade().equals("1")){
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath2");
                }else if(studentConclusion.getGrade().equals("2")){
                    templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath3");
				}else if(studentConclusion.getGrade().equals("3")){
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath4");
				}else if(studentConclusion.getGrade().equals("4")){
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath5");
				}else if(studentConclusion.getGrade().equals("5")){
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath6");
				}else if(studentConclusion.getGrade().equals("6")){
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath7");
				}else if(studentConclusion.getGrade().equals("7")){
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath8");
				}else if(studentConclusion.getGrade().equals("8")){
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath9");
				}else if(studentConclusion.getGrade().equals("9")){
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath10");
				}else if(studentConclusion.getGrade().equals("10")){
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath11");
				}else if(studentConclusion.getGrade().equals("11")){
					templatePath = Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath12");
				}else{
					templatePath= Global.getConfig("downLoadConclusionInferiorityTemplateByLinuxPath");
				}
            }else{
                templatePath= Global.getConfig("downLoadConclusionInferiorityTemplatePath");
            }
			map.put("${category}","自卑型学生");
        }else if(characterType.equals(careFullType)){
            if(isOSLinux()){
				if(studentConclusion.getGrade().equals("0")) {
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath1");
				}else if(studentConclusion.getGrade().equals("1")){
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath2");
                }else if(studentConclusion.getGrade().equals("2")){
                    templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath3");
				}else if(studentConclusion.getGrade().equals("3")){
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath4");
				}else if(studentConclusion.getGrade().equals("4")){
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath5");
				}else if(studentConclusion.getGrade().equals("5")){
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath6");
				}else if(studentConclusion.getGrade().equals("6")){
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath7");
				}else if(studentConclusion.getGrade().equals("7")){
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath8");
				}else if(studentConclusion.getGrade().equals("8")){
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath9");
				}else if(studentConclusion.getGrade().equals("9")){
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath10");
				}else if(studentConclusion.getGrade().equals("10")){
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath11");
				}else if(studentConclusion.getGrade().equals("11")){
					templatePath = Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath12");
				}else{
					templatePath= Global.getConfig("downLoadConclusionCareFullTemplateByLinuxPath");
				}
            }else{
                templatePath= Global.getConfig("downLoadConclusionCareFullTemplatePath");
            }
			map.put("${category}","认真型学生");
        }else{
            map.put("${category}","");
        }
		//获取word模板并读入到流中
//		String templatePath = Global.getConfig("downLoadConclusionTemplatePath");
        InputStream is = null;
        try {
            is = new FileInputStream(templatePath);
        } catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e.getMessage());
            e.printStackTrace();
        }

//		CustomXWPFDocument doc = new CustomXWPFDocument(is);
		CustomXWPFDocument doc = null;
        try {
            doc = new CustomXWPFDocument(is);
        } catch (IOException e) {
			logger.error("XWPFDocument异常", e.getMessage());
            e.printStackTrace();
        }

//
//		xwpfTUtil.replaceInPara(doc, map);    //替换文本里面的变量
		xwpfTUtil.replaceInTable(doc, map);

        FileOutputStream out;
        try {
            out = new FileOutputStream(pdfFilesPath+fileDocName);
			logger.debug("pdfFilesPath+fileDocName----begin-----"+pdfFilesPath+fileDocName);
            doc.write(out);
            out.close();
			logger.debug("pdfFilesPath+fileDocName----end-----"+pdfFilesPath+fileDocName);
        }catch (IOException e){
			logger.error("创建word模板文档失败", e.getMessage());
            e.printStackTrace();
        }

        String inDocPath = pdfFilesPath + fileDocName;
        String outPdfPath = pdfFilesPath + filePdfName;

		logger.debug("word转换为pdf-----开始-------");
		if(isOSLinux()){
			//word转换为pdf--Linux
			System.out.println("word转换为pdf--Linux");
			try {
				System.out.println("inDocPath-linux>" + inDocPath);
				System.out.println("outPdfPath-linux>" + outPdfPath);
				OfficeToPDF.officeToPDF(inDocPath,outPdfPath);
			} catch (FileNotFoundException e) {
				logger.error("word转换为pdf异常", e.getMessage());
			}
		}else{
			//word转换为pdf--Windowx
			System.out.println("inDocPath-windowx>" + inDocPath);
			System.out.println("outPdfPath-windowx>" + outPdfPath);
			System.out.println("word转换为pdf--Windowx");
			DocUtil.transfer(inDocPath,outPdfPath);
		}
		logger.debug("word转换为pdf-----结束-------");

		logger.debug("生成结论成功");
        addMessage(redirectAttributes, "生成结论成功");
        model.addAttribute("filePdfName", filePdfName);
		return "modules/edu/studentConclusionPDF";

	}

	@RequiresPermissions("edu:studentConclusion:edit")
	@RequestMapping(value = "getEchartsJSON", method = RequestMethod.POST)
	@ResponseBody
	public String getEchartsJSON(@RequestBody JSONObject requestJson) {
		String categoryStr = requestJson.getString("categoryJson");
		return getEditRecordEchartsJSON(categoryStr);
	}

    @RequiresPermissions("edu:studentConclusion:edit")
    @RequestMapping(value = "pdfStreamHandeler/{filePdfName:.+}")
    @ResponseBody
    public void pdfStreamHandeler(@PathVariable("filePdfName") String filePdfName, HttpServletRequest request, HttpServletResponse response) {
        String basePdfPath = Global.getConfig("basePdfPath");
        String pdfFilesPath = request.getSession().getServletContext().getRealPath("/")+basePdfPath+"/";
        String filePath = pdfFilesPath + filePdfName;

        File file = new File(filePath);
        byte[] data = null;
        try {
            FileInputStream input = new FileInputStream(file);
            data = new byte[input.available()];
            input.read(data);
            response.getOutputStream().write(data);
            input.close();
        } catch (Exception e) {
            logger.error("pdf文件处理异常：" + e.getMessage());
        }
    }

    @RequiresPermissions("edu:studentConclusion:edit")
	@RequestMapping(value = "delete")
	public String delete(StudentConclusion studentConclusion, RedirectAttributes redirectAttributes) {
		studentConclusionService.delete(studentConclusion);
		addMessage(redirectAttributes, "删除结论成功");
		return "redirect:"+Global.getAdminPath()+"/edu/studentConclusion/?repage";
	}

	@RequiresPermissions("edu:studentConclusion:edit")
	@RequestMapping(value = "validateStudentNo")
	@ResponseBody
	public String validateStudentNo(String studentNo, String id,HttpServletRequest request ) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("studentNo", studentNo);
		if (id != null)
			map.put("id", id);
		int count = 0;
		count = studentConclusionService.countStudentConclusionByNo(map);
		if (count >= 1) {
			return "false";
		} else {
			return "true";
		}
	}

	/**
	 * 测评列表页面中的下载结论
	 * @param studentConclusion
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequiresPermissions("edu:studentConclusion:view")
	@RequestMapping(value = "downLoad")
	public void downLoad(StudentConclusion studentConclusion, RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response) throws IOException{
		studentConclusion = studentConclusionService.get(studentConclusion.getId());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmm");
		String fileName = studentConclusion.getStudent()+"_测评结论_"+sdf.format(new Date())+".docx";

		Map<String, Object> map = new HashMap<String, Object>();
		//测评编号
		map.put("${no}", studentConclusion.getStudent());
		//测评老师
		User user = systemService.getUser(studentConclusion.getTeacher().getId());
		String teacherName = user.getName();
		map.put("${teacher}", teacherName);
		//测评日期
		SimpleDateFormat createTimeSimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String createTime =  createTimeSimpleDateFormat.format(studentConclusion.getCreatetime());
		map.put("${date}",createTime);
		//测评学生姓名
		map.put("${student}",studentConclusion.getConclusionName());
		//测评结论
		map.put("${content}", studentConclusion.getConclustion());

		String templatePath = Global.getConfig("downLoadConclusionTemplatePath");
		InputStream is = new FileInputStream(templatePath);
//		XWPFDocument doc = new XWPFDocument(is);
		CustomXWPFDocument doc = new CustomXWPFDocument(is);
		XwpfTUtil xwpfTUtil = new XwpfTUtil();
//		xwpfTUtil.replaceInPara(doc, map);
		xwpfTUtil.replaceInTable(doc, map);
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setContentType("bin");
		fileName = URLEncoder.encode(fileName,"UTF-8");
		response.setHeader("Content-disposition","attachment;filename="+fileName);
		doc.write(os);
		xwpfTUtil.close(os);
		xwpfTUtil.close(is);
		os.flush();
		os.close();
	}

	/**
	 *
	 * @param inputFilePath
	 * @return
	 * @throws FileNotFoundException
	 */
    protected static InputStream getInFileStream(String inputFilePath) throws FileNotFoundException{
        File inFile = new File(inputFilePath);
        FileInputStream iStream = new FileInputStream(inFile);
        return iStream;
    }

	/**
	 *
	 * @param outputFilePath
	 * @return
	 * @throws IOException
	 */
    protected static OutputStream getOutFileStream(String outputFilePath) throws IOException{
        File outFile = new File(outputFilePath);
        try{
            outFile.getParentFile().mkdirs();
        } catch (NullPointerException e){
        }
        outFile.createNewFile();
        FileOutputStream oStream = new FileOutputStream(outFile);
        return oStream;
    }

	/**
	 *
	 * @param base64Info
	 * @param echartsPicFilePath
	 * @return
	 */
	private String decodeBase64(String base64Info,String echartsPicFilePath){
		if(StringUtils.isEmpty(base64Info)){
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		String[] arr = base64Info.split("base64,");
		// 数据雷达图中：data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABI4AAAEsCAYAAAClh/jbAAA ...  在"base64,"之后的才是图片信息
		String picPath = echartsPicFilePath+ UUID.randomUUID().toString() +".png";
        // 如果目标路径不存在, 则新建该路径
        File outputFile = new File(picPath);
        if (!outputFile.getParentFile().exists()) {
            System.out.print("如果目标路径不存在, 则新建该路径");
            outputFile.getParentFile().mkdirs();
        }
		try {
			byte[] buffer = decoder.decodeBuffer(arr[1]);
			OutputStream os = new FileOutputStream(picPath);
			os.write(buffer);
			os.close();
		} catch (IOException e) {
			throw new RuntimeException();
		}
		return picPath;
	}

	/**
	 *
	 * @param number
	 * @return
	 */
	private static String filterNumber(String number)
	{
		number = number.replaceAll("[(0-9)]", "");
		return number;
	}

	/**
	 *
	 * @param dimensionConfigMap
	 * @param scoreList
	 * @return
	 */
	private Map<String,String> scoreMap(Map<String,String> dimensionConfigMap,List<Map<String,Object>> scoreList){
		Map<String,String> scoreMap = new LinkedHashMap<String, String>();
		for (String dimensionConfig : dimensionConfigMap.values()) {
			for(Map<String,Object> sMap:scoreList){
				String dimension = sMap.get("dimension").toString();
				String score = removeTrim(sMap.get("score").toString());
				if(sMap.containsValue( dimensionConfig)){
//					tempTotal = tempTotal +Integer.parseInt(score);
					scoreMap.put(dimension,score);
				}
			}
		}
//		tempTotal = tempTotal/5;
//		System.out.println("tempTotal==2018-05-04===*******==" + tempTotal);
		return scoreMap;
	}

	/**
	 *
	 * @param dimensionConfigMap
	 * @param scoreMap
	 * @return
	 */
	private Map<String,String> sortMap(Map<String,String> dimensionConfigMap,Map<String,String> scoreMap) {
		Map<String, String> sortMap = new LinkedHashMap<String, String>();
		for (Map.Entry<String, String> svo : dimensionConfigMap.entrySet()) {
			if (!scoreMap.containsKey(svo.getValue())) {
//				System.out.println("sortMap function in StudentConclusion svo.getValue()->" + svo.getValue());
				sortMap.put(svo.getValue(), "0");
			} else {
				for (Map.Entry<String, String> vo : scoreMap.entrySet()) {
//					System.out.println("sortMap function in StudentConclusion vo.getKey()->" + vo.getKey() + "||||" + removeTrim(vo.getValue()));
					sortMap.put(vo.getKey(), removeTrim(vo.getValue()));
				}
			}

		}
		return sortMap;
	}

	/**
	 * 读取雷达图图片信息，返回图片保存路径
	 * @param echartsPicFilePath
	 * @param picBase64Info
	 * @return
	 */
	private Map<String,Object> getEcharts(String echartsPicFilePath,String picBase64Info){
		Map<String,Object> echartMap = new HashMap<String, Object>();
		String picPath = decodeBase64(picBase64Info, echartsPicFilePath);
		if(picPath!=null){
			try {
				echartMap.put("width",500);
				echartMap.put("height", 300);
				echartMap.put("type", "png");
				echartMap.put("content", XwpfTUtil.inputStream2ByteArray(new FileInputStream(picPath), true));
			}catch (Exception e){
				e.printStackTrace();
			}
		}else{
			return echartMap;
		}

		return echartMap;
	}

	public String getEditRecordEchartsJSON(String category) {
		String[] categoryIds = category.split(",");
		StringBuffer sb = new StringBuffer();
		List<String> categoryList = new ArrayList<String>();
		for(int i = 0; i < categoryIds.length; i++){
			sb.append(categoryIds[i]);
			sb.append(",");
			categoryList.add(categoryIds[i]);
		}

		Map<String,String> dimensionConfigMap = new LinkedHashMap<String, String>();
		dimensionConfigMap.put("dimension1",Global.getConfig("dimension1"));
		dimensionConfigMap.put("dimension2",Global.getConfig("dimension2"));
		dimensionConfigMap.put("dimension3",Global.getConfig("dimension3"));
		dimensionConfigMap.put("dimension4",Global.getConfig("dimension4"));
		dimensionConfigMap.put("dimension5",Global.getConfig("dimension5"));

		List<Map<String,Object>> characterDimensionList = studentConclusionService.selectEchartsJsonByCharacterIds(categoryList);
		System.out.println("characterDimensionList==="+characterDimensionList);
		Map<String,String> scoreMap = scoreMap(dimensionConfigMap,characterDimensionList);

		System.out.println("scoreMap==="+scoreMap);
		Map<String,String> sortMap = sortMap(dimensionConfigMap,scoreMap);
		System.out.println("sortMap===="+sortMap);
		System.out.println("echartsTargetAndActualScore->" + echartsTargetAndActualScore(sortMap));

		//获取雷达图的诊断分数（求平均值分数）
		int averageScore = 0;
		for (Map.Entry<String, String> score : sortMap.entrySet()) {
			averageScore = averageScore + Integer.parseInt(score.getValue());
		}
		averageScore = averageScore/5;
		Map<String, String> diagnosisAvgMap = new HashMap<String, String>();
		diagnosisAvgMap.put("diagnosisAvg",String.valueOf(averageScore));

		//正负条形图后台获取数值
		Map<String,String> colDimensionConfigMap = new LinkedHashMap<String, String>();
		//学习能力
		colDimensionConfigMap.put("dimension7",Global.getConfig("dimension7"));
		//自制能力
		colDimensionConfigMap.put("dimension8",Global.getConfig("dimension8"));
		//性格素养
		colDimensionConfigMap.put("dimension9",Global.getConfig("dimension9"));
		//内心世界
		colDimensionConfigMap.put("dimension10",Global.getConfig("dimension10"));
		List<Map<String,Object>> averageScoreList = studentConclusionService.selectColumnEchartsJsonByCharacterIds(categoryList);
		System.out.println("averageScoreList====="+averageScoreList);
		Map<String,String> avgScoreMap = scoreMap(colDimensionConfigMap,averageScoreList);
		System.out.println("avgScoreMap==="+avgScoreMap);

		Map<String,String> avgSortMap = sortMap(colDimensionConfigMap,avgScoreMap);
		System.out.println("avgSortMap===="+avgSortMap);

		Map<String,Map<String,String>> echartJsonResultMap = new LinkedHashMap<String, Map<String,String>>();
		echartJsonResultMap.put("radarEchart",echartsTargetAndActualScore(sortMap));
		echartJsonResultMap.put("columnEchart",avgSortMap);
		//增加雷达图的页面中的诊断平均分数值
		echartJsonResultMap.put("diagnosisAvgScore",diagnosisAvgMap);

		String scoreJson =JSON.toJSONString(echartJsonResultMap);
		System.out.println("scoreJson->"+scoreJson);
		return scoreJson;
	}

	private Map<String,String> echartsTargetAndActualScore(Map<String,String> sortMap){
		Map<String,String> echartTargetAndActualScoreMap = new LinkedHashMap<String, String>();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		for (Map.Entry<String, String> vo : sortMap.entrySet()) {
			parameterMap.put("characteristicName",vo.getKey());
			List<PersonalityCharacteristics> personalityCharacteristicsList= personalityCharacteristicsService.selectCharacteristicScoreByName(parameterMap);
			for(PersonalityCharacteristics personalityCharacteristics:personalityCharacteristicsList){
				if(vo.getKey().equals(personalityCharacteristics.getCharacteristicName())){
					echartTargetAndActualScoreMap.put(vo.getKey(), vo.getValue() + "|" + personalityCharacteristics.getCharacteristicValue());
				}
			}
		}
		return echartTargetAndActualScoreMap;
	}

	/**
	 * 去掉小数点和小数部分，保留整数部分
	 * @param str
	 * @return
	 */
	public static String removeTrim(String str){
		if(str.indexOf(".") > 0){
			//去掉多余的0
			str = str.replaceAll("0+?$", "");
			//如最后一位是.则去掉
			str = str.replaceAll("[.]$", "");
		}
		return str;
	}

	public static boolean isOSLinux() {
		Properties prop = System.getProperties();

		String os = prop.getProperty("os.name");
		if (os != null && os.toLowerCase().indexOf("linux") > -1) {
			return true;
		} else {
			return false;
		}
	}


    /**
     * @description 获取性格特征出现的频次
     * @param source
     * @param key
     * @return
     */
    private static int getTotalFrequency(String source,String key){
        List<String> list = Arrays.asList(key.split(","));
        int num =0;
        for(String s:list){
            num = num + getFrequency(source, s);
        }
        return num;
    }
    /**
     * @description 获取性格特征出现的频率
     * @param source
     * @param key
     * @return
     */
    public static int getFrequency(String source,String key){
        int i, j, count = 0;
        int len1 = source.length();
        int len2 = key.length();
        for(i=0; i<len1-len2; i++){
            for(j=0; j<len2; j++){
                if(key.charAt(j) != source.charAt(j + i)){
                    break;
                }
            }
            if(j>=key.length()){
                count++;
            }
        }
        return count;
    }

    /**
     * @description: 根据各学生类型关键字对应的性格特征出现的频率的最大值来计算
     * @param map
     * @return
     */
    private static String getMaxKeyMap(Map<String, Integer> map) {
        List<Double> list = new ArrayList<Double>();
        for (String temp : map.keySet()) {
            double value = map.get(temp);
            list.add(value);
        }
        double max = 0;
        for (int i = 0; i < list.size(); i++) {
            double size = list.get(i);
            max = (max>size)?max:size;
        }
        for (String key : map.keySet()) {
            if (max == map.get(key)) {
//                return key + "=" + max;
				return key;
            }
        }
        return null;
    }

}