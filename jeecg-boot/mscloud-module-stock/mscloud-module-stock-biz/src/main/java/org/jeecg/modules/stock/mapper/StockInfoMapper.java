package org.jeecg.modules.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.stock.entity.StockInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description: 证券信息
 * @Author:
 * @Date: 2020-02-17
 * @Version: V1.0
 */
public interface StockInfoMapper extends BaseMapper<StockInfo> {
    List<StockInfo> selectByType(@Param("type") Integer type,@Param("status") Integer status);
    /**
     * 按年度、季度获取没有成长信息的证券列表
     * @param type 证券类型
     * @param year
     * @param quarter
     * @return
     */
    List<StockInfo> selectListNotExistsProfit(@Param("type") Integer type,@Param("year") Integer year,@Param("quarter") Integer quarter);
    List<StockInfo> selectListNotExistsKline(@Param("type") Integer type,@Param("status") Integer status,@Param("date") String date);
    List<StockInfo> selectListNotExistsGrowth(@Param("type") Integer type,@Param("year") Integer year,@Param("quarter") Integer quarter);
    List<Map> selectMapByType(@Param("type") Integer type);
    IPage<Map> loadList4API(Page<Map> page, Integer type, String key);
}
