package org.jeecg.modules.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.time.DateUtils;
import org.jeecg.modules.stock.entity.StockInfo;
import org.jeecg.modules.stock.entity.StockProfit;
import org.jeecg.modules.stock.mapper.StockProfitMapper;
import org.jeecg.modules.stock.service.IStockInfoService;
import org.jeecg.modules.stock.service.IStockProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class StockProfitServiceImpl extends ServiceImpl<StockProfitMapper, StockProfit> implements IStockProfitService {
    @Autowired
    StockProfitMapper stockProfitMapper;
    @Resource
    IStockInfoService stockSer;

    @Override
    public List<StockProfit> list(String code, Integer year, Integer quarter){
        return stockProfitMapper.selectStockProfit(code,year,quarter);
    }

    @Override
    public List<Map> listMap(String code, Integer year, Integer quarter){
        return stockProfitMapper.selectStockProfitMap(code,year,quarter);
    }

    @Override
    public IPage<Map> loadList4API(String code, Integer year, Integer quarter, int pageSize, int pageNo, String key) {
        Page<Map> page = new Page<>(pageNo, pageSize);
        return stockProfitMapper.loadList4API(page,code,year,quarter,key);
    }

    @Override
    @Transactional
    public void syncStockProfitFromBaoStock(StockInfo stock, Integer year, Integer quarter){
        Collection list=new ArrayList();
        try {
            String[] args = new String[]{"python", "G:\\workgroup\\stock\\python\\stock-profit4java.py",stock.getCode(),String.valueOf(year),String.valueOf(quarter)};
            Process pr = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(),"GBK"));
            String line;

            while ((line = in.readLine()) != null) {
                JSONArray rsArr=null;
                try{
                    rsArr= JSON.parseArray(line);
                }catch (Exception ex){
                }
                if(rsArr!=null&&rsArr.size()>0) {
                    for (Object obj : rsArr) {
                        JSONArray arr = (JSONArray) obj;
                        if (arr != null && arr.size() > 0) {
                            StockProfit sp=new StockProfit();
                            sp.setYear(year);
                            sp.setQuarter(quarter);
                            sp.setCode(arr.getString(0));
                            sp.setPubDate(arr.getDate(1));
                            sp.setStatDate(arr.getDate(2));
                            sp.setRoeAvg(arr.getDouble(3));
                            sp.setNpMargin(arr.getDouble(4));
                            sp.setGpMargin(arr.getDouble(5));
                            sp.setNetProfit(arr.getDouble(6));
                            sp.setEpsTtm(arr.getDouble(7));
                            sp.setMbRevenue(arr.getDouble(8));
                            sp.setTotalShare(arr.getDouble(9));
                            sp.setLiqaShare(arr.getDouble(10));
                            list.add(sp);
                            System.out.println(list.size()+"条季频盈利能力数据获取完成待提交");
                        }
                    }
                }
            }
            this.saveBatch(list,50);
            System.out.println("证券【"+stock.getCode()+"】"+list.size()+"条季频盈利能力数据写入完成");
            in.close();
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void syncStockProfitFromBaoStockAll(Integer year, Integer quarter){
        List<StockInfo> stocks=stockSer.listNoProfit(1, year,quarter);
        //批量更新
        if(!CollectionUtils.isEmpty(stocks)){
            System.out.println(stocks.size()+"只证券需要同步"+year+"年，"+quarter+"季度盈利能力数据");
            for(int i=0;i<stocks.size();i++){
                StockInfo stock=stocks.get(i);
                this.syncStockProfitFromBaoStock(stock,year,quarter);

                System.out.println("证券【"+stock.getCode()+"】 BaoStock季频盈利能力【"+year+"年，"+quarter+"季度】数据获取处理完成,剩余"+(stocks.size()-i-1));

                //更新证券信息中证券的最新盈利能力信息
                List<StockProfit> list=stockProfitMapper.selectStockProfit(stock.getCode(),null,null);
                if(CollectionUtils.isEmpty(list)){
                    continue;
                }
                if(stock.getPrPubDate()==null|| DateUtils.truncatedCompareTo(stock.getPrPubDate(),list.get(0).getPubDate(), Calendar.DATE)<0){
                    stock.setPrPubDate(list.get(0).getPubDate());
                    stock.setLatestPrId(list.get(0).getId());
                }
                stockSer.saveOrUpdate(stock);

            }
        }
    }
}
