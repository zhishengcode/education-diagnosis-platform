<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>类别特征管理</title>
	<meta name="decorator" content="default"/>
	<style>
		li{
			display: inline;
			line-height:35px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			var personalityCharacteristicsId=$("#personalityCharacteristicsId").val();
			if(personalityCharacteristicsId !=null && personalityCharacteristicsId !=""){
			    var personalityCharacteristicsIds=personalityCharacteristicsId.split(",");
			    for(var i=0;i<personalityCharacteristicsIds.length;i++){
			        $("#"+personalityCharacteristicsIds[i]).attr("checked",true);
				}
			}
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				rules:{
                    characterName:{
                        remote :{
                            url:"${ctx}/edu/characterdict/characterName",
                            type:'post',
                            data:{
                                characterName:function () {
                                    return $("#characterName").val();
                                },
								id:$("#id").val()
                            }
						}
					},
                    characterValue:{
                        range:[-50,100]
					}
				},
				messages:{
                    characterName:{remote:"当前输入的性格特征已存在，请重新输入！"}
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
            $("#btnSubmit").click(function(){
                var personalityCharacteristicsId="";
                $('input:checkbox[name=personality]:checked').each(function(i){
                    personalityCharacteristicsId+=$(this).attr("id")+",";
				});
                $("#personalityCharacteristicsId").val(personalityCharacteristicsId.substring(0,personalityCharacteristicsId.length-1));
                $("#inputForm").submit();
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/edu/characterdict/">类别特征列表</a></li>
		<li class="active"><a href="${ctx}/edu/characterdict/form?id=${characterdict.id}">类别特征<shiro:hasPermission name="edu:characterdict:edit">${not empty characterdict.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:characterdict:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="characterdict" action="${ctx}/edu/characterdict/save" method="post" class="form-horizontal">
		<form:hidden path="id" id="id"/>
		<form:hidden path="personalityCharacteristicsId" id="personalityCharacteristicsId" name="personalityCharacteristicsId"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">性格特征名称：</label>
			<div class="controls">
				<form:input path="characterName" name="characterName" id="characterName" htmlEscape="false" maxlength="128" class="input-xlarge required" placeholder="请输入性格特征名称"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<form:errors path="characterName" cssStyle="color:red"></form:errors>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性格特征维度：</label>
			<div class="controls">
				<ul>
				<c:forEach items="${personalityList}" var="p" varStatus="status">
                     <li>
						 <input type="checkbox" name="personality" id="${p.id}"  value="${p.id}"/> ${p.characteristicName}
					 </li>
				</c:forEach>
				</ul>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特征维度分值：</label>
			<div class="controls">
				<form:input path="characterValue" name="characterValue" id="characterValue" htmlEscape="false" maxlength="4" class="input-xlarge" placeholder="请输入特征维度分值"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">性格特征值：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="characterValue" htmlEscape="false" maxlength="11" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">排序：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="sort" htmlEscape="false" maxlength="11" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="edu:characterdict:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>