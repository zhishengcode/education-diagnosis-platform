package com.thinkgem.jeesite.common.pdf;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

public class OfficeToPDF {
    private static Logger logger = LoggerFactory.getLogger(OfficeToPDF.class);
    public static int officeToPDF(String sourceFile, String destFile) throws FileNotFoundException {
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                logger.debug("找不到源文件");
                return -1;// 找不到源文件, 则返回-1
            }

            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                logger.debug("如果目标路径不存在, 则新建该路径");
                outputFile.getParentFile().mkdirs();
            }

            // connect to an OpenOffice.org instance running on port 8100
            logger.debug("connect to an OpenOffice.org instance running on port 8100");
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(
                    "127.0.0.1", 8100);
            connection.connect();
            logger.debug("connected to an OpenOffice.org instance running on port 8100");
            // convert
//            DocumentConverter converter = new OpenOfficeDocumentConverter(
//                    connection);
            logger.debug("convert docx to pdf");
            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);

            // close the connection
            logger.debug("close the openoffice connection");
            connection.disconnect();

            return 0;
        } catch (ConnectException e) {
//            e.printStackTrace();
            logger.error("openoffice连接异常", e.getMessage());
        } catch (IOException e) {
//            e.printStackTrace();
            logger.error("openoffice IO异常", e.getMessage());
        }

        return 1;
    }
}
