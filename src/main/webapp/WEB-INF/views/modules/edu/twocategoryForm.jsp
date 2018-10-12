<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>笔划形态管理</title>
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
			var eduqualifierlist='${eduqualifierlist}';
			if(typeof(eduqualifierlist)!="	undefined"){
                var lists=eduqualifierlist.split(",");
                for(var i=0;i<lists.length;i++){
                    var ids=lists[i].split(";");
                    $('input[id="'+ids[0]+ids[1]+'"]').prop("checked", "checked");
                }
			}


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
			$("#btnSubmit").click(function(){
			    var characterId="";
			    var eduqualifierId="";
			    var eduqualifierlist="";
                $('input:radio:checked').each(function(){
                    characterId=$(this).attr("name");//性格特征Id
                    eduqualifierId=$(this).val();　　//修饰词Id
                    eduqualifierlist+=characterId+";"+eduqualifierId+",";
                });
                $("#eduqualifierlist").val(eduqualifierlist.substring(0,eduqualifierlist.length-1));
                //提交表单
                $("#inputForm").submit();
            });
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/edu/twocategory/">笔划形态列表</a></li>
		<li class="active"><a href="${ctx}/edu/twocategory/form?id=${twocategory.id}">笔划形态<shiro:hasPermission name="edu:twocategory:edit">${not empty twocategory.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:twocategory:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="twocategory" action="${ctx}/edu/twocategory/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="eduqualifierlist" name="eduqualifierlist" />
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">笔划类别:</label>
			<div class="controls">
				<form:select path="onecategoryid" class="input-medium">
					<form:options items="${onelist}" itemLabel="firstcategoryname" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">笔划形态名称：</label>
			<div class="controls">
				<form:input path="twocategoryname" htmlEscape="false" maxlength="50" class="input-xlarge required" placeholder="请输入笔划形态名称"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">笔划形态数值：</label>
			<div class="controls">
				<form:input path="twocategoryvalue" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>--%>
		<%--ShawnXiang注释--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">性格特征:</label>--%>
			<%--<div class="controls">--%>
				<%--<form:checkboxes path="charalist" items="${allchara}" element="br"  itemLabel="characterName"  itemValue="id" htmlEscape="false" />--%>
			<%--</div>--%>
		<%--</div>--%>

		<%--ShawnXiang添加修改--%>
		<div class="control-group">
			<label class="control-label">性格特征:</label>
			<div class="controls">
				<ul>
					<c:forEach items="${allchara}" var="st" varStatus="status">
						<li><input type="checkbox" name="charalist" <c:if test="${fn:contains(twocategory.charalist,st.id)}">checked</c:if> id=characterId value="${st.id}" />${st.characterName}</li>
						<%--<li>--%>
						  <%--<c:forEach items="${eduQualifierList}" var="fier" varStatus="status">--%>
							  <%--<input type="radio" name="${st.id}" id="${st.id}${fier.id}"  value="${fier.id}"/> ${fier.qualifierName}--%>
						  <%--</c:forEach>--%>
						<%--</li><br/>--%>
						<%--<c:if test="${status.count%3==0}">
							<input type="checkbox" name="charalist" <c:if test="${fn:contains(twocategory.charalist,st.id)}">checked</c:if> id=characterId value="${st.id}" />${st.characterName}<br/>
						</c:if>
						<c:if test="${status.count%3!=0}">
							<input type="checkbox" name="charalist" <c:if test="${fn:contains(twocategory.charalist,st.id)}">checked</c:if> id=characterId value="${st.id}" />${st.characterName}
						</c:if>--%>
					</c:forEach>
				</ul>
			</div>
		</div>

		<%--	<div class="control-group">
                <label class="control-label">性格特征ID：</label>
                <div class="controls">
                    <form:input path="characterid" htmlEscape="false" maxlength="11" class="input-xlarge "/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">状态位：</label>
                <div class="controls">
                    <form:input path="status" htmlEscape="false" maxlength="11" class="input-xlarge "/>
                </div>
            </div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="edu:twocategory:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>