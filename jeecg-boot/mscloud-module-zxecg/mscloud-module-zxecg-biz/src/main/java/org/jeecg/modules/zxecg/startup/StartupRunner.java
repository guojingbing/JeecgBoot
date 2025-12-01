package org.jeecg.modules.zxecg.startup;

import org.jeecg.modules.zxecg.phoenix.service.IPhoenixBussinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {
    @Autowired
    IPhoenixBussinessService phoenixBussinessService;

    @Override
    public void run(ApplicationArguments args){
        System.out.println("StartupRunner start");
        phoenixBussinessService.initPhoenixDatabase();
        System.out.println("StartupRunner end");
    }
}
