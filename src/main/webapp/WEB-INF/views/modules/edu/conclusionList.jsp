<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结论管理</title>
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
		<li class="active"><a href="${ctx}/edu/conclusion/">结论列表</a></li>
		<%--<shiro:hasPermission name="edu:conclusion:edit"><li><a href="${ctx}/edu/conclusion/form">结论添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="conclusion" action="${ctx}/edu/conclusion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>测评编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>测评老师：</label>
				<sys:treeselect id="teacher" name="teacher.id" value="${conclusion.teacher.id}" labelName="teacher.name" labelValue="${conclusion.teacher.name}"
								title="测评老师" url="/edu/teacher/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>学生：</label>
				<sys:treeselect id="student" name="student.id" value="${conclusion.student.id}" labelName="student.name" labelValue="${conclusion.student.name}"
								title="学生" url="/student/student/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>测评编号</th>
				<th>测评老师</th>
				<th>学生</th>
				<th>生成时间</th>
				<shiro:hasPermission name="edu:conclusion:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="conclusion">
			<tr>
				<td><a href="${ctx}/edu/conclusion/form?id=${conclusion.id}">
					${conclusion.id}
				</a></td>
				<td>
					${conclusion.teacher.name}
				</td>
				<td>
					${conclusion.student.name}
				</td>
				<td>
					<fmt:formatDate value="${conclusion.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="edu:conclusion:edit"><td>
    				<a href="${ctx}/edu/conclusion/form?id=${conclusion.id}">查看</a>
					<a href="${ctx}/edu/conclusion/delete?id=${conclusion.id}" onclick="return confirmx('确认要删除该结论吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>