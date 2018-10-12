<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.io.*,java.sql.*" %>
<%--<%@ include file="/WEB-INF/views/office/connectionInfo.jsp"%>--%>
<%--<%@ include file="/WEB-INF/views/office/FilePathInfo.jsp"%>--%>
<%
    boolean isNewFile ;
    String filetype="";
    String fileId="";
    String fileName="";
    String fileUrl="";
    String attachFileName="";
    String attachFileDescribe="";
    String attachFileUrl="";
    String templateFileUrl=request.getContextPath()+"/static/office/";//新建文档模板url
    String otherData="";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
//    fileId = request.getParameter("FileId")==null?"":request.getParameter("FileId").toString().trim();
    fileId = "";
    if(fileId=="")
    {isNewFile = true;}
    else
    {isNewFile = false;}
    if(isNewFile)
    {
//        filetype=request.getParameter("fileType")==null?"":request.getParameter("fileType").trim();//如果filetype参数为空,默认为word文档.
        filetype = "word";
        if(filetype.equalsIgnoreCase("excel"))
        {
            fileName="新建Excel文档.xls";
            templateFileUrl=templateFileUrl+"newExcelTemplate.xls";
        }
        else if(filetype.equalsIgnoreCase("word")){
            fileName="新建word文档.doc";
            templateFileUrl=templateFileUrl+"newWordTemplate.doc";
        }
        else if(filetype.equalsIgnoreCase("ppt"))
        {
            fileName="新建ppt文档.ttp";
            templateFileUrl=templateFileUrl+"newPptTemplate.ppt";
        }
        else{
            filetype="word";
            fileName="新建word文档.doc";
            templateFileUrl=templateFileUrl+"newWordTemplate.doc";
        }
        fileUrl=templateFileUrl;//如果是新文档，控件打开新建模板文档
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>ntko office文档控件示例-ms office文档编辑</title>
    <meta content="IE=7" http-equiv="X-UA-Compatible" />
    <!--设置缓存-->
    <meta http-equiv="cache-control" content="no-cache,must-revalidate">
    <meta http-equiv="pragram" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="<%=request.getContextPath() %>/static/office/StyleSheet.css" rel="stylesheet" type="text/css" />
    <script src="<%=request.getContextPath() %>/static/office/OfficeContorlFunctions.js"></script>
</head>
<body  onload='intializePage("<%=fileUrl%>")' onbeforeunload ="onPageClose()">
<form id="form1" modelAttribute="studentConclusionPrintModel" action="upLoadOfficeFile.jsp" enctype="multipart/form-data" style="padding:0px;margin:0px;">
    <div id="editmain" class="editmain">
        <div id="editmain_middle" class="editmain_middle">
            <div id="editmain_right" class="editmain_right">
                <div id="officecontrol">
                    <script type="text/javascript" src="<%=request.getContextPath() %>/officentko/ntkoofficecontrol.js"></script>
                    <div id=statusBar style="height:20px;width:100%;background-color:#c0c0c0;font-size:12px;"></div>
                    <script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
                        setFileOpenedOrClosed(false);
                    </script>
                    <script language="JScript" for="TANGER_OCX" event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
                        OFFICE_CONTROL_OBJ.SetBookmarkValue("时间", document.all("conclusionDatePrint").value);
                        OFFICE_CONTROL_OBJ.SetBookmarkValue("结论", document.all("conclusionPrint").value);
                        OFFICE_CONTROL_OBJ.activeDocument.saved=true;//saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
                        //获取文档控件中打开的文档的文档类型
                        switch (OFFICE_CONTROL_OBJ.doctype)
                        {
                            case 1:
                                fileType = "Word.Document";
                                fileTypeSimple = "wrod";
                                break;
                            case 2:
                                fileType = "Excel.Sheet";
                                fileTypeSimple="excel";
                                break;
                            case 3:
                                fileType = "PowerPoint.Show";
                                fileTypeSimple = "ppt";
                                break;
                            case 4:
                                fileType = "Visio.Drawing";
                                break;
                            case 5:
                                fileType = "MSProject.Project";
                                break;
                            case 6:
                                fileType = "WPS Doc";
                                fileTypeSimple="wps";
                                break;
                            case 7:
                                fileType = "Kingsoft Sheet";
                                fileTypeSimple="et";
                                break;
                            default :
                                fileType = "unkownfiletype";
                                fileTypeSimple="unkownfiletype";
                        }
                        setFileOpenedOrClosed(true);
                    </script>
                    <script language="JScript" for=TANGER_OCX event="BeforeOriginalMenuCommand(TANGER_OCX_str,TANGER_OCX_obj)">
                        alert("BeforeOriginalMenuCommand事件被触发");
                    </script>
                    <script language="JScript" for=TANGER_OCX event="OnFileCommand(TANGER_OCX_str,TANGER_OCX_obj)">
                        if (TANGER_OCX_str == 3)
                        {
                            alert("不能保存！");
                            CancelLastCommand = true;
                        }
                    </script>
                    <script language="JScript" for=TANGER_OCX event="AfterPublishAsPDFToURL(result,code)">
                        result=trim(result);
                        alert(result);
                        document.all("statusBar").innerHTML="服务器返回信息:"+result;
                        if(result=="文档保存成功。")
                        {window.close();}
                    </script>
                    <%--<script language="JScript" for=TANGER_OCX event="OnCustomMenuCmd2(menuPos,submenuPos,subsubmenuPos,menuCaption,menuID)">--%>
                        <%--alert("第" + menuPos +","+ submenuPos +","+ subsubmenuPos +"个菜单项,menuID="+menuID+",菜单标题为\""+menuCaption+"\"的命令被执行.");--%>
                    <%--</script>--%>
                </div>
            </div>
        </div>
    </div>
    <div style="display: none">
        <input type="text" name="conclusionPrint" id="conclusionPrint" value="${studentConclusion.conclustion}">
        <input type="text" name="conclusionDatePrint" id="conclusionDatePrint" value="${conclusionDatePrint}">
    </div>
</form>
</body>
</html>
