package team.yingyingmonster.ccbs.service.serviceinterface;

import team.yingyingmonster.ccbs.database.bean.Company;

public interface CompanyService {
    Company selectCompany(Long accountId);
    Integer recharge(Long companyId, Long rechargePrice);//账户充值
}
