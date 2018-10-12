<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>性格特征管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/edu/personalityCharacteristics/">特征维度列表</a></li>
		<shiro:hasPermission name="edu:characterdict:edit"><li><a href="${ctx}/edu/personalityCharacteristics/form">特征维度添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="personalityCharacteristics" action="${ctx}/edu/personalityCharacteristics/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>特征维度：</label>
				<form:input path="characteristicName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>特征维度名称</th>
				<th>特征维度值</th>
				<th>排序</th>
				<shiro:hasPermission name="edu:personalityCharacteristics:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personalityCharacteristics">
			<tr>
				<td><a href="${ctx}/edu/personalityCharacteristics/form?id=${personalityCharacteristics.id}">
					${personalityCharacteristics.characteristicName}
				</a></td>
				<td>
					${personalityCharacteristics.characteristicValue}
				</td>
				<td>
					${personalityCharacteristics.sort}
				</td>
				<shiro:hasPermission name="edu:personalityCharacteristics:edit"><td>
    				<a href="${ctx}/edu/personalityCharacteristics/form?id=${personalityCharacteristics.id}">修改</a>
					<a href="${ctx}/edu/personalityCharacteristics/delete?id=${personalityCharacteristics.id}" onclick="return confirmx('确认要删除该类别特征吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>