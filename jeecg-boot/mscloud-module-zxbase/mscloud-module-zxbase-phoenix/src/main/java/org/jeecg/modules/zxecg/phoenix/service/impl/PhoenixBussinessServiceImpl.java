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
        //创建sequence
        Object[][] seqs=new Object[][]{{"SEQ_REP_MATCH_KEY",1,null},{"SEQ_ECG_SEG_KEY",1,null},{"SEQ_OPER_LOG_KEY",1,null},{"SEQ_CUST_REQ_LOG_KEY",1,null}};
        for(Object[] seq:seqs){
            createSequence((String)seq[0],(Integer)seq[1],(Integer)seq[2]);
        }
    }

    @Override
    public void phoenixTest() {
        this.initPhoenixDatabase();
        this.phoenixInsertTest();
        this.phoenixQueryTest();
        this.phoenixUpdateTest();
        this.phoenixDeleteTest();
    }

    @Override
    public void phoenixInsertTest() {
        List<PhoenixDemoEntity> list=new ArrayList<>();
        PhoenixDemoEntity phoenixDemoEntity=new PhoenixDemoEntity();
        phoenixDemoEntity.setType((short)1);
        phoenixDemoEntity.setName("姓名");
        phoenixDemoEntity.setRemark("姓名备注");
        list.add(phoenixDemoEntity);
        phoenixDemoEntity=new PhoenixDemoEntity();
        phoenixDemoEntity.setType((short)2);
        phoenixDemoEntity.setName("张三");
        phoenixDemoEntity.setRemark("张三备注");
        list.add(phoenixDemoEntity);
        try {
            super.upsertBatch(list,true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void phoenixUpdateTest() {
        List<PhoenixDemoEntity> list=new ArrayList<>();
        PhoenixDemoEntity phoenixDemoEntity=new PhoenixDemoEntity();
        phoenixDemoEntity.setId(101L);
        phoenixDemoEntity.setType((short)3);
        phoenixDemoEntity.setName("姓名修改");
        phoenixDemoEntity.setRemark("姓名备注修改");
        list.add(phoenixDemoEntity);
        phoenixDemoEntity=new PhoenixDemoEntity();
        phoenixDemoEntity.setId(201L);
        phoenixDemoEntity.setType((short)4);
        phoenixDemoEntity.setName("张三修改");
        phoenixDemoEntity.setRemark("张三备注修改");
        list.add(phoenixDemoEntity);
        try {
            super.upsertBatch(list,false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void phoenixDeleteTest() {
        List<PhoenixDemoEntity> list=new ArrayList<>();
        PhoenixDemoEntity phoenixDemoEntity=new PhoenixDemoEntity();
        phoenixDemoEntity.setId(201L);
        phoenixDemoEntity.setType((short)4);
        phoenixDemoEntity.setName("姓名");
        phoenixDemoEntity.setRemark("姓名备注");
        list.add(phoenixDemoEntity);
        try {
            super.deleteBatch(list,false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void phoenixQueryTest() {
        try {
            PhoenixQueryPager pager = new PhoenixQueryPager(Arrays.asList(201L),10,1,"name like '张三%'","id asc,type asc");
            pager=super.query(PhoenixDemoEntity.class,pager);
            if(pager!=null){
                if(CollectionUtils.isNotEmpty(pager.getList())){
                    System.out.println(pager.getList().size());
                }
            }
            PhoenixQueryPager pager1 = new PhoenixQueryPager(null,10,1,"id in (201,301) and name like '张三%'","id asc,type asc");
            pager1=super.query(PhoenixDemoEntity.class,"select * from PHOENIX_DEMO",pager1);
            if(pager1!=null){
                if(CollectionUtils.isNotEmpty(pager1.getList())){
                    System.out.println(pager1.getList().size());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
