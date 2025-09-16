package org.jeecg.modules.zxecg.cust.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.zxecg.cust.constant.ReportConst;
import org.jeecg.modules.zxecg.cust.entity.*;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportMapper;
import org.jeecg.modules.zxecg.cust.service.*;
import org.jeecg.modules.zxecg.system.entity.CommCompanyRepTempl;
import org.jeecg.modules.zxecg.system.entity.CommDept;
import org.jeecg.modules.zxecg.system.service.ICommBaseCodeService;
import org.jeecg.modules.zxecg.system.service.ICommCompanyPrescriberService;
import org.jeecg.modules.zxecg.system.service.ICommCompanyRepTemplService;
import org.jeecg.modules.zxecg.system.service.ICommDeptService;
import org.jeecg.modules.zxecg.system.vo.CommBaseCodeDetailVO;
import org.jeecg.modules.zxecg.util.ZxecgDateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */

@Service
public class CustUserEcgReportServiceImpl extends ServiceImpl<CustUserEcgReportMapper, CustUserEcgReport> implements ICustUserEcgReportService {
    @Resource
    ICustUserEcgRecService recService;
    @Resource
    ICustUserInfoService userInfoService;
    @Resource
    ICustUserEcgReportStatisticsService statisticsService;
    @Resource
    ICustUserEcgReportDiagnosisService diagnosisService;
    @Resource
    ICustUserEcgReportFragService reportFragService;
    @Resource
    ICustUserEcgReportOrderService reportOrderService;
    @Resource
    ICommBaseCodeService baseCodeService;
    @Resource
    ICommCompanyRepTemplService repTemplService;
    @Resource
    ICommCompanyPrescriberService companyPrescriberService;
    @Resource
    ICommDeptService deptService;

    /**
     * 列表分页查询
     *
     * @param pageList
     * @param loginUserId
     * @param likeMap
     * @param column
     * @param order
     * @return
     */
    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        List<Map> list = this.baseMapper.loadListPaging(pageList, loginUserId, likeMap, column, order);
        //todo  进一步处理数据
        return pageList.setRecords(list);
    }

    /**
     * 机构迁移
     *
     * @param repId
     * @param deptId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeDept(Long repId, Long deptId) {
        CustUserEcgReport ecgReport = this.getById(repId);
        if (null == ecgReport) {
            return;
        }
        ecgReport.setDeptId(deptId);
        CommDept commDept = deptService.getById(deptId);
        Long companyId = commDept.getCompanyId();
        ecgReport.setCompanyId(companyId);
        this.updateById(ecgReport);
        //更新对应测量记录的部门id
        List<CustUserEcgRec> recList = recService.getEcgListByRepId(repId);
        if (CollectionUtils.isEmpty(recList)) {
            return;
        }
        for (CustUserEcgRec custUserEcgRec : recList) {
            custUserEcgRec.setDeptId(deptId);
            custUserEcgRec.setCompanyId(companyId);
        }
        recService.updateBatchById(recList);
    }

    /**
     * 根据用户和报告类型查询报告列表,按报告结束日期倒序排列前10条报告
     *
     * @param userId
     * @param repType
     * @return
     */
    @Override
    public List<Map> connectRepList(Long userId, Integer repType) {
        CustUserInfo userInfo = userInfoService.getById(userId);
        if (null == userInfo) {
            return null;
        }
        List<Map> list = this.baseMapper.connectRepList(userId, userInfo.getUserName(), repType);
        return list;
    }

    /**
     * 查询报告状态
     *
     * @param repId
     * @return
     */
    @Override
    public Map repState(Long repId) {
        Map result = new HashMap();
        CustUserEcgReport report = this.getById(repId);
        result.put("reanaStatus", report.getReanaStatus());
        result.put("fragStyle", report.getFragStyle() == null ? 0 : report.getFragStyle());
        boolean repContentChanged = false;
        CustUserEcgReportStatistics st = statisticsService.getById(repId);
        String doctorConclusion = null;
        if (st == null) {
            repContentChanged = true;
        } else {
            doctorConclusion = st.getDoctorConclusion();
            long lst = 0;
            long lrst = 0;
            if (st.getLastStatisticsTime() != null) lst = st.getLastStatisticsTime().getTime();
            if (report.getLastRepChangedTime() != null) lrst = report.getLastRepChangedTime().getTime();
            if (lrst > lst) repContentChanged = true;
        }
        result.put("doctorConclusion", doctorConclusion);
        result.put("repContentChanged", repContentChanged);
        return result;
    }

    /**
     * AI解读信息查询
     *
     * @param repId
     * @return
     */
    @Override
    public Map getRepDiagInfo(Long repId) {
        Map repInfo = this.baseMapper.getRepDiagInfo(repId);
        int age = repInfo.get("age") == null ? 0 : Integer.parseInt(repInfo.get("age").toString());
        String userGender = repInfo.get("userGender") == null ? null : ("1".equals(repInfo.get("userGender").toString()) ? "男" : "女");
        String repDesc = "年龄" + age +
                "岁，体重" + repInfo.get("weight") + "kg，性别" + userGender + "，心电分析报告结论显示："
                + (null == repInfo.get("doctorConclusion") ? "" : repInfo.get("doctorConclusion"));
        Map<String, Object> map = new HashMap<>();
        map.put("id", repInfo.get("id"));
        map.put("repId", repId);
        map.put("type", 1);
        map.put("repDesc", repDesc);
        map.put("inDesc", repInfo.get("inDesc"));
        map.put("outDesc", repInfo.get("outDesc"));
        return map;
    }

    /**
     * 解读结论修改
     *
     * @param repId
     * @param content
     * @param inDesc
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void diagUpdate(Long repId, String content, String inDesc) {
        CustUserEcgReportDiagnosis diagnosis = diagnosisService.getDiagnosisByRepId(repId);
        if (null == diagnosis) {
            diagnosis = new CustUserEcgReportDiagnosis();
        } else {
            diagnosis.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
        }
        Map info = this.getRepDiagInfo(repId);
        String repDesc = (String) info.get("repDesc");
        diagnosis.setRepDesc(repDesc);
        diagnosis.setRepId(repId);
        diagnosis.setOutDesc(content);
        diagnosis.setType(1);
        diagnosis.setRepDesc(repDesc);
        diagnosis.setInDesc(inDesc);
        diagnosis.setDiagStatus(0);
        diagnosis.setOperTime(new Timestamp(System.currentTimeMillis()));
        diagnosisService.saveOrUpdate(diagnosis);
    }

    /**
     * 查询报告有数据的项目
     *
     * @param repId
     * @return
     */
    @Override
    public Map<String, Object> getRepItems(Long repId) {
        Map<String, Object> map = new HashMap<>();
        //获取打印类目
        CustUserEcgReport report = this.getById(repId);
        List<Integer> dataItems = new ArrayList<>();
        if (report.getRepType() != null && report.getRepType() != 1) {
            dataItems.add(ReportConst.REPORT_ITEM_HRV_LIST);
        }
        List<Integer> catIdList = reportFragService.getCatIdByRepId(repId);
        if (CollectionUtil.isNotEmpty(catIdList)) {
            dataItems.addAll(catIdList);
        }
        //心率统计图表
        dataItems.add(ReportConst.REPORT_ITEM_HR);
        //心率变异性图表 2020-11-28
        dataItems.add(ReportConst.REPORT_ITEM_HRV_CHART);
        //心律失常表格 2020-11-28
        dataItems.add(ReportConst.REPORT_ITEM_HOUR_EVENT);
        //如果没有室早就没有心率震荡，如果有室早就有心率震荡
        if (dataItems.contains(ReportConst.REPORT_ITEM_VE)) {
            dataItems.add(ReportConst.REPORT_ITEM_HRT);
        }
        dataItems.add(ReportConst.REPORT_ITEM_SCATTER);
        Collections.sort(dataItems);
        map.put("dataItems", dataItems);
        //获取医师等信息
        Map<String, Object> hospData = new HashMap<>();
        hospData.put("deptName", report.getDeptName());
        hospData.put("hospitalName", report.getHospitalNumber());
        hospData.put("sectionNumber", report.getSectionNumber());
        hospData.put("userRegFlag", report.getUserRegFlag());
        hospData.put("hisBillNo", report.getHisBillNo());
        CustUserEcgReportOrder reportOrder = reportOrderService.getByRepId(repId);
        if (null != report) {
            hospData.put("prescriberName", reportOrder.getPrescriberName());
            hospData.put("preId", reportOrder.getPreId());
            hospData.put("orderDate", reportOrder.getOrderDate());
        }
        map.put("hospData", hospData);
        //查询所有报告打印项
        List<Map<String, Object>> items = baseCodeService.getPrintItems("CD113");
        map.put("items", items);
        List<Integer> arrList = CollectionUtil.isEmpty(items) ? new ArrayList<>() :
                items.stream().map(m -> Integer.parseInt(m.get("codeId").toString())).collect(Collectors.toList());
        List templCodes = new ArrayList();
        //机构排除项目
        List removeList = new ArrayList();
        CommCompanyRepTempl repTempl = repTemplService.getRepTemplByCompanyId(report.getCompanyId());
        if (null != repTempl) {
            String templStyle = repTempl.getTemplStyle();
            if (StringUtils.isNotBlank(templStyle)) {
                List<String> sArr = Arrays.asList(templStyle.split(","));
                List<Long> codeIds = sArr.stream().map(Long::new).collect(Collectors.toList());
                Map<Long, CommBaseCodeDetailVO> details = baseCodeService.getCodeDetailsByTypeNo("CD213");
                for (Long codeId : codeIds) {
                    CommBaseCodeDetailVO commBaseCodeDetailVO = details.get(codeId);
                    Map<String, Object> codeMap = new HashMap<>();
                    codeMap.put("value", codeId);
                    codeMap.put("text", commBaseCodeDetailVO.getCodeName());
                    templCodes.add(codeMap);
                }
            }
            String excludeItems = repTempl.getExcludeItems();
            if (StringUtils.isNotBlank(excludeItems)) {
                List<String> ss = Arrays.asList(excludeItems.split(","));
                removeList = ss.stream().map(Integer::new).collect(Collectors.toList());
            }
        }
        map.put("templCodes", templCodes);
        map.put("excludeItems", removeList);
        CustUserEcgReportStatistics statistics = statisticsService.getById(repId);
        if (null == statistics) {
            if (CollectionUtils.isNotEmpty(removeList)) {
                arrList.removeAll(removeList);
            }
        } else {
            String lastPrintItems = statistics.getReportItems();
            if (StringUtils.isNotBlank(lastPrintItems)) {
                List<String> arr = Arrays.asList(lastPrintItems.split(","));
                arrList = arr.stream().map(Integer::new).collect(Collectors.toList());
            } else {
                arrList = new ArrayList();
            }
        }
        map.put("lastPrintItems", arrList);
        List<Map<String, Object>> prescriberList = companyPrescriberService.getCompanyPrescriber(report.getCompanyId(), report.getDeptId());
        map.put("prescriberList", prescriberList);
        //todo 判断his信息同步，若需要同步his信息，调用his接口获取患者信息后更新报告信息
        //boolean syncHis=hisApiCallService.chkNeedSyncHisInfo(report.getCompanyId());
        map.put("needSyncHis", false);
        map.put("syncHisBy", false);
        return map;
    }


    /**
     * 编辑报告中的用户信息
     *
     * @param repId
     * @param userName
     * @param birthDate
     * @param userGender
     * @param loginUserId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editReportUserInfo(Long repId, String userName, Date birthDate, Integer userGender, Long loginUserId) {
        CustUserEcgReport report = this.getById(repId);
        if (StringUtils.isNotBlank(userName)) {
            report.setUserName(userName);
        }
        if (null != birthDate) {
            report.setBirthDate(birthDate);
            int age = ZxecgDateUtil.getReportAge(new Date(report.getReportDate().getTime()), birthDate);
            report.setAge(age);
        }
        if (null != userGender) {
            report.setUserGender(userGender);
        }
        if (null != loginUserId) {
            report.setLastModifyUserId(loginUserId);
            report.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
        }
        this.updateById(report);
    }

    /**
     * 合并报告用户列表分页查询
     *
     * @param pageList
     * @param loginUserId
     * @param likeMap
     * @param column
     * @param order
     * @return
     */
    @Override
    public Page<Map> loadMergeUserListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadMergeUserListPaging(pageList, loginUserId, likeMap, column, order));
    }

    /**
     * 查询用户按指定月份统计有报告的日期
     *
     * @param userId
     * @param userName
     * @param startMonth
     * @param endMonth
     * @param loginUserId
     * @return
     */
    @Override
    public List<Map> repDate(Long userId, String userName, String startMonth, String endMonth, Long loginUserId) {
        List<Map> list = this.baseMapper.getRepDateByUser(userId, userName, startMonth, endMonth, loginUserId);
        return list;
    }

    @Override
    public CustUserEcgReportStatistics getRepStatistics(Long repId) {
        return statisticsService.getByRepId(repId);
    }
}
