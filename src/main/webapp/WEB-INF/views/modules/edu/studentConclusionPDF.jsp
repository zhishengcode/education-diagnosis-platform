<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>测评结论</title>
</head>
<body>

<iframe src="<c:url value="/pdf/web/viewer.html" />?file=${ctx}/edu/studentConclusion/pdfStreamHandeler/${filePdfName}"
    width="100%" height="800"></iframe>

</body>
</html>