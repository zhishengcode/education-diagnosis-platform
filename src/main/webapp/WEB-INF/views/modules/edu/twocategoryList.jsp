<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>笔划形态管理</title>
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
		<li class="active"><a href="${ctx}/edu/twocategory/">笔划形态列表</a></li>
		<shiro:hasPermission name="edu:twocategory:edit"><li><a href="${ctx}/edu/twocategory/form">笔划形态添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="twocategory" action="${ctx}/edu/twocategory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>笔划类别：</label>
				<form:input path="onecategoryid.firstcategoryname" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>笔划类别</th>
				<th>笔划形态名称</th>
				<%--<th>笔划形态数值</th>
				<th>性格特征ID</th>--%>
				<shiro:hasPermission name="edu:twocategory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="twocategory">
			<tr>
				<td>
						${twocategory.onecategoryid.firstcategoryname}
				</td>
				<td><a href="${ctx}/edu/twocategory/form?id=${twocategory.id}">
					${twocategory.twocategoryname}
				</a></td>
				<%--<td>
					${twocategory.twocategoryvalue}
				</td>--%>
				<%--<td>
					${twocategory.characterid}
				</td>--%>
				<shiro:hasPermission name="edu:twocategory:edit"><td>
    				<a href="${ctx}/edu/twocategory/form?id=${twocategory.id}">修改</a>
					<a href="${ctx}/edu/twocategory/delete?id=${twocategory.id}" onclick="return confirmx('确认要删除该笔划形态吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>