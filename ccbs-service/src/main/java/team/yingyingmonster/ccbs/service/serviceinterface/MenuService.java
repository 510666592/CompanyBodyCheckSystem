package team.yingyingmonster.ccbs.service.serviceinterface;

import team.yingyingmonster.ccbs.database.bean.Account;
import team.yingyingmonster.ccbs.database.bean.Menu;
import java.util.List;

/**
 * @author Juer Whang <br/>
 * - project: CompanyCheckSystem
 * - create: 17:07 2018/10/31
 * - 菜单相关的服务。
 **/
public interface MenuService {
    /**
     * 判断某个账户是否拥有某个菜单的权限，用于进行权限控制。
     * @param accountBean 欲判断的用户
     * @param menuBean 目标按钮
     * @return 判断结果
     */
    boolean hasPremission(Account accountBean, Menu menuBean);

    /**
     * 根据角色ID获取相应的菜单权限。
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<Menu> selectAccountMenuByRoleId(Long roleId);
    boolean updateMenu(Menu menu);//修改菜单
}
