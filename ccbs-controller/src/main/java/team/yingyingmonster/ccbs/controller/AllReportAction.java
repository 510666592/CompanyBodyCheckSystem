package team.yingyingmonster.ccbs.controller;

import com.github.pagehelper.PageHelper;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yingyingmonster.ccbs.bean.ResultMessage;
import team.yingyingmonster.ccbs.common.CommonUtil;

import team.yingyingmonster.ccbs.czh.PdfUitl;
import team.yingyingmonster.ccbs.template.PdfUtil;
import team.yingyingmonster.ccbs.database.bean.Report;
import team.yingyingmonster.ccbs.database.mapper.czh.AllReportMapper;

import java.io.*;
import java.util.List;

/**
 * 生成pdf文件，打印
 */
@Controller
@RequestMapping("/all-report")
public class AllReportAction {
    @Autowired
    private AllReportMapper allReportMapper;
    private PdfUtil pdfUtil =new PdfUtil();
    private PdfUitl pdfUitl=new PdfUitl();
    private CommonUtil commonUtil =new CommonUtil();
    /**
     * 跳转界面
     */
    @RequestMapping("/all-report")
    private  String clob(){
        return "all-report/all-report";
    }

    /**
     * 获取团检报告列表
     * @return
     */
    @RequestMapping("/report-list")
    @ResponseBody
    private ResultMessage getAllReport(){
        PageHelper.startPage(1,6);
        PageHelper.orderBy("reportid");
        List<Report> allreport=allReportMapper.selectAllReport();
        return ResultMessage.createSuccessMessage("获取的团检报告",allreport);
    }
    /**
     * 预览界面
     */
    @RequestMapping("/report-summary")
    @ResponseBody
    private ResultMessage getReportSummary(@RequestBody Long reportid) throws IOException, DocumentException {
        String pdfpath = pdfUitl.PdfPath(reportid);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(pdfpath));
            Report report;
            report = allReportMapper.selectReportSummary(reportid);
            String pdfString = new String(CommonUtil.fromBase64(report.getReportsummary()));
            pdfUtil.createPdfFromHtmlFile(pdfString, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ResultMessage.createSuccessMessage("获取pdf文件的路径", pdfpath);
    }
}

