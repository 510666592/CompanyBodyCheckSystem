package team.yingyingmonster.ccbs.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.yingyingmonster.ccbs.database.bean.Account;
import team.yingyingmonster.ccbs.database.mapper.AccountMapper;


/**
 * @author Juer Whang <br/>
 * - project: CompanyCheckSystem
 * - create: 10:23 2018/10/30
 * -
 **/

@Controller
@RequestMapping("/login")
public class LoginAction {
    private AccountMapper acconutMapper;
    private Account account=null;
    @RequestMapping("/login")
    public String index() {
        return "login/login";
    }
    @RequestMapping("/loginAction")
    private  String loginAction(Long accountId,String accountPassword){
//        account=acconutMapper.loginAccount(accountId,accountPassword);
        if(account!=null){
            if(account.getRoleid()==1){
               return "/index";
            }else if (account.getRoleid()==2){
                return "";
            }else if (account.getRoleid()==3){
                return "";
            }else if (account.getRoleid()==4){
                return "";
            }else if(account.getRoleid()>=5){
                return "";
            }
        }
        return "login/login";
    }

}
