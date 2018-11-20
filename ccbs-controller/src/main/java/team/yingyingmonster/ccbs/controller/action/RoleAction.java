package team.yingyingmonster.ccbs.controller.action;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yingyingmonster.ccbs.bean.ResultMessage;
import team.yingyingmonster.ccbs.database.mapper.dingli.DingRoleMapper;

/**
 * @author DingLi <br/>
 * - project: CompanyBodyCheckSystem
 * - create: 15:34 2018/11/8
 * - 角色管理
 **/

    @Controller
    @RequestMapping("/role-list")
    public class RoleAction {
        @Autowired
        private DingRoleMapper dingRoleMapper;
        @RequestMapping("/index")
        public String index() {
            return "background-management/role-management";
        }
        @RequestMapping("/get-roleList")
        @ResponseBody
        public ResultMessage getRole() {
            PageHelper.orderBy("ROLEID");
            return ResultMessage.createSuccessMessage("success!",dingRoleMapper.selectAllRole());
        }
        //权限设置模态框
    @RequestMapping("/role-permission")
    public String index2() {
        return "background-management/role-management-permissions";
    }
    }

