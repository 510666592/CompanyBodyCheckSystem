package team.yingyingmonster.ccbs.database.mapper.czh;

import team.yingyingmonster.ccbs.database.bean.Report;

import java.util.List;

public interface AllReportMapper {
    public List<Report> selectAllReport();
    public Report selectReportSummary(Long reportid);
}
