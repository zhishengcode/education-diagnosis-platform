<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>笔划类别管理</title>
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
		<li class="active"><a href="${ctx}/edu/onecategory/">笔划类别列表</a></li>
		<shiro:hasPermission name="edu:onecategory:edit"><li><a href="${ctx}/edu/onecategory/form">笔划类别添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="onecategory" action="${ctx}/edu/onecategory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>笔划类别：</label>
				<form:input path="firstcategoryname" htmlEscape="false" maxlength="15" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>笔划类别名称</th>
				<th>排序列</th>
				<shiro:hasPermission name="edu:onecategory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="onecategory">
			<tr>
				<td>${onecategory.firstcategoryname}</td>
				<td>${onecategory.sort}</td>
				<shiro:hasPermission name="edu:onecategory:edit"><td>
    				<a href="${ctx}/edu/onecategory/form?id=${onecategory.id}">修改</a>
					<a href="${ctx}/edu/onecategory/delete?id=${onecategory.id}" onclick="return confirmx('确认要删除该笔划类别吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>