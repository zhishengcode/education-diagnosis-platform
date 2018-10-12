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
		<li class="active"><a href="${ctx}/edu/studentConclusion/">测评列表</a></li>
		<shiro:hasPermission name="edu:studentConclusion:edit"><li><a href="${ctx}/edu/studentConclusion/form">测评添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentConclusion" action="${ctx}/edu/studentConclusion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>测评编号：</label>
				<form:input path="student" htmlEscape="false" maxlength="50" class="input-medium"/>
				<%--<sys:treeselect id="student" name="student.id" value="${studentConclusion.student.id}" labelName="student.name" labelValue="${studentConclusion.student.name}"
								title="学员" url="/student/student/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>--%>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>测评编号</th>
				<th>测评类型</th>
				<th>教师</th>
				<th>结论</th>
				<th>创建时间</th>
				<shiro:hasPermission name="edu:studentConclusion:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentConclusion">
			<tr>
				<td><a href="${ctx}/edu/studentConclusion/form?id=${studentConclusion.id}">
					${studentConclusion.student}
				</a></td>
				<td>
					${fns:getDictLabel(studentConclusion.evaluationType, 'evaluation_type', '')}
				</td>
				<td>
					${studentConclusion.teacher.name}
				</td>

				<td>
						${fns:abbr(studentConclusion.conclustion,70)}
				</td>
				<td>
					<fmt:formatDate value="${studentConclusion.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="edu:studentConclusion:edit"><td>
    				<a href="${ctx}/edu/studentConclusion/form?id=${studentConclusion.id}">修改</a>
					<a href="${ctx}/edu/studentConclusion/delete?id=${studentConclusion.id}" onclick="return confirmx('确认要删除该结论吗？', this.href)">删除</a>
					<a href="${ctx}/edu/studentConclusion/downLoad?id=${studentConclusion.id}">下载结论</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>