package team.yingyingmonster.ccbs.service.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yingyingmonster.ccbs.database.bean.Account;
import team.yingyingmonster.ccbs.database.bean.Company;
import team.yingyingmonster.ccbs.database.bean.User;
import team.yingyingmonster.ccbs.database.mapper.juergenie.JuerCompanyMapper;
import team.yingyingmonster.ccbs.database.mapper.zhw.ZhaoAccountMapper;
import team.yingyingmonster.ccbs.database.mapper.zhw.ZhaoCompanyMapper;
import team.yingyingmonster.ccbs.database.mapper.zhw.ZhaoUserMapper;
import team.yingyingmonster.ccbs.service.serviceinterface.AccountService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juer Whang <br/>
 * - project: CompanyBodyCheckSystem
 * - create: 11:51 2018/11/2
 * -   该文件由赵寒威接管
 **/
@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private ZhaoAccountMapper zhaoAccountMapper;

    @Autowired
    private JuerCompanyMapper juerCompanyMapper;

    @Autowired
    private ZhaoCompanyMapper zhaoCompanyMapper;


    @Override
    public Account login(Long accountId, String accountPassword) {
        Account account = new Account();
        account.setAccountid(accountId);
        account.setAccountpassword(accountPassword);
        List<Account> accountList = zhaoAccountMapper.selectAccountByCondition(account);
        if (accountList != null) {
            account = accountList.get(0);
            return account;
        } else {
            account = null;
            return account;
        }

    }

    @Override
    public Long addAccount(String accountName, String accountPassword, String companyPhone, String companyEmail) {
        Account account = new Account();
        account.setAccountname(accountName);
        account.setAccountpassword(accountPassword);
        Long insertNum = zhaoAccountMapper.addAccount(account);
        if (insertNum != null) {
            Company company = new Company();
            company.setAccountid(account.getAccountid());
            company.setCompanyphone(companyPhone);
            company.setCompanyemail(companyEmail);
            Integer result = zhaoCompanyMapper.addCompany(company);
            if (result > 0) {
                return account.getAccountid();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 通过账户ID查找公司ID
     */
    @Override
    public Long findCompanyId(Long accountId) {
        Long companyId = juerCompanyMapper.selectCompanyByAccountId(accountId).getCompanyid();
        return companyId;
    }

}
