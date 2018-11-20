package team.yingyingmonster.ccbs.service.serviceinterface;

import team.yingyingmonster.ccbs.database.bean.Account;


public interface AccountService {
    Account login(Long accountId, String accountPassword);
    Long addAccount(String accountName,String accountPassword,String companyPhone,String companyEmail);
    Long findCompanyId(Long accountId);//通过账户ID查找公司ID
    Long addAccountInTheBackground(String accountName,String accountPassword,Long roleId);//后台添加用户
    boolean deleteAccount(Long accountId); //删除用户
}
