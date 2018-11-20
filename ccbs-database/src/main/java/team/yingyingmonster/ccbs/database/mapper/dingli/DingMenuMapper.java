package team.yingyingmonster.ccbs.database.mapper.dingli;

import org.springframework.stereotype.Repository;
import team.yingyingmonster.ccbs.database.bean.Menu;

@Repository
public interface DingMenuMapper {
    Menu selectByPrimaryKey(Long menuid);
    Integer deleteByPrimaryKey(Long menuid);
    Integer insert(Menu menu);
    Integer insertSelective(Menu menus);
    Integer updateByPrimaryKeySelective(Menu menu);
}
