package team.yingyingmonster.ccbs.controller.action;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yingyingmonster.ccbs.bean.ResultMessage;
import team.yingyingmonster.ccbs.database.mapper.dingli.DingDeptMapper;

@Controller
@RequestMapping("/dept-list")
public class DeptAction {
    @Autowired
    private DingDeptMapper dingDeptMapper;
    @RequestMapping("/index")
    public String index(){
        return "background-management/dept-management";
    }
    @RequestMapping("/get-deptlist")
    @ResponseBody
    public ResultMessage getRole() {
        PageHelper.orderBy("DEPTID");
        return ResultMessage.createSuccessMessage("success!",dingDeptMapper.selectAllDept());
    }
}
