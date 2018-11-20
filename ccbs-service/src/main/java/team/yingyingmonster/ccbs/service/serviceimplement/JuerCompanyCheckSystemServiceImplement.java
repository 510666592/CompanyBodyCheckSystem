package team.yingyingmonster.ccbs.service.serviceimplement;

import com.google.gson.JsonObject;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yingyingmonster.ccbs.database.bean.*;
import team.yingyingmonster.ccbs.database.bean.juergenie.JuerCombo;
import team.yingyingmonster.ccbs.database.bean.juergenie.JuerUser;
import team.yingyingmonster.ccbs.database.mapper.ComboMapper;
import team.yingyingmonster.ccbs.database.mapper.CompanyMapper;
import team.yingyingmonster.ccbs.database.mapper.TeamformMapper;
import team.yingyingmonster.ccbs.database.mapper.juergenie.*;
import team.yingyingmonster.ccbs.database.bean.juergenie.JuerCompanyCheckEntity;
import team.yingyingmonster.ccbs.image.QrCodeUtil;
import team.yingyingmonster.ccbs.json.JsonUtil;
import team.yingyingmonster.ccbs.service.servicebean.Constant;
import team.yingyingmonster.ccbs.service.serviceinterface.JuerCompanyCheckSystemService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Juer Whang <br/>
 * - project: CompanyBodyCheckSystem
 * - create: 16:36 2018/11/4
 * - 提供团检报名的服务。
 **/
@Service
public class JuerCompanyCheckSystemServiceImplement implements JuerCompanyCheckSystemService {
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private JuerCompanyMapper juerCompanyMapper;
    @Autowired
    private ComboMapper comboMapper;
    @Autowired
    private JuerComboMapper juerComboMapper;
    @Autowired
    private JuerComboCheckMapper juerComboCheckMapper;
    @Autowired
    private JuerUserMapper juerUserMapper;
    @Autowired
    private JuerUserCheckMapper juerUserCheckMapper;
    @Autowired
    private JuerTeamformMapper juerTeamformMapper;
    @Autowired
    private JuerTeamformCombocheckMapper juerTeamformCombocheckMapper;
    @Autowired
    private JuerBillMapper juerBillMapper;

    /**
     * 获取团检报名用的实体，这个实体用于传输到前台，供前端进行数据操作。
     * @param companyEntity
     * @return
     */
    @Override
    public JuerCompanyCheckEntity getCompanyCheckEntity(Long accountid) {
        Company company = juerCompanyMapper.selectCompanyByAccountId(accountid);
        if (company != null) {
            // 取出所有持久套餐
            JuerCombo comboCondition = new JuerCombo();
            comboCondition.setCombotype(Constant.COMBO_TYPE_LASTING);
            List<JuerCombo> comboList = juerComboMapper.selectJuerComboByCondition(comboCondition);

            JuerCompanyCheckEntity entity = new JuerCompanyCheckEntity();
            entity.setCompany(company);
            entity.setComboList(comboList);

            User condition = new User();
            condition.setCompanyid(company.getCompanyid());
            entity.setUserList(juerUserMapper.selectUsersByCondition(condition));

            return entity;
        } else {
            return null;
        }
    }

    /**
     * 通过团检报名实体，向数据库插入数据。
     * @param juerCompanyCheckEntity
     * @return
     */
    @Override
    public boolean registerCompanyCheck(Account account, JuerCompanyCheckEntity juerCompanyCheckEntity) throws Exception {
        Teamform teamform = new Teamform();
        teamform.setCompanyid(juerCompanyCheckEntity.getCompany().getCompanyid());
        teamform.setTeamformstate(Constant.TEAMFORM_UNDONE);
        teamform.setTeamformid(juerTeamformMapper.getNewId());

//        List<TeamformCombocheck> teamformCombocheckList = generatTeamformCombocheckList(juerCompanyCheckEntity.getComboList(), teamform.getTeamformid());
        List<TeamformCombocheck> teamformCombocheckList = new LinkedList<>();
        List<UserCheck> userCheckList = new LinkedList<>();
        List<Bill> billList = new LinkedList<>();

        // 遍历实体中选中的所有套餐
        for (int i = 0; i < juerCompanyCheckEntity.getSelectCombo().size(); i++) {
            JuerCombo combo = juerCompanyCheckEntity.getSelectCombo().get(i);

            // 构建一个团检套餐实体
            TeamformCombocheck combocheck = new TeamformCombocheck();
            combocheck.setTeamformcombocheckid(juerTeamformCombocheckMapper.getNewId());
            combocheck.setComboid(combo.getComboid());
            combocheck.setTeamformid(teamform.getTeamformid());
            teamformCombocheckList.add(combocheck);

            // 获取套餐对应的检查项
            List<Check> checkList = juerComboCheckMapper.selectChecksByComboid(combo.getComboid());

            // 遍历该套餐中的所有用户，并为其构建相应的个人体检实体
            for (int j = 0; j < combo.getUserList().size(); j++) {
                UserCheck userCheck = new UserCheck();
                userCheck.setUserid(combo.getUserList().get(j).getUserid());
                userCheck.setTeamformcombocheckid(combocheck.getTeamformcombocheckid());
                userCheck.setUsercheckid(juerUserCheckMapper.getNewId());
                userCheckList.add(userCheck);

                // 遍历检查项，为每个用户创建相应的记账实体
                for (int k = 0; k < checkList.size(); k++) {
                    Bill bill = new Bill();
                    bill.setUsercheckid(userCheck.getUsercheckid());
                    bill.setBillid(juerBillMapper.getNewId());
                    bill.setCheckid(checkList.get(k).getCheckid());

                    billList.add(bill);
                }
            }
        }

        Company company = juerCompanyMapper.selectCompanyByAccountId(account.getAccountid());
        Double price = getCompanyCheckPrice(juerCompanyCheckEntity.getSelectCombo());
        if (price > company.getCompanyprice().doubleValue())
            return false;

        if (juerTeamformMapper.insert(teamform)<1)
            throw new Exception("插入团检数据失败 - juerTeamformMapper.insert");
        // 逐一进行插入，并若插入失败则抛出异常，并执行回滚操作。
        if (juerTeamformCombocheckMapper.insertBatch(teamformCombocheckList)<1)
            throw new Exception("插入团检数据失败 - juerTeamformCombocheckMapper.insertBatch");
        if (juerUserCheckMapper.insertBatch(userCheckList)<1)
            throw new Exception("插入团检数据失败 - juerUserCheckMapper.insertBatch");
        if (juerBillMapper.insertBatch(billList)<1)
            throw new Exception("插入团检数据失败 - juerBillMapper.insertBatch");

        company.setCompanyprice(company.getCompanyprice().subtract(new BigDecimal(price)));
        companyMapper.updateByPrimaryKey(company);
        return true;
    }

    @Override
    public Double getCompanyCheckPrice(List<JuerCombo> juerComboList) {
        return juerComboMapper.selectAllPriceByJuerComboList(juerComboList);
    }

    @Override
    public JuerCombo addCustomCombo(List<Check> checkList) throws Exception {
        JuerCombo combo = new JuerCombo();
        combo.setCombotype(Constant.COMBO_TYPE_TEMP);
        combo.setComboid(juerComboMapper.getNewId());
        combo.setComboname("自选套餐");
        combo.setCombosummary(joinCheckName(checkList));
        if (comboMapper.insert(combo) < 1)
            throw new Exception("插入错误 - comboMapper.insert");
        // TODO: 继续插入项目中的检查项目
        return null;
    }

    @Override
    public List<JuerUser> getJuerUser(Long companyid) {
        return juerUserMapper.selectJuerUsersByCompanyid(companyid);
    }

    @Override
    public List<JuerUser> getJuerUsersByTeamformId(Long teamformid) {
        List<UserCheck> userCheckList = juerUserCheckMapper.selectUserChecksByTeamformid(teamformid);
        List<JuerUser> juerUserList = juerUserMapper.selectJuerUsersByUserCheckList(userCheckList);
        for (UserCheck userCheck: userCheckList) {
            for (JuerUser juerUser: juerUserList) {
                System.out.println(userCheck.getUserid() + ", " + juerUser.getUserid());
                if (juerUser.getUserid().equals(userCheck.getUserid()))
                    juerUser.setUsercheckid(userCheck.getUsercheckid());
            }
        }
        return juerUserList;
    }

    @Override
    public byte[] getUserQrcodeByteArray(UserCheck userCheck) {
        JsonObject json = new JsonObject();
        json.addProperty("userid", userCheck.getUserid());
        json.addProperty("userCheckId", userCheck.getUsercheckid());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            if (QrCodeUtil.createQrCode(output, json.toString(), 550, "PNG"))
                return output.toByteArray();
        } catch (WriterException|IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<TeamformCombocheck> generatTeamformCombocheckList(List<JuerCombo> list, Long teamformid) {
        List<TeamformCombocheck> result = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            Combo combo = list.get(i);
            TeamformCombocheck combocheck = new TeamformCombocheck();
            combocheck.setComboid(combo.getComboid());
            combocheck.setTeamformid(teamformid);
            result.add(combocheck);
        }
        return result;
    }

    private String joinCheckName(List<Check> checkList) {
        StringBuilder builder = new StringBuilder();
        for(Check check: checkList) {
            builder.append(check.getCheckname()).append("、");
        }
        return builder.replace(builder.length()-1, builder.length(), "").toString();
    }
}
