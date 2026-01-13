package org.jeecg.modules.stock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.stock.entity.StockInfo;
import org.jeecg.modules.stock.entity.StockProfit;

import java.util.List;
import java.util.Map;

/**
 * 证券季频盈利能力业务
 */
public interface IStockProfitService extends IService<StockProfit> {
    /**
     * 查询指定证券的季频盈利能力信息列表
     * @param code
     * @param year
     * @param quarter
     * @return
     */
    List<StockProfit> list(String code, Integer year, Integer quarter);
    /**
     * 查询指定证券的季频盈利能力信息返回Map集合
     * @param code
     * @param year
     * @param quarter
     * @return
     */
    List<Map> listMap(String code, Integer year, Integer quarter);
    /**
     * 分页查询
     * @param code
     * @param pageSize
     * @param pageNo
     * @param key
     * @return
     */
    IPage<Map> loadList4API(String code, Integer year, Integer quarter, int pageSize, int pageNo, String key);

    /**
     * 从BaoStock更新证券季频盈利能力
     * @param stock
     * @param year
     * @param quarter
     */
    void syncStockProfitFromBaoStock(StockInfo stock, Integer year, Integer quarter);

    /**
     * 按季度同步所有证券的季频盈利信息
     * @param year
     * @param quarter
     */
    void syncStockProfitFromBaoStockAll(Integer year, Integer quarter);
}
