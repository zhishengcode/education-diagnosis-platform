<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结论管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/echarts/echarts.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/echarts-gl/echarts-gl.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/echarts-stat/ecStat.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/echarts/extension/dataTool.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/echarts/map/js/china.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/echarts/map/js/world.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/echarts/extension/bmap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/simplex.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
            if($("#echartsScore").val()==""){
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }

                for(var i in optionCol.series[0].data){
                    colorList[i]=optionCol.series[0].data[i]>0 ? '#483D8B' : '#DC143C';
                }

                if (optionCol && typeof optionCol === "object") {
                    myChartCol.setOption(optionCol);
                }
            }else{
                var echartJson = (new Function("return " + $("#echartsScore").val()))();
                //遍历json
                var scoreArray = [];
                var scoreTargetArray = [];
                var avgArray = [];
                var key = "";
                var value = "";
                var scoreValue = "";
                var scoreTargetValue = "";
                var avgValue = "";
                debugger;
                for(var j in echartJson) {
                    key = j;
                    value = echartJson[j];
                    if(key == "radarEchart"){
                        for(var k in value) {
                            // scoreValue = value[k];
                            // scoreArray.push(scoreValue);
                            scoreValue = value[k].substring(0,value[k].indexOf("|"));
                            scoreArray.push(scoreValue);

                            scoreTargetValue = value[k].substring(value[k].indexOf("|")+1,value[k].length);
                            scoreTargetArray.push(scoreTargetValue);
                        }
                    }
                    if(key == "columnEchart"){
                        for(var k in value) {
                            avgValue = value[k];
                            avgArray.push(avgValue);
                        }
                    }
                }
                debugger;
                option.series[0].data[1].value = scoreArray;
                option.series[0].data[0].value = scoreTargetArray;
                myChart.setOption(option);

                optionCol.series[0].data = avgArray;
                for(var i in optionCol.series[0].data){
                    colorList[i]=optionCol.series[0].data[i]>0 ? '#483D8B' : '#DC143C';
                }
                myChartCol.setOption(optionCol);
            }



			$("#inputForm").validate({
				submitHandler: function(form){
//					loading('正在提交，请稍等...');

                    var picBase64Info = myChart.getDataURL();
                    // alert(picBase64Info.length());
                    $("#echartsPic").val(picBase64Info);
                    var picColBase64Info = myChartCol.getDataURL();
                    // alert(picColBase64Info.length());
                    $("#columnEchartsPic").val(picColBase64Info)

					form.submit();
				},
                rules:{
                    student:{
                        remote :{
                            url:"${ctx}/edu/studentConclusion/validateStudentNo",
                            type:'post',
                            data:{
                                studentNo:function () {
                                    return $("#student").val();
                                },
                                id:$("#id").val()
                            }
                        }
                    },
                    age:{
                        required: true,
                        digits: true,
                        range:[0,150]
                    },
                },
                messages:{
                    student:{remote:"当前输入的测评编号已存在，请重新输入！"},
                    age:"年龄必须输入小于等于3位的正整数，请重新输入！"
                },
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
            $('input[type=checkbox]').click(function(){
                loadEchartsData();
            });

            $("#btnSubmit").click(function(){
                loadEchartsData();
			});

		});

		function loadEchartsData(){
            var twocategoryId="";
            var twocategoryIds="";
            $('input[type=checkbox]:checked').each(function(){
                twocategoryId =$(this).val();
                twocategoryIds+=twocategoryId+",";
            });
            var categoryStr = twocategoryIds.substring(0,twocategoryIds.length-1);
            $("#twocategoryIdlist").val(categoryStr);
            var myChannel = {};//构建json数据，用于发送给后台
            var arr = new Array();
            $.ajax({//发送请求
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: "${ctx}/edu/studentConclusion/getEchartsJSON",
                data: JSON.stringify({
                    categoryJson:categoryStr,
                }),
                dataType: "json",
                error: function (echartData) {
                    alert("错误提示:" + echartData);
                },
                success: function (echartData) {
                    var echartJson = eval(echartData);
                    // alert("echartJson==="+echartJson)
                    //遍历json
					debugger;
                    var scoreArray = [];
                    var scoreTargetArray = [];
                    var avgArray = [];
                    var key = "";
                    var value = "";
                    var scoreValue = "";
                    var scoreTargetValue = "";
                    var avgValue = "";
                    // debugger;
                    for(var j in echartJson) {
                        key = j;
                        value = echartJson[j];
                        if(key == "radarEchart"){
                            for(var k in value) {
                                scoreValue = value[k].substring(0,value[k].indexOf("|"));
                                scoreArray.push(scoreValue);

                                scoreTargetValue = value[k].substring(value[k].indexOf("|")+1,value[k].length);
                                scoreTargetArray.push(scoreTargetValue);
                            }
                        }
                        if(key == "columnEchart"){
                            for(var k in value) {
                                avgValue = value[k];
                                avgArray.push(avgValue);
                            }
                        }
                    }
                    // alert("scoreArray==="+scoreArray);
                    // alert("scoreTargetArray==="+scoreTargetArray);
                    option.series[0].data[1].value = scoreArray;
                    option.series[0].data[0].value = scoreTargetArray;
                    myChart.setOption(option);

                    // alert("avgArray==="+avgArray)
                    optionCol.series[0].data = avgArray;
                    for(var i in optionCol.series[0].data){
                        colorList[i]=optionCol.series[0].data[i]>0 ? '#483D8B' : '#DC143C';
                    }
                    myChartCol.setOption(optionCol);
                }
            });
		}


	</script>
</head>
<body>
<div id="container" style="height: 300px;width: 500px;display: none"></div>
<div id="containerCol" style="height: 300px;width: 500px;display: none"></div>
<script type="text/javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        // title: {
        //     text: '学生测评'
        // },
        tooltip: {},
        radar: {
            // shape: 'circle',
            name: {
                textStyle: {
                    color: '#fff',
                    backgroundColor: '#999',
                    borderRadius: 3,
                    padding: [3, 5]

                }
            },
            indicator: [
                { name: '情绪管理', max: 100},
                { name: '思维管理', max: 100},
                { name: '心态管理', max: 100},
                { name: '乐群性', max: 100},
                { name: '组织能力', max: 100}
            ]
        },
        series: [{
            name: '目标 vs 实际',
            type: 'radar',
            // areaStyle: {normal: {}},
            data : [
                {
                    value : [50, 50, 50, 50, 50],
                    name : '目标测评值'
                },
                {
                    value : [50, 50, 50, 50, 50],
                    name : '实际测评值'
                }
            ]
        }]
    };

    //正负条形图
    var domCol = document.getElementById("containerCol");
    var myChartCol = echarts.init(domCol);
    optionCol = null;
    var colorList = [];
    optionCol = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'value',
                min : -60,
                max : 60,
                axisLabel:{
                    //X轴刻度配置
                    interval:0
                }
            }
        ],
        yAxis : [
            {
                type : 'category',
                axisTick : {show: false},
                data : ['学习能力','自制能力','性格素养','内心边界']
            }
        ],
        series : [
            {
                name:'分数',
                type:'bar',
                label: {
                    normal: {
                        show: true,
                        position: 'inside'
                    }
                },
                itemStyle:{
                    normal:{
                        color:function(params){
                            return colorList[params.dataIndex]
                        }
                    }
                },
                barWidth : 50,
                data:[-10, -20, 15, 30]
            }
        ]
    };

</script>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/edu/studentConclusion/">测评列表</a></li>
		<li class="active"><a href="${ctx}/edu/studentConclusion/form?id=${studentConclusion.id}">测评<shiro:hasPermission name="edu:studentConclusion:edit">${not empty studentConclusion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:studentConclusion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<%--<form:hidden path="echartspng" id="echartspng"/>--%>
	<form:form id="inputForm" modelAttribute="studentConclusion" action="${ctx}/edu/studentConclusion/save" method="post" class="form-horizontal">
		<form:hidden path="id" id="id"/>
		<form:hidden path="echartsPic" id="echartsPic"/>
		<form:hidden path="columnEchartsPic" id="columnEchartsPic"/>
		<form:hidden path="echartsScore" id="echartsScore"/>
		<input type="hidden" id="twocategoryIdlist" name="twocategoryIdlist" />
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">测评编号：</label>
			<div class="controls">
				<form:input path="student" htmlEscape="false" maxlength="50" class="required" placeholder="请输入测评编号"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<form:errors path="student" cssStyle="color:red"></form:errors>
				<%--<sys:treeselect id="student" name="student.id" value="${studentConclusion.student.id}" labelName="student.name" labelValue="${studentConclusion.student.name}"--%>
								<%--title="学员" url="/student/student/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="conclusionName" htmlEscape="false" maxlength="200" placeholder="请输入名称"/>
				<form:errors path="conclusionName" cssStyle="color:red"></form:errors>
					<%--<sys:treeselect id="student" name="student.id" value="${studentConclusion.student.id}" labelName="student.name" labelValue="${studentConclusion.student.name}"--%>
					<%--title="学员" url="/student/student/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">测评类型：</label>
			<div class="controls">
				<form:select path="evaluationType" class="input-medium">
					<form:options items="${fns:getDictList('evaluation_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教师：</label>
			<div class="controls">
				<sys:treeselect id="teacher" name="teacher.id" value="${studentConclusion.teacher.id}" labelName="teacher.name" labelValue="${studentConclusion.teacher.name}"
								title="教师" url="/sys/user/nowData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学校：</label>
			<div class="controls">
				<form:input path="school" htmlEscape="false" maxlength="50" class="required" placeholder="请输入学校"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<form:errors path="school" cssStyle="color:red"></form:errors>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">班级：</label>
			<div class="controls">
				<form:select path="grade" class="input-medium">
					<form:options items="${fns:getDictList('gradeType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<%--<div class="control-group">--%>
			<%--<label class="control-label">班级：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="grade" htmlEscape="false" maxlength="50" class="required" placeholder="请输入班级"/>--%>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
				<%--<form:errors path="grade" cssStyle="color:red"></form:errors>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">年龄：</label>
			<div class="controls">
				<form:input path="age" htmlEscape="false" maxlength="50" class="required" placeholder="请输入年龄"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<form:errors path="age" cssStyle="color:red"></form:errors>
			</div>
		</div>

	<div class="control-group">
		<label class="control-label">笔记分析：</label>
		<div class="controls">
		<table class="table table-striped table-bordered table-condensed">

		<c:forEach items="${onelist}" var="one">
			<tr>
				<td width="12%"><div style="text-align: right">${one.firstcategoryname}:</div></td><td width="92%"><form:checkboxes path="values"  items="${fns:getTwocharaByStudentConclusion(one.id)}" itemLabel="twocategoryname"  itemValue="id" htmlEscape="false" class="required"/></td>
			</tr>
		</c:forEach>
		</table></div></div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:studentConclusion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="生成结论"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>



</body>
</html>