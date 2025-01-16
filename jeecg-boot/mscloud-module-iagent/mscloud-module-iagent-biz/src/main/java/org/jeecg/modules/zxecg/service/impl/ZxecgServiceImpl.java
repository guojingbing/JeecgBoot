package org.jeecg.modules.zxecg.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.modules.zxecg.mapper.ZxecgCustUserMapper;
import org.jeecg.modules.zxecg.mapper.ZxecgCustUserReportMapper;
import org.jeecg.modules.zxecg.service.IZxecgService;
import org.jeecg.modules.zxecg.vo.ZxecgUserReportVo;
import org.jeecg.modules.zxecg.vo.ZxecgUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 正心心电业务操作
 * @Author: Kingpin
 * @Date: 2024-12-18 13:33:40
 **/
@Service
public class ZxecgServiceImpl implements IZxecgService {
    @Resource
    private ZxecgCustUserMapper zxecgCustUserMapper;
    @Resource
    private ZxecgCustUserReportMapper zxecgCustUserReportMapper;

    @Override
    @DS("multi-ecg")
    public ZxecgUserReportVo getUserReportInfo(String userId, String repDate) {
        return zxecgCustUserReportMapper.getUserReport(Long.parseLong(userId),repDate);
    }

    @Override
    @DS("multi-ecg")
    public List<ZxecgUserVo> getUserFriends(String userId) {
        return zxecgCustUserMapper.getUserFriends(Long.parseLong(userId));
    }

    @Override
    @DS("multi-ecg")
    public ZxecgUserVo getUserInfo(String userId) {
        return zxecgCustUserMapper.getUserInfo(Long.parseLong(userId));
    }
}
