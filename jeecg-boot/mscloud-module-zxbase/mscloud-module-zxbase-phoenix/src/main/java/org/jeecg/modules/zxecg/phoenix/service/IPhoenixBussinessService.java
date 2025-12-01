package org.jeecg.modules.zxecg.phoenix.service;

/**
 * @Description: Phoenix业务服务
 * @Author: guojingbing
 * @Date:   2025-11-10
 * @Version: V1.0
 */
public interface IPhoenixBussinessService {
    /**
     * 初始化Phoenix数据库
     */
    void initPhoenixDatabase();

    /**
     * 测试Phoenix数据库
     */
    void phoenixTest();
    /**
     * 测试插入
     */
    void phoenixInsertTest();

    /**
     * 测试更新
     */
    void phoenixUpdateTest();

    /**
     * 测试删除
     */
    void phoenixDeleteTest();

    /**
     * 测试查询
     */
    void phoenixQueryTest();
}
