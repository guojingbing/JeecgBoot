package org.jeecg.modules.zxecg.cust.service.phoenix.impl;

import org.jeecg.modules.zxecg.cust.service.phoenix.IPhoenixEcgService;
import org.jeecg.modules.zxecg.phoenix.service.IPhoenixBussinessService;
import org.jeecg.modules.zxecg.phoenix.service.impl.PhoenixSupportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoenixEcgServiceImpl extends PhoenixSupportServiceImpl implements IPhoenixEcgService {
    @Autowired
    IPhoenixBussinessService phoenixBussinessService;
    @Override
    public void phoenixTest() {
        phoenixBussinessService.phoenixTest();
    }
}
