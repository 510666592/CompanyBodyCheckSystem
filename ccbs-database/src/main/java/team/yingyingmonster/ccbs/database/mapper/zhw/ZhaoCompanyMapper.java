package team.yingyingmonster.ccbs.database.mapper.zhw;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.yingyingmonster.ccbs.database.bean.Company;

@Repository
public interface ZhaoCompanyMapper {
    Integer addCompany(Company company);
    Company selectCompany(Long accountId);
    Integer recharge(@Param("companyId") Long companyId, @Param("companyPrice") Long companyPrice);//账户充值
}
