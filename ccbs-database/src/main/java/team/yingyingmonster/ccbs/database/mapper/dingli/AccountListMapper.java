package team.yingyingmonster.ccbs.database.mapper.dingli;


import org.springframework.stereotype.Repository;
import team.yingyingmonster.ccbs.database.bean.Account;

import java.util.List;
@Repository
public interface AccountListMapper {
   List<Account> selectAllAccount();
   Long addAccount(Account account);
   Integer deleteByPrimaryKey(Long accountId);
}
