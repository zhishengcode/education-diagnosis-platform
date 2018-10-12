package com.thinkgem.jeesite.common.pdf;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class DocUtil {

//    public static void main(String[] args) throws Exception {
//        String docx = "D:/0001_conclusion_20180418_1427.docx";
//        String pdf = "D:/0001_conclusion_20180418_1427.pdf";
//        transfer(docx,pdf);
//
//    }

    public static void transfer(String docx,String pdf){
        // 直接转换
        InputStream docxStream = null;
        try {
            docxStream = getInFileStream(docx);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] pdfData = new byte[0];
        try {
            pdfData = docxToPdf(docxStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileUtils.writeByteArrayToFile(new File(pdf), pdfData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("finished.");
    }

    protected static InputStream getInFileStream(String inputFilePath) throws FileNotFoundException {
        File inFile = new File(inputFilePath);
        FileInputStream iStream = new FileInputStream(inFile);
        return iStream;
    }

    /**
     * docx转成pdf
     *
     * @param docxStream
     *            docx文件流
     * @return 返回pdf数据
     * @throws Exception
     */
    public static byte[] docxToPdf(InputStream docxStream) throws Exception {
        ByteArrayOutputStream targetStream = null;
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(docxStream);

            PdfOptions options = PdfOptions.create();
            targetStream = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(doc, targetStream, options);

            return targetStream.toByteArray();
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            IOUtils.closeQuietly(targetStream);
        }
    }

}
