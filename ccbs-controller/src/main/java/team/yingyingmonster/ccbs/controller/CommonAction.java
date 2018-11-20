package team.yingyingmonster.ccbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yingyingmonster.ccbs.bean.ResultMessage;
import team.yingyingmonster.ccbs.database.bean.Account;
import team.yingyingmonster.ccbs.database.bean.Menu;
import team.yingyingmonster.ccbs.database.mapper.MenuMapper;
import team.yingyingmonster.ccbs.database.mapper.juergenie.JuerMenuMapper;
import team.yingyingmonster.ccbs.service.servicebean.Constant;

import javax.servlet.http.HttpSession;

/**
 * @author Juer Whang <br/>
 * - project: CompanyBodyCheckSystem
 * - create: 11:20 2018/11/5
 * -
 **/
@Controller
@RequestMapping("/common")
public class CommonAction {
    @Autowired
    private JuerMenuMapper juerMenuMapper;

    @RequestMapping("/index")
    public String index() {
        return "common/index";
    }

    @RequestMapping("/get-menu")
    @ResponseBody
    public ResultMessage getMenu(HttpSession session) {
        Account account = (Account) session.getAttribute(Constant.SESSION_LOGIN_ACCOUNT);
        return ResultMessage.createSuccessMessage("success!", juerMenuMapper.selectMenusByRoleid(account.getRoleid()));
    }

    @RequestMapping("/user-info")
    @ResponseBody
    public ResultMessage userInfo(HttpSession session) {
        return ResultMessage.createSuccessMessage("success!", session.getAttribute(Constant.SESSION_LOGIN_ACCOUNT));
    }

    @RequestMapping("/logout")
    @ResponseBody
    public ResultMessage logout(HttpSession session){
        session.removeAttribute(Constant.SESSION_LOGIN_ACCOUNT);
        return ResultMessage.createSuccessMessage("success",null);
    }
}
