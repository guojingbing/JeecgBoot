package org.jeecg.modules.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.stock.entity.StockKline;

import java.util.List;
import java.util.Map;

/**
 * @Description: 证券日频估值
 * @Author:
 * @Date: 2020-02-17
 * @Version: V1.0
 */
public interface StockKlineMapper extends BaseMapper<StockKline> {
    /**
     * 按日期范围获取指定证券K线数据
     * @param code
     * @param startDate
     * @param endDate
     * @param rows
     * @param frequency
     * @return
     */
    List<StockKline> selectByDateRange(@Param("code") String code, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("rows") Integer rows, @Param("frequency") String frequency);
    List<Map> selectMapByDateRange(@Param("code") String code, @Param("startDate") String startDate, @Param("endDate") String endDate);
    IPage<Map> loadList4API(Page<Map> page, String code, String startDate, String endDate, String key);
    /**
     * 按证券类型获取指定日期范围内的没有日线数据的K线数据
     * @param type
     * @return
     */
    List<Map> selectAllWithoutVMaRange(@Param("type") Integer type, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
