package team.yingyingmonster.ccbs.database;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import team.yingyingmonster.ccbs.common.CommonUtil;
import team.yingyingmonster.ccbs.czh.PdfUitl;
import team.yingyingmonster.ccbs.database.bean.Report;
import team.yingyingmonster.ccbs.database.mapper.czh.AllReportMapper;


public class CzhPdfFile {

    @Autowired
    private AllReportMapper allReportMapper;
    @Test
    public void test(){
        PdfUitl pdfUitl = new PdfUitl();
//        Report report;
//        report=allReportMapper.selectReportSummary(Long.valueOf(1));
        pdfUitl.htmlCodeComeFromFile("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>pdf打印界面</title>\n" +
                "    <link rel=\"stylesheet\" href=\"/assets/css/bootstrap.min.css\">\n" +
                "</head>\n" +
                "<body ng-app=\"pdfapp\">\n" +
                "    <div class=\"container-fluid\" ng-controller=\"pdfcontrol\">\n" +
                "        <div class=\"row\">\n" +
                "            <div  class=\"col-lg-12\">\n" +
                "                <table class=\"table\">\n" +
                "                    <thead>\n" +
                "                        <tr>\n" +
                "                            <td>团检人ID</td>\n" +
                "                            <td>团检人姓名</td>\n" +
                "                            <td>团检时间</td>\n" +
                "                            <td>团检状态</td>\n" +
                "                            <td>操作</td>\n" +
                "                        </tr>\n" +
                "                    </thead>\n" +
                "                    <tbody>\n" +
                "                        <tr ng-repeat=\"x in allreport\">\n" +
                "                            <td>{{x.reportid}}</td>\n" +
                "                            <td>{{x.doctorByDoctorid.accountByAccountid.accountname}}</td>\n" +
                "                            <td>{{x.reportdate}}</td>\n" +
                "                            <td>{{x.reportstate}}</td>\n" +
                "                            <td>\n" +
                "                                <button class=\"btn btn-primary\" ng-click=\"getreportid(x.reportid)\">打印</button>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "                <div id=\"pdf\"></div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "<script src=\"/assets/scripts/jquery.min.js\"></script>\n" +
                "<script src=\"/assets/scripts/bootstrap.min.js\"></script>\n" +
                "<script src=\"/assets/scripts/angular.min.js\"></script>\n" +
                "<script src=\"/assets/scripts/toastr.min.js\"></script>\n" +
                "<script src=\"/assets/scripts/md5.min.js\"></script>\n" +
                "<script src=\"/assets/scripts/base64.min.js\"></script>\n" +
                "<script src=\"/assets/scripts/juer-script.js\"></script>\n" +
                "<script src=\"/assets/ckeditor/ckeditor.js\"></script>\n" +
                "<script src=\"/assets/scripts/pdfobject.js\"></script>\n" +
                "<script src=\"/assets/scripts/pdfobject.min.js\"></script>\n" +
                "<script>\n" +
                "    var pdfapp=angular.module(\"pdfapp\",[]);\n" +
                "    pdfapp.controller(\"pdfcontrol\",['$scope', '$http', function ($scope,$http) {\n" +
                "        var getreportid=0;\n" +
                "        $scope.allreport=[];\n" +
                "        $http.post(\"/all-report/report-list\")\n" +
                "            .then(\n" +
                "                function success(response) {\n" +
                "                    for (let i=0;i<response.data.data.length;i++) {\n" +
                "                        $scope.allreport.push(response.data.data[i]);\n" +
                "                    }\n" +
                "                }\n" +
                "            )\n" +
                "        $scope.getreportid=function (reportid) {\n" +
                "            getreportid=reportid;\n" +
                "            console.log(getreportid);\n" +
                "            $http.post(\"/all-report/report-summary\",getreportid).then(function success(response) {\n" +
                "                console.log(response.data.data)\n" +
                "                PDFObject.embed(response.data.data, \"#pdf\");\n" +
                "            })\n" +
                "        }\n" +
                "    }])\n" +
                "</script>\n" +
                "</html>","D://pdf/1.pdf");
//        pdfUitl.htmlCodeComeFromFile("D://pdf/test.html","D://pdf/1.pdf");
    }
}
