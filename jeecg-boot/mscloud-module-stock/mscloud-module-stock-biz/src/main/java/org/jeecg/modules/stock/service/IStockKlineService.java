package org.jeecg.modules.stock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.stock.entity.StockKline;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 证券日频估值业务
 */
public interface IStockKlineService extends IService<StockKline> {
    /**
     * 查询指定证券的日频估值信息列表
     * @param code
     * @param startDate
     * @param endDate
     * @param frequency
     * @return
     */
    List<StockKline> list(String code, String startDate, String endDate,String frequency);
    /**
     * 查询指定证券的日频估值信息返回Map集合
     * @param code
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map> listMap(String code, String startDate, String endDate);
    /**
     * 分页查询
     * @param code
     * @param pageSize
     * @param pageNo
     * @param key
     * @return
     */
    IPage<Map> loadList4API(String code, String startDate, String endDate, int pageSize, int pageNo, String key);

    /**
     * 从BaoStock更新证券日频估值
     * @param type 证券类型，code为空时有效
     * @param code
     * @param startDate
     * @param endDate
     * @param frequency 数据类型，默认为d，日k线；d=日k线、w=周、m=月、5=5分钟、15=15分钟、30=30分钟、60=60分钟k线数据，不区分大小写；指数没有分钟线数据；周线每周最后一个交易日才可以获取，月线每月最后一个交易日才可以获取。
     * @param adjustflag 复权类型，默认不复权：3；1：后复权；2：前复权。已支持分钟线、日线、周线、月线前后复权。
     * @param computeDayline 是否计算日线
     */
    void syncStockValuationFromBaoStock(Integer type, String code, String startDate, String endDate,String frequency,String adjustflag,boolean computeDayline);

    /**
     * 计算股票指定日期的日线数据
     * @param code
     * @param date
     */
    StockKline computeStockDayLine(String code, String date);

    /**
     * 计算所有K线的日线
     * @param type
     * @param startDate
     * @param endDate
     */
    void computeAllStockDayLine(Integer type, String startDate, String endDate);

    /**
     * 根据前一天结果计算日线信息
     * @param code
     * @param date
     * @return
     */
    StockKline computeStockDayLineByPre(String code, String date);

    StockKline computeStockDayLineByPre(StockKline pre, StockKline cur);

    /**
     * 根据前一天结果计算指定日期所有证券K线日线数据
     * @param type
     * @param date
     */
    void computeAllStockDayLineByPre(Integer type, String date);

    /**
     * 计算证券的年度滚动最高、最低、平均、中位
     * @param code
     * @param curDate
     */
    void computeStockMaxMin(String code, String curDate);

    /**
     * 计算所有证券的年度滚动最高、最低、平均、中位
     * @param type
     * @param curDate
     */
    void computeAllStockMaxMin(Integer type, String curDate);

    void batchUpdateKlines(Collection klines);

    /**
     * 以下方法从Tushare获取数据
     * 日期格式:yyyyMMdd
     */
    /**
     * 获取证券日线
     * @param codes
     * @param startDate
     * @param endDate
     */
    void getKlineDaily(List<String> codes,String startDate,String endDate);
}
