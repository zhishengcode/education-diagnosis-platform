<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>性格特征与笔划形态关联管理</title>
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
		<li class="active"><a href="${ctx}/edu/cateCha/">性格特征与笔划形态关联列表</a></li>
		<shiro:hasPermission name="edu:cateCha:edit"><li><a href="${ctx}/edu/cateCha/form">性格特征与笔划形态关联添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cateCha" action="${ctx}/edu/cateCha/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>笔划形态id：</label>
				<form:input path="categoryId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<shiro:hasPermission name="edu:cateCha:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cateCha">
			<tr>
				<shiro:hasPermission name="edu:cateCha:edit"><td>
    				<a href="${ctx}/edu/cateCha/form?id=${cateCha.id}">修改</a>
					<a href="${ctx}/edu/cateCha/delete?id=${cateCha.id}" onclick="return confirmx('确认要删除该性格特征与笔划形态关联吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>