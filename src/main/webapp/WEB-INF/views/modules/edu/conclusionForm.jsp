<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结论管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/edu/conclusion/">结论列表</a></li>
		<li class="active"><a href="${ctx}/edu/conclusion/form?id=${conclusion.id}">结论<shiro:hasPermission name="edu:conclusion:edit">${not empty conclusion.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:conclusion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="conclusion" action="${ctx}/edu/conclusion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">测评老师：</label>
			<div class="controls">
				${conclusion.teacher.name}
				<%--<form:input path="teacherId" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学生：</label>
			<div class="controls">
				${conclusion.student.name}
				<%--<form:input path="studentId" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生成时间：</label>
			<div class="controls">
				<fmt:formatDate value="${conclusion.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				<%--<input name="createtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${conclusion.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结论：</label>
			<div class="controls">
				${conclusion.conclusion}
				<%--<form:input path="conclusion" htmlEscape="false" maxlength="500" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="form-actions">
			<%--<shiro:hasPermission name="edu:conclusion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>