package team.yingyingmonster.ccbs.database.mapper.juergenie;

import team.yingyingmonster.ccbs.database.bean.Menu;

import java.util.List;

public interface JuerMenuMapper {
    List<Menu> selectAllMenu();
    List<Menu> selectMenusByCondition(Menu condition);
    List<Menu> selectMenusByRoleid(Long roleid);
}
