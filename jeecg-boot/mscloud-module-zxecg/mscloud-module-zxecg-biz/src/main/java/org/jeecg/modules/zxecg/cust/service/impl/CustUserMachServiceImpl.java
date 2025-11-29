package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserMach;
import org.jeecg.modules.zxecg.cust.entity.CustUserMachBindingLog;
import org.jeecg.modules.zxecg.cust.mapper.CustUserMachMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserMachBindingLogService;
import org.jeecg.modules.zxecg.cust.service.ICustUserMachService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 * @description 用户设备相关操作
 */

@Service
public class CustUserMachServiceImpl extends ServiceImpl<CustUserMachMapper, CustUserMach> implements ICustUserMachService {
    @Resource
    ICustUserMachBindingLogService bindingLogService;

    @Override
    public Page<Map> loadListPaging(Page<Map> page, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        return page.setRecords(this.baseMapper.loadListPaging(page, loginUserId, likeMap, column, order));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void machUnbind(CustUserMach userMach) {
        CustUserMachBindingLog bindingLog = new CustUserMachBindingLog();
        //绑定
        if (userMach.getBindingStatus() == 1) {
            //设置绑定时间为当前时间
            userMach.setBindingTime(new Timestamp(System.currentTimeMillis()));
            //置空解绑时间
            userMach.setUnbindTime(null);
            bindingLog.setOperTime(userMach.getBindingTime());
        } else {
            //设置解绑时间为当前时间
            userMach.setUnbindTime(new Timestamp(System.currentTimeMillis()));
            bindingLog.setOperTime(userMach.getUnbindTime());
        }
        this.updateById(userMach);
        bindingLog.setBindingId(userMach.getBindingId());
        bindingLog.setOperTypeId(userMach.getBindingStatus());
        //标记ios或android
        bindingLog.setDeviceFlag(userMach.getDeviceFlag());
        if (userMach.getDeviceFlag() == 1) {
            bindingLog.setDeviceSn(userMach.getUuid());
        } else {
            bindingLog.setDeviceSn(userMach.getMachMac());
        }
        bindingLogService.save(bindingLog);
    }

    @Override
    public CustUserMach getMachByUserIdAndMachSn(String machSn, Long userId) {
        return this.baseMapper.getMachByUserIdAndMachSn(machSn, userId);
    }
}
