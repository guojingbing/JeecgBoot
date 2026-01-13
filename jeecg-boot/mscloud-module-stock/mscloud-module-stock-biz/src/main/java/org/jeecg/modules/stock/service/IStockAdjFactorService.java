package org.jeecg.modules.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.stock.entity.StockAdjFactor;

import java.util.List;

/**
 * 证券业务
 */
public interface IStockAdjFactorService extends IService<StockAdjFactor> {
    /**
     * 按分类查询返回对象列表
     * @param type
     * @return
     */
    List<StockAdjFactor> list(Integer type, Integer status);
    /**
     * 获取证券复权因子
     * @param codes
     * @param startDate
     * @param endDate
     */
    void getTushareADJFactors(List<String> codes,String startDate,String endDate);
    void syncADJFactors(String startDate,String endDate);
}
