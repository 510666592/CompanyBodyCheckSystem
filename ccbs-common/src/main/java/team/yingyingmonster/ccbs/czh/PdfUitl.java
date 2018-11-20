package team.yingyingmonster.ccbs.czh;

import com.itextpdf.text.*;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.*;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class PdfUitl {
    /**
     * 生成pdf文件
     */
    public void htmlCodeComeFromFile(String filePath, String pdfPath) {
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);
        try {
            StyleSheet st = new StyleSheet();
            st.loadTagStyle("body", "leading", "16,0");
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            HTMLWorker htmlWorker = new HTMLWorker(document);
            htmlWorker.parse(new StringReader(filePath));
            document.close();
            System.out.println("文档创建成功");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 中文乱码问题
     */
    public void htmlCodeComeString(String htmlCode, String pdfPath) {
        Document doc = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));
            doc.open();
            // 解决中文问题
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
            Paragraph t = new Paragraph(htmlCode, FontChinese);
            doc.add(t);
            doc.close();
            System.out.println("文档修改成功");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 生成pdf文档的路径
     */
    public String PdfPath(Long reportid){
       String path="D://pdf/";
       String pdf=".pdf";
       return path+reportid+pdf;
    }
}
