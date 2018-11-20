package team.yingyingmonster.ccbs.controller.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yingyingmonster.ccbs.bean.ResultMessage;
import team.yingyingmonster.ccbs.database.bean.Company;
import team.yingyingmonster.ccbs.database.mapper.dingli.DingCompanyMapper;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author DingLi <br/>
 * - project: CompanyBodyCheckSystem
 * - 团检单位管理信息列表展示
 **/
@Controller
@RequestMapping("/company-list")
public class CompanyAction {
    @Autowired
    private DingCompanyMapper dingCompanyMapper;
    @RequestMapping("/index")
    public String index() {
        return "background-management/company-management";
    }
    @RequestMapping("/get-companyList")
    @ResponseBody
    public ResultMessage getCompany() {
        PageHelper.startPage(1,6);
        PageHelper.orderBy("COMPANYID");
        return ResultMessage.createSuccessMessage("success!", dingCompanyMapper.selectAllCompany());
    }
    @RequestMapping("/add-company")
    public String addCompany() {
        return "background-management/company-management-add";
    }
}
