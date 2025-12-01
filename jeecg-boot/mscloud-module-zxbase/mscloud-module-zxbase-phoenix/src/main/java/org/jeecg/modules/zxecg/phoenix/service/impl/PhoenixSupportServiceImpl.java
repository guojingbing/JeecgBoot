package org.jeecg.modules.zxecg.phoenix.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.jeecg.modules.zxecg.phoenix.config.JdbcTemplateConfig;
import org.jeecg.modules.zxecg.phoenix.service.IPhoenixSupportService;
import org.jeecg.modules.zxecg.phoenix.util.PhoenixQueryPager;
import org.jeecg.modules.zxecg.phoenix.util.PhoenixUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public abstract class PhoenixSupportServiceImpl implements IPhoenixSupportService {
    @Autowired
    @Qualifier("phoenixJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcTemplateConfig jdbcTemplateConfig;
//    @Value("${spring.datasource.phoenix.schema}")
//    private String schema;


    @Override
    public void initPhoenixDatabase() {
        //创建schema
        createSchema(jdbcTemplateConfig.getSchema());
        //扫描代码自定义注解PhoenixEntityAnnotation.ClassAnnotation的实体类建表
        List<Class<?>> classes=PhoenixUtil.scanPhoenixEntities();
        createTablesIncludeIndex(classes);
    }

    @Override
    public void createSchema(String schema) {
        if(StringUtils.isBlank(schema)){
            schema=jdbcTemplateConfig.getSchema();
        }

        String sql=("create schema if not exists "+schema).toUpperCase();
        jdbcTemplate.execute(sql);
    }

    @Override
    public void dropSchema(String schema) {
        String sql=("drop schema if exists "+schema).toUpperCase();
        jdbcTemplate.execute(sql);
    }

    @Override
    public void createTablesIncludeIndex(List<Class<?>> classes) {
        if(CollectionUtils.isEmpty(classes)){
            return;
        }
        for(Class clazz:classes){
            String sql= PhoenixUtil.initHbTableCreateSql(clazz,3);
            jdbcTemplate.execute(sql);
            //创建索引
            List<String> isqls=PhoenixUtil.initHbIndexCreateSql(clazz);
            if(CollectionUtils.isNotEmpty(isqls)){
                for(String isql:isqls){
                    jdbcTemplate.execute(isql);
                }
            }
        }
    }

    @Override
    public void dropTablesIncludeIndex(List<Class<?>> classes) {
        if(CollectionUtils.isEmpty(classes)){
            return;
        }
        for(Class clazz:classes){
            String sql= PhoenixUtil.initHbTableDropSql(clazz);
            jdbcTemplate.execute(sql);
            //删除索引
            List<String> isqls=PhoenixUtil.initHbIndexDropSql(clazz);
            if(CollectionUtils.isNotEmpty(isqls)){
                for(String isql:isqls){
                    jdbcTemplate.execute(isql);
                }
            }
        }
    }

    @Override
    public void createIndex(Class clazz) {
        //创建索引
        List<String> isqls=PhoenixUtil.initHbIndexCreateSql(clazz);
        if(CollectionUtils.isNotEmpty(isqls)){
            for(String isql:isqls){
                jdbcTemplate.execute(isql);
            }
        }
    }

    @Override
    public void dropIndex(Class clazz) {
        //删除索引
        List<String> isqls=PhoenixUtil.initHbIndexDropSql(clazz);
        if(CollectionUtils.isNotEmpty(isqls)){
            for(String isql:isqls){
                jdbcTemplate.execute(isql);
            }
        }
    }

    @Override
    public void createSequence(String sequenceName, Integer start, Integer end) {
        String sql=PhoenixUtil.initHbSequenceCreateSql(jdbcTemplateConfig.getSchema(),sequenceName,start,end);
        jdbcTemplate.execute(sql);
    }

    @Override
    public void dropSequence(String sequenceName) {
        String sql=PhoenixUtil.initHbSequenceDropSql(jdbcTemplateConfig.getSchema(),sequenceName);
        jdbcTemplate.execute(sql);
    }

    @Override
    public <T> void upsertBatch(List<T> dataList,boolean isInsert) throws Exception {
        Map<String, Object> params=PhoenixUtil.initUpsertParams(dataList,isInsert);
        if(params==null||params.isEmpty()){
            return;
        }
        String sql=params.get("sql").toString();
        List<Map<String, Object>> vList = (List<Map<String, Object>>) params.get("datas");
        if(StringUtils.isBlank(sql)||CollectionUtils.isEmpty(vList)){
            return;
        }
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<String, Object> vMap = vList.get(i);
                Iterator<String> keys = vMap.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    ps.setObject(Integer.parseInt(key), vMap.get(key));
                }
            }

            @Override
            public int getBatchSize() {
                return dataList.size();
            }
        });
    }

    @Override
    public <T> void deleteBatch(List<T> dataList,boolean matchAllColumn) throws Exception {
        Map<String, Object> params=PhoenixUtil.initDeleteParams(dataList,matchAllColumn);
        if(params==null||params.isEmpty()){
            return;
        }
        String sql=params.get("sql").toString();
        List<Map<String, Object>> vList = (List<Map<String, Object>>) params.get("datas");
        if(StringUtils.isBlank(sql)||CollectionUtils.isEmpty(vList)){
            return;
        }
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<String, Object> vMap = vList.get(i);
                Iterator<String> keys = vMap.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    ps.setObject(Integer.parseInt(key), vMap.get(key));
                }
            }

            @Override
            public int getBatchSize() {
                return dataList.size();
            }
        });
    }

    @Override
    public <T> PhoenixQueryPager query(Class<T> clazz, PhoenixQueryPager pager) throws Exception{
        String sql=PhoenixUtil.initQuerySql(clazz,pager);
        if(StringUtils.isBlank(sql)){
            return pager;
        }

        List<Object> params=pager.getKeyParams();
        List<T> list=jdbcTemplate.query(sql, ps -> {
            if(CollectionUtils.isNotEmpty(params)){
                for(int i=0;i<params.size();i++){
                    ps.setObject(i+1,params.get(i));
                }
            }
        }, new BeanPropertyRowMapper<>(clazz));

        pager.setList(list);
        return pager;
    }

    @Override
    public void excuteSql(String sql) throws Exception {
        jdbcTemplate.execute(sql);
    }

    @Override
    public void hbaseMajorCompact() {
        List<String> tables=PhoenixUtil.scanPhoenixTables();
        execHbaseTablesMajorCompact(tables);
    }

    @Override
    public void execHbaseTablesMajorCompact(List<String> tables) {
        try {
            Configuration conf = HBaseConfiguration.create();
            org.apache.hadoop.hbase.client.Connection connection = ConnectionFactory.createConnection(conf);
            //管理员对象
            Admin admin = connection.getAdmin();
            String schema = jdbcTemplateConfig.getSchema();
            for (String table : tables) {
                TableName table_name = TableName.valueOf((schema+":"+table).toUpperCase());
                if (admin.tableExists(table_name)) {
                    admin.majorCompact(table_name);
                    System.out.println(new Date()+">>>>>>major compact table:" + table_name.getNameAsString());
                }
            }
            admin.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
