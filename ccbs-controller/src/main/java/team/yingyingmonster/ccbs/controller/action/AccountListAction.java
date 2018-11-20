package team.yingyingmonster.ccbs.controller.action;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yingyingmonster.ccbs.bean.ResultMessage;
import team.yingyingmonster.ccbs.database.mapper.dingli.AccountListMapper;
import team.yingyingmonster.ccbs.service.serviceinterface.AccountService;

import java.util.Map;

/**
 * @author DingLi <br/>
 * - project: CompanyBodyCheckSystem
 * - create: 9:31 2018/11/7
 * - 账户管理信息列表展示
 **/
@Controller
@RequestMapping("/accountList")
public class AccountListAction {
    @Autowired
    private AccountListMapper accountListMapper;
    @Autowired
    private AccountService accountService;
    @RequestMapping("/index")
    public String index() {
        return "background-management/account-management";
    }
    @RequestMapping("/get-accountList")
    @ResponseBody
    public ResultMessage getAccount() {
        PageHelper.startPage(1,5);
        PageHelper.orderBy("ACCOUNTID");
        return ResultMessage.createSuccessMessage("success!",accountListMapper.selectAllAccount());
    }
    /*
     * 添加用户模态框
     */
   @RequestMapping("/account-add")
    public String addAccount(){
        return "background-management/account-managerment-add";
   }
    /*
     * 添加用户
     */
    @RequestMapping("/account-edit")
    public String editAccount(){
        return "background-management/account-management-edit";
    }
    @RequestMapping("/account-management-add")
    @ResponseBody
    public ResultMessage addAccountInTheBackground(@RequestBody Map<String, Object> map) {
        Long accountId = accountService.addAccountInTheBackground(map.get("accountname").toString(), map.get("accountpassword").toString(), ((Long) map.get("roleid")));
        String msg = "";
        if (accountId > 0) {
            msg = "添加成功";
            return ResultMessage.createSuccessMessage(msg, accountId);
        } else {
            msg = "添加失败";
            return ResultMessage.createErrorMessage(msg);
        }
    }
    /*
     * 后台删除用户
     */
    @RequestMapping("/account-delete")
    @ResponseBody
    public ResultMessage deleteAccount(@RequestBody Long accountId){
        boolean result=accountService.deleteAccount(accountId);
        if (result){
            return ResultMessage.createSuccessMessage("删除成功",null);
        }else {
            return ResultMessage.createErrorMessage("删除失败");
        }
    }
}