package team.yingyingmonster.ccbs.service.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yingyingmonster.ccbs.database.bean.Company;
import team.yingyingmonster.ccbs.database.mapper.zhw.ZhaoCompanyMapper;
import team.yingyingmonster.ccbs.service.serviceinterface.CompanyService;

/**
 * @author Zhao Han Wei <br/>
 * - project: CompanyBodyCheckSystem
 * - create: 9:43 2018/11/9
 * - Company接口实现类
 **/
@Service
public class CompanyServiceImplement implements CompanyService {

    @Autowired
    private ZhaoCompanyMapper zhaoCompanyMapper;

    @Override
    public Company selectCompany(Long accountId) {
        Company company = null;
        company = zhaoCompanyMapper.selectCompany(accountId);
        return company;
    }

    //账户充值
    @Override
    public Integer recharge(Long companyId, Long companyPrice) {
        return zhaoCompanyMapper.recharge(companyId, companyPrice);
    }
}
