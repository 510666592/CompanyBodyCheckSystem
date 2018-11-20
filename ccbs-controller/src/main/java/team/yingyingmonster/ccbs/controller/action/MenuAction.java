package team.yingyingmonster.ccbs.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yingyingmonster.ccbs.bean.ResultMessage;
import team.yingyingmonster.ccbs.database.bean.Menu;
import team.yingyingmonster.ccbs.database.mapper.MenuMapper;
import team.yingyingmonster.ccbs.service.serviceinterface.MenuService;


import javax.annotation.Resource;

/**
 * @author DingLi <br/>
 * - project: CompanyCheckSystem
 * - create: 17:37 2018/11/1
 * - 菜单管理
 **/
@Controller
@RequestMapping("/menuList")
public class MenuAction {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuService menuService;

    @RequestMapping("/index")
    public String index() {
        return "background-management/menu-managerment";
    }
    @RequestMapping("/role-menu")
    public String index2() {
        return "background-management/role-management-permissions";
    }
    @RequestMapping("/edit-menu")
    public String index3() {
        return "background-management/menu-edit";
    }
    //修改菜单
    @RequestMapping("/update-menu")
    @ResponseBody
    public ResultMessage updateMenu(@RequestBody Menu menu){
        boolean result = menuService.updateMenu(menu);
        if (result){
            return ResultMessage.createSuccessMessage("修改成功",null);
        }else {
            return ResultMessage.createErrorMessage("修改失败");
        }
    }
}
