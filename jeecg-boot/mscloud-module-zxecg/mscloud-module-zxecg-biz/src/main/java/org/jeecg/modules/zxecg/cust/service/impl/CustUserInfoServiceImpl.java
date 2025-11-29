package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.zxecg.cust.entity.CustUserInfo;
import org.jeecg.modules.zxecg.cust.mapper.CustUserInfoMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserInfoService;
import org.jeecg.modules.zxecg.cust.vo.CardInfoVo;
import org.jeecg.modules.zxecg.cust.vo.CustUserInfoVo;
import org.jeecg.modules.zxecg.system.service.ICommBaseCodeService;
import org.jeecg.modules.zxecg.system.vo.CommBaseCodeDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/21
 * @description
 */

@Service
public class CustUserInfoServiceImpl extends ServiceImpl<CustUserInfoMapper, CustUserInfo> implements ICustUserInfoService {
    @Autowired
    private ICommBaseCodeService commBaseCodeService;

    @Override
    public Page<Map> loadListPaging(Page<Map> page, Map<String, Object> likeMap, String column, String order) {
        return page.setRecords(this.baseMapper.loadListPaging(page, likeMap, column, order));
    }

    @Override
    public CustUserInfoVo userInfo(Long userId) {
        CustUserInfoVo userInfo = this.baseMapper.userInfo(userId);
        //查询通用代码配置
        // 查询症状
        if (StringUtils.isNotBlank(userInfo.getSymptom())) {
            String symptoms = userInfo.getSymptom();
            List<String> symptomList = Arrays.asList(symptoms.split(","));
            List<Long> list = symptomList.stream().map(Long::new).collect(Collectors.toList());
            Map<String, CommBaseCodeDetailVO> codeMap = commBaseCodeService.getCodeDetailsByCodeIds("CD203", list);
            String result = StringUtils.join(codeMap.keySet(), ",");
            userInfo.setSymptom(result);
        }
        if (StringUtils.isNotBlank(userInfo.getHabits())) {
            String habits = userInfo.getHabits();
            List<String> habitsList = Arrays.asList(habits.split(","));
            List<Long> list = habitsList.stream().map(Long::new).collect(Collectors.toList());
            Map<String, CommBaseCodeDetailVO> codeMap = commBaseCodeService.getCodeDetailsByCodeIds("CD215", list);
            String result = StringUtils.join(codeMap.keySet(), ",");
            userInfo.setHabits(result);
        }
        if (StringUtils.isNotBlank(userInfo.getMedical())) {
            String medical = userInfo.getMedical();
            List<String> medicalList = Arrays.asList(medical.split(","));
            List<Long> list = medicalList.stream().map(Long::new).collect(Collectors.toList());
            Map<String, CommBaseCodeDetailVO> codeMap = commBaseCodeService.getCodeDetailsByCodeIds("CD214", list);
            String result = StringUtils.join(codeMap.keySet(), ",");
            userInfo.setMedical(result);
        }

        return userInfo;
    }

    @Override
    public Page<Map> loadVoucherListPaging(Page<Map> page, Map<String, Object> likeMap, String column, String order) {
        return page.setRecords(this.baseMapper.loadVoucherListPaging(page, likeMap, column, order));
    }

    @Override
    public Page<Map> loadRelatedListPaging(Page<Map> page, Map<String, Object> likeMap, String column, String order) {
        return page.setRecords(this.baseMapper.loadRelatedListPaging(page, likeMap, column, order));
    }

    @Override
    public CustUserInfo getUserInfoByNoOrTel(String userNo, String userTel) {
        return this.baseMapper.getUserInfoByNoOrTel(userNo, userTel);
    }

    @Override
    public CardInfoVo getVoucherInfo(String cardNo) {
        return this.baseMapper.getVoucherInfo(cardNo);
    }
}
