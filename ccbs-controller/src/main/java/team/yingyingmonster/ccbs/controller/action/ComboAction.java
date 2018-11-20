package team.yingyingmonster.ccbs.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yingyingmonster.ccbs.bean.ResultMessage;
import team.yingyingmonster.ccbs.database.mapper.dingli.DingComboMapper;

@Controller
@RequestMapping("/combo-list")
public class ComboAction {
    @Autowired
    private DingComboMapper dingComboMapper;
    @RequestMapping("index")
    public String index(){
        return "/background-management/combo-management";
    }
    @RequestMapping("/get-comboList")
    @ResponseBody
    public ResultMessage getCombo() {
        return ResultMessage.createSuccessMessage("success!", dingComboMapper.selectAllCombo());
    }
}
