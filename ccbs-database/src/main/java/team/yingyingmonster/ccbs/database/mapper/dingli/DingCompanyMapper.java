package team.yingyingmonster.ccbs.database.mapper.dingli;

import org.springframework.stereotype.Repository;
import team.yingyingmonster.ccbs.database.bean.Company;

import java.util.List;

@Repository
public interface DingCompanyMapper {
    Long addCompany(Company company);
    Integer updateByPrimaryKeySelective(Company company);
    List<Company> selectAllCompany();
}
