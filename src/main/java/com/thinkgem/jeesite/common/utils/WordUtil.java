package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.edu.entity.StudentConclusion;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/10/26.
 */
public class WordUtil {

//    public static void main(String[] args) throws Exception{
//
//        WordUtil.createWord("conclusionNo","张老师","2017-10-26","王领航","热情活泼","D\\:\\\\result\\\\");
//    }

    public static void createWord(StudentConclusion studentConclusion, String resultPath) throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
        String createTime =  sdf.format(studentConclusion.getCreatetime());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("${conclusionNo}", "2017011111");
        map.put("${teacher}", "aaaa");
        map.put("${createDate}","2017011111");
        map.put("${student}","cccc");
        map.put("${content}", "ddddd");
        String templatePath = Global.getConfig("downLoadConclusionTemplatePath");
        InputStream is = new FileInputStream(templatePath);
//        HWPFDocument doc = new HWPFDocument(is);
        XWPFDocument doc = new XWPFDocument(is);
//        Range range = doc.getRange();
//        range.replaceText("${conclusionNo}",studentConclusion.getStudent());
//        range.replaceText("${teacher}",studentConclusion.getStudent());
//        range.replaceText("${createDate}",createTime);
//        range.replaceText("${student}",studentConclusion.getStudent());
//        range.replaceText("${content}",studentConclusion.getConclustion());

//        //替换段落里面的变量
//        WordUtil.replaceInPara(doc, map);
////        //替换表格里面的变量
        WordUtil.replaceInTable(doc, map);
        OutputStream os = new FileOutputStream(resultPath+studentConclusion.getStudent()+".doc");
        doc.write(os);
        WordUtil.close(os);
        WordUtil.close(is);
    }

    private static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            WordUtil.replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     * @param para 要替换的段落
     * @param params 参数
     */
    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (WordUtil.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i=0; i<runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = WordUtil.matcher(runText);
                if (matcher.find()) {
                    while ((matcher = WordUtil.matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }

    /**
     * 关闭输入流
     * @param is
     */
    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * @param os
     */
    private static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 替换表格里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        WordUtil.replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }
}
