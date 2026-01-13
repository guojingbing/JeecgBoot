package org.jeecg.modules.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.stock.entity.StockAdjFactor;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:
 * @Date: 2020-02-17
 * @Version: V1.0
 */
public interface StockAdjFactorMapper extends BaseMapper<StockAdjFactor> {
    IPage<Map> loadList4API(Page<Map> page, Integer type, String key);
    List<StockAdjFactor> selectRange(@Param("codes") String codes, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
