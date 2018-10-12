<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>性格特征管理</title>
	<meta name="decorator" content="default"/>
	<style>
		li{
			display: inline;
			line-height:35px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				rules:{
                    characteristicName:{
                        remote :{
                            url:"${ctx}/edu/personalityCharacteristics/characteristicName",
                            type:'post',
                            data:{
                                characteristicName:function () {
                                    return $("#characteristicName").val();
                                },
								id:$("#id").val()
                            }
						}
					},
                    sort:{
                        required: true,
                        digits: true
                    },
				},
				messages:{
                    characteristicName:{remote:"当前输入的性格特征已存在，请重新输入！"},
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

		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/edu/personalityCharacteristics/">特征维度列表</a></li>
		<li class="active"><a href="${ctx}/edu/personalityCharacteristics/form?id=${personalityCharacteristics.id}">特征维度<shiro:hasPermission name="edu:characterdict:edit">${not empty characterdict.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:personalityCharacteristics:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="personalityCharacteristics" action="${ctx}/edu/personalityCharacteristics/save" method="post" class="form-horizontal">
		<form:hidden path="id" id="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">特征维度名称：</label>
			<div class="controls">
				<form:input path="characteristicName" name="characteristicName" id="characteristicName" htmlEscape="false" maxlength="128" class="input-xlarge required" placeholder="请输入性格特征名称"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<form:errors path="characteristicName" cssStyle="color:red"></form:errors>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特征维度值：</label>
			<div class="controls">
				<form:input path="characteristicValue" name="characteristicValue" id="characteristicValue" htmlEscape="false" class="input-xlarge" placeholder="请输入特征维度分值"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序列：</label>
			<div class="controls">
				<form:input path="sort" name="sort" id="sort" htmlEscape="false" class="input-xlarge" placeholder="请输入排序值"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:characterdict:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>