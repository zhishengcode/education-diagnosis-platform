<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
                rules:{
                    sort:{
                        required: true,
                        digits: true
                    },
                },
                messages:{
                    sort:"排序值必须输入正整数，请重新输入！"
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
            // $("#btnSubmit").click(function(){
            //     var r = /^\+?[1-9][0-9]*$/;　　//正整数
            //     var flag=r.test($("#sort").val());
            //     //如果判断为正整数，则flag为true
				// if(!flag){
				// 	// alert("排序值必须输入正整数，请重新输入！");
            //         // $("#messageBox").text("排序值必须输入正整数，请重新输入！");
				// 	return false;
				// }
				// alert("提交");
            //     //提交表单
				// return false;
            //     $("#inputForm").submit();
            // });
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/edu/onecategory/">分类列表</a></li>
		<li class="active"><a href="${ctx}/edu/onecategory/form?id=${onecategory.id}">分类<shiro:hasPermission name="edu:onecategory:edit">${not empty onecategory.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:onecategory:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="onecategory" action="${ctx}/edu/onecategory/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">笔划类别：</label>
			<div class="controls">
				<form:input path="firstcategoryname" htmlEscape="false" maxlength="20" class="input-xlarge required" placeholder="请输入笔划类别"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序值：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="20" class="input-xlarge required" placeholder="请输入排序值"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:onecategory:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>