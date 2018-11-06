package team.yingyingmonster.ccbs.controller.action;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;
import team.yingyingmonster.ccbs.controller.bean.ResultMessage;
import team.yingyingmonster.ccbs.database.bean.Account;
import team.yingyingmonster.ccbs.service.serviceinterface.AccountService;


import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import java.util.List;



/**

 * @author DingLi <br/>

 * - project: CompanyCheckSystem

 * - create: 11:54 2018/10/30

 * - 账号管理

 **/

@Controller

@RequestMapping("/account")

public class AccountAction {
    private List accountList;
    @Resource
    private AccountService accountServiceImplement;
    @Resource
    private AccountService accountService;
    @Resource
    private Account account;
    // 获取用户管理的显示数据,需要分页和模糊查找
    @RequestMapping(value = "/accountList")
    @ResponseBody
    public ResultMessage accountList(Account condition, Long startTime, Long endTime) {
        PageInfo<Account> pageInfo = accountService.selectAccountPage(1, 5);
        return ResultMessage.createSuccessMessage("获取成功！", pageInfo);
    }

    //新增用户
    @RequestMapping(value = "/addAccount")
    @ResponseBody
    public String addAccount(HttpServletRequest request) {
        account.setAccountname(request.getParameter("accountName"));
        account.setAccountpassword(request.getParameter("accountPassword"));
        account.setRoleid(Long.parseLong(request.getParameter("accountId")));
        accountServiceImplement.insertAccount(account);
        return "redirect:/account/accountList";
    }

    //用户增加插入到数据库
    @RequestMapping(value="/accountAddSave")
    public String accountAddSave(HttpServletRequest request, Account accountBean,
                                 int roleId) {
        int roleid = roleId;
        accountService.insertAccount(accountBean);
        return "redirect:accountList";
    }

}