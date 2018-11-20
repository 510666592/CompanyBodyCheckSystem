package team.yingyingmonster.ccbs.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yingyingmonster.ccbs.bean.ResultMessage;
import team.yingyingmonster.ccbs.database.mapper.dingli.DingCheckListMapper;

/**
 * @author DingLi <br/>
 * - project: CompanyBodyCheckSystem
 * - create: 9:43 2018/11/8
 * - 体检细项列表
 **/
@Controller
@RequestMapping("/check-list")
public class CheckAction {
      @Autowired
      private DingCheckListMapper dingCheckListMapper;
      @RequestMapping("/index")
      public String index(){
          return "background-management/check-managerment";
      }
      @RequestMapping("/get-checkList")
      @ResponseBody
      public ResultMessage getCheck() {
            return ResultMessage.createSuccessMessage("success!", dingCheckListMapper.selectAllCheck());
      }
}
