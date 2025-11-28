package org.jeecg.modules.zxecg.phoenix.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.zxecg.phoenix.entity.PhoenixDemoEntity;
import org.jeecg.modules.zxecg.phoenix.service.IPhoenixBussinessService;
import org.jeecg.modules.zxecg.phoenix.util.PhoenixQueryPager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PhoenixBussinessServiceImpl extends PhoenixSupportServiceImpl implements IPhoenixBussinessService {
    @Override
    public void initPhoenixDatabase() {
        super.initPhoenixDatabase();
    }

    @Override
    public void phoenixTest() {
        List<PhoenixDemoEntity> list=new ArrayList<>();
        PhoenixDemoEntity phoenixDemoEntity=new PhoenixDemoEntity();
        phoenixDemoEntity.setId(101L);
        phoenixDemoEntity.setType((short)1);
        phoenixDemoEntity.setName("姓名测试");
        phoenixDemoEntity.setRemark("remark");
        list.add(phoenixDemoEntity);
        phoenixDemoEntity=new PhoenixDemoEntity();
        phoenixDemoEntity.setId(201L);
        phoenixDemoEntity.setType((short)1);
        phoenixDemoEntity.setName("张三修改");
        phoenixDemoEntity.setRemark("备注2");
        list.add(phoenixDemoEntity);
//        phoenixDemoEntity=new PhoenixDemoEntity();
//        phoenixDemoEntity.setType((short)3);
//        phoenixDemoEntity.setName("姓名");
//        phoenixDemoEntity.setRemark("备注");
//        list.add(phoenixDemoEntity);
        try {
//            phoenixSupportService.deleteBatch(list,true);
//            phoenixSupportService.hbaseMajorCompact();

            PhoenixQueryPager pager = new PhoenixQueryPager(Arrays.asList(201L),10,1,"name like '张三%'","id asc,type asc");
            pager=super.query(PhoenixDemoEntity.class,pager);
            if(pager!=null){
                if(CollectionUtils.isNotEmpty(pager.getList())){
                    System.out.println(pager.getList().size());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
