package org.jeecg.modules.stock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.stock.entity.StockGrowth;
import org.jeecg.modules.stock.entity.StockInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 证券季频成长能力业务
 */
public interface IStockGrowthService extends IService<StockGrowth> {
    /**
     * 查询指定证券的季频成长能力信息列表
     * @param code
     * @param year
     * @param quarter
     * @return
     */
    List<StockGrowth> list(String code, Integer year, Integer quarter);
    /**
     * 查询指定证券的季频成长能力信息返回Map集合
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
     * 从BaoStock更新证券季频成长能力
     * @param stock
     * @param year
     * @param quarter
     */
    Collection<StockGrowth> syncStockGrowthFromBaoStock(StockInfo stock, Integer year, Integer quarter);

    /**
     * 按季度同步所有证券的季频成长信息
     * @param year
     * @param quarter
     */
    void syncStockGrowthFromBaoStockAll(Integer year, Integer quarter);
}
