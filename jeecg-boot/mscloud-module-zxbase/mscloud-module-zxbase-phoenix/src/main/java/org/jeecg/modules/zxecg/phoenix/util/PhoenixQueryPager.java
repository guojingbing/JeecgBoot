package org.jeecg.modules.zxecg.phoenix.util;

import lombok.Data;

import java.util.List;

/**
 * @Description: Phoenix查询分页参数
 */
@Data
public class PhoenixQueryPager {
    private Integer pageSize;//每页记录数
    private Integer pageNo;//当前页码
    private Integer pageCount;//总页数
    private Integer totalCount;//总记录数
    private List<Object> keyParams;//查询主键字段参数
    private String caseSql;//查询条件sql
    private String orderSql;//排序sql
    private List<?> list;//查询结果列表
    public PhoenixQueryPager() {}
    public PhoenixQueryPager(List<Object> keyParams) {
        this.keyParams=keyParams;
    }
    public PhoenixQueryPager(List<Object> keyParams, Integer pageSize) {
        this.keyParams=keyParams;
        this.pageSize=pageSize;
    }
    public PhoenixQueryPager(List<Object> keyParams, Integer pageSize, Integer pageNo) {
        this.keyParams=keyParams;
        this.pageSize=pageSize;
        this.pageNo=pageNo;
    }
    public PhoenixQueryPager(List<Object> keyParams, Integer pageSize, Integer pageNo, String caseSql) {
        this.keyParams=keyParams;
        this.pageSize=pageSize;
        this.pageNo=pageNo;
        this.caseSql=caseSql;
    }
    public PhoenixQueryPager(List<Object> keyParams, Integer pageSize, Integer pageNo, String caseSql, String orderSql) {
        this.keyParams=keyParams;
        this.pageSize=pageSize;
        this.pageNo=pageNo;
        this.caseSql=caseSql;
        this.orderSql=orderSql;
    }
}
