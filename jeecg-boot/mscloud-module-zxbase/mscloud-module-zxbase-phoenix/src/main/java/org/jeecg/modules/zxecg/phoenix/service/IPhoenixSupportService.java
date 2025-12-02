package org.jeecg.modules.zxecg.phoenix.service;

import org.jeecg.modules.zxecg.phoenix.util.PhoenixQueryPager;

import java.util.List;

/**
 * @Description: Phoenix支持服务
 * @Author: guojingbing
 * @Date:   2025-11-10
 * @Version: V1.0
 */
public interface IPhoenixSupportService {
    /**
     * 初始化phoenix hbase数据库
     */
    void initPhoenixDatabase();

    /**
     * 创建schema
     * @param schema
     */
    void createSchema(String schema);

    /**
     * 删除schema
     * @param schema
     */
    void dropSchema(String schema);

    /**
     * 通过entity批量建表
     * @param classes
     */
    void createTablesIncludeIndex(List<Class<?>> classes);

    /**
     * 通过entity批量删表
     * @param classes
     */
    void dropTablesIncludeIndex(List<Class<?>> classes);

    /**
     * 创建索引
     * @param clazz
     */
    void createIndex(Class clazz);

    /**
     * 删除索引
     * @param clazz
     */
    void dropIndex(Class clazz);

    /**
     * 创建序列
     * @param sequenceName
     * @param start
     * @param end
     */
    void createSequence(String sequenceName,Integer start,Integer end);

    /**
     * 删除序列
     * @param sequenceName
     */
    void dropSequence(String sequenceName);

    /**
     * 实体批量保存
     * 关键点，集合内所有对象不为空的字段必须相同
     * @param list
     * @param isInsert 是否插入，true插入，false更新
     * @param <T>
     * @throws Exception
     */
    <T> void upsertBatch(List<T> list,boolean isInsert) throws Exception;

    /**
     * 批量删除
     * 按有值的字段删除
     * @param list
     * @param matchAllColumn 是否匹配所有字段,fse只匹配主键字段
     * @param <T>
     * @throws Exception
     */
    <T> void deleteBatch(List<T> list,boolean matchAllColumn) throws Exception;

    /**
     * 分页查询
     * @param clazz
     * @param pager
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> PhoenixQueryPager query(Class<T> clazz, PhoenixQueryPager pager) throws Exception;

    /**
     * 自定义查询语句分页查询
     * @param clazz
     * @param baseSql WHERE前的基础查询语句
     * @param pager
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> PhoenixQueryPager query(Class<T> clazz, String baseSql,PhoenixQueryPager pager) throws Exception;

    /**
     * 执行phoenix 建表、删表 sql语句
     * @param sql
     * @throws Exception
     */
    void excuteSql(String sql) throws Exception;

    /**
     * 执行hbase表的MajorCompact
     * 自动通过注解获取phoenix实体类对应的表名
     */
    void hbaseMajorCompact();
    /**
     * 执行MajorCompact
     * @param tables 表名列表
     */
    void execHbaseTablesMajorCompact(List<String> tables);
}
