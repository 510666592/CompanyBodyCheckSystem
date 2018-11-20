package team.yingyingmonster.ccbs.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import team.yingyingmonster.ccbs.bean.ResultMessage;
import team.yingyingmonster.ccbs.database.bean.Account;
import team.yingyingmonster.ccbs.database.bean.Company;
import team.yingyingmonster.ccbs.database.bean.User;
import team.yingyingmonster.ccbs.poi.POIUtils;
import team.yingyingmonster.ccbs.service.servicebean.Constant;
import team.yingyingmonster.ccbs.service.serviceinterface.AccountService;
import team.yingyingmonster.ccbs.service.serviceinterface.CompanyService;
import team.yingyingmonster.ccbs.service.serviceinterface.UserService;
import team.yingyingmonster.ccbs.web.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author Zhao Han Wei <br/>
 * - project: CompanyBodyCheckSystem
 * - create: 16:17 2018/11/5
 * - 团检单位账户信息处理
 **/

@Controller
@RequestMapping("/company-info")
public class AccountAction {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping("/index")
    public String index(){
        return "/company-info/account-info";
    }


    @RequestMapping("/account-register")
    @ResponseBody
    public ResultMessage register(@RequestBody Map<String, Object> map) {
        Long accountId = accountService.addAccount(map.get("accountName").toString(), map.get("accountPassword").toString(), map.get("companyPhone").toString(), map.get("companyEmail").toString());
        String msg = "";
        if (accountId > 0) {
            msg = "注册成功";
            return ResultMessage.createSuccessMessage(msg, accountId);
        } else {
            msg = "注册失败";
            return ResultMessage.createErrorMessage(msg);
        }
    }

    @RequestMapping("/account-info")
    @ResponseBody
    public ResultMessage accountInfo(HttpSession session) {
        Account account = (Account) session.getAttribute(Constant.SESSION_LOGIN_ACCOUNT);
        if (account != null) {
            Company company = companyService.selectCompany(account.getAccountid());
            if (company != null) {
                Map<String, Object> map = new ManagedMap<>();
                map.put("account", account);
                map.put("company", company);
                return ResultMessage.createSuccessMessage(null, map);
            } else {
                return ResultMessage.createErrorMessage("没有获取到账户信息");
            }
        } else {
            return ResultMessage.createErrorMessage("请重新登入");
        }
    }

    /*
    * 添加体检人员名单
    */
    @RequestMapping("/user-list")
    @ResponseBody
    public ResultMessage userNameList(@RequestParam(value = "userNameList", required = true) MultipartFile file, HttpSession session) {
        Long accountId = ((Account) session.getAttribute(Constant.SESSION_LOGIN_ACCOUNT)).getAccountid();
        String filename = "test/files/" + accountId + ".xlsx";
        String path = WebUtil.getRealPath(session, filename);
        File nameFile = new File(path);
        if (!nameFile.getParentFile().exists()) {
            nameFile.getParentFile().mkdirs();
        }
        List<String[]> list = null;
        try {
            file.transferTo(nameFile);
            list = POIUtils.readExcelAndNotCheck(nameFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.createErrorMessage(e.getMessage());
        }
        return ResultMessage.createSuccessMessage("success", list);
    }

    /*
    * 提交提交人员名单
    */
    @RequestMapping("/submit-users")
    @ResponseBody
    public ResultMessage submitUsers(@RequestBody List<String[]> userList, HttpSession session){
        Long accountId=((Account)session.getAttribute(Constant.SESSION_LOGIN_ACCOUNT)).getAccountid();
        Long companyId=accountService.findCompanyId(accountId);
        Integer result=userService.insertUsers(companyId,userList);
        return ResultMessage.createSuccessMessage("success",result);
    }

    /*
     * 查询体检人员名单
     */
    @RequestMapping("/select-users")
    @ResponseBody
    public ResultMessage selectUsers(HttpSession session, @RequestBody Integer pageNum){
        Long accountId=((Account)session.getAttribute(Constant.SESSION_LOGIN_ACCOUNT)).getAccountid();
        Long companyId=accountService.findCompanyId(accountId);
        PageHelper.startPage(pageNum,5);
        List<User> userList=userService.selectUsers(companyId);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return ResultMessage.createSuccessMessage("success", pageInfo);
    }

    /*
     * 插入单个体检人员名单
     */
    @RequestMapping("/add-user")
    @ResponseBody
    public ResultMessage addUser(@RequestBody User user, HttpSession session){
        Long accountId=((Account)session.getAttribute(Constant.SESSION_LOGIN_ACCOUNT)).getAccountid();
        Long companyId=accountService.findCompanyId(accountId);
        Integer result=userService.insertUser(companyId,user);
        return ResultMessage.createSuccessMessage("success",result);
    }

    /*
     * 删除单个体检人员名单
     */
    @RequestMapping("/delete-user")
    @ResponseBody
    public ResultMessage deleteUser(@RequestBody Long userId){
        boolean result=userService.deleteUser(userId);
        if (result){
            return ResultMessage.createSuccessMessage("删除成功",null);
        }else {
            return ResultMessage.createErrorMessage("删除失败");
        }
    }

    /*
     * 修改单个体检人员名单
     */
    @RequestMapping("/update-user")
    @ResponseBody
    public ResultMessage updateUser(@RequestBody User user){
        boolean result = userService.updateUser(user);
        if (result){
            return ResultMessage.createSuccessMessage("修改成功",null);
        }else {
            return ResultMessage.createErrorMessage("修改失败");
        }
    }

    /*
     * 下载体检人员名单
     */
    @RequestMapping("/downlond-template")
    @ResponseBody
    public ResponseEntity<byte[]> downlondTemplate(HttpSession session, HttpServletRequest request) throws IOException {
        String filePath = WebUtil.getRealPath(session, "WEB-INF/company-info");
        String fileName = "体检人员名单模板.xlsx";
        File file = new File(filePath+File.separator+fileName);
        HttpHeaders httpHeaders = new HttpHeaders();
        String downloadFileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
        httpHeaders.setContentDispositionFormData("attachment", downloadFileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.CREATED);
    }

    /*
     * 账户充值
     */
    @RequestMapping("/recharge")
    @ResponseBody
    public ResultMessage recharge(HttpSession session, @RequestBody Long rechargePrice) {
        Long accountId=((Account)session.getAttribute(Constant.SESSION_LOGIN_ACCOUNT)).getAccountid();
        Long companyId=accountService.findCompanyId(accountId);
        Integer result = companyService.recharge(companyId, rechargePrice);
        if (result>0){
            return ResultMessage.createSuccessMessage("success", null);
        }else {
            return ResultMessage.createErrorMessage("faild");
        }
    }



}
