package team.yingyingmonster.ccbs.service.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yingyingmonster.ccbs.database.bean.Account;
import team.yingyingmonster.ccbs.database.bean.Menu;
import team.yingyingmonster.ccbs.database.mapper.dingli.DingMenuMapper;
import team.yingyingmonster.ccbs.service.serviceinterface.MenuService;

import javax.xml.ws.Action;
import java.util.List;

/**
 * @author DingLi <br/>
 * - project: CompanyBodyCheckSystem
 * - create: 16:29 2018/11/2
 * - 菜单管理
 **/
@Service
public class MenuServiceImplement implements MenuService {
    @Autowired
    private DingMenuMapper dingMenuMapper;
    @Override
    public boolean hasPremission(Account accountBean, Menu menuBean)
    {
        return false;
    }

    @Override
    public List<Menu> selectAccountMenuByRoleId(Long roleId) {
        return null;
    }

    @Override
    public boolean updateMenu(Menu menu) {
        Integer result=0;
        result = dingMenuMapper.updateByPrimaryKeySelective(menu);
        return result>0;
    }
}
