<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学员信息管理</title>
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
		<li class="active"><a href="${ctx}/student/student/">学员信息列表</a></li>
		<shiro:hasPermission name="student:student:edit"><li><a href="${ctx}/student/student/form">学员信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="student" action="${ctx}/student/student/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学员姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学员姓名</th>
				<th>学员年龄</th>
				<th>性别</th>
				<th>手机号</th>
				<th>授课教师</th>
				<th>地址</th>
				<shiro:hasPermission name="student:student:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="student">
			<tr>
				<td><a href="${ctx}/student/student/form?id=${student.id}">
					${student.name}
				</a></td>
				<td>
					${student.age}
				</td>
				<td>
					<c:if test="${student.gender eq 0}">男</c:if>
					<c:if test="${student.gender eq 1}">女</c:if>
				</td>
				<td>
					${student.mobile}
				</td>
				<td>
					${student.teacher.name}
				</td>
				<td>
					${student.address}
				</td>
				<shiro:hasPermission name="student:student:edit"><td>
    				<a href="${ctx}/student/student/form?id=${student.id}">修改</a>
					<a href="${ctx}/student/student/delete?id=${student.id}" onclick="return confirmx('确认要删除该学员信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>