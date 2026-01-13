package org.jeecg.modules.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.stock.entity.StockGrowth;
import org.jeecg.modules.stock.entity.StockInfo;
import org.jeecg.modules.stock.mapper.StockGrowthMapper;
import org.jeecg.modules.stock.service.IStockGrowthService;
import org.jeecg.modules.stock.service.IStockInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class StockGrowthServiceImpl extends ServiceImpl<StockGrowthMapper, StockGrowth> implements IStockGrowthService {
    @Autowired
    StockGrowthMapper stockGrowthMapper;
    @Resource
    IStockInfoService stockSer;

    @Override
    public List<StockGrowth> list(String code, Integer year, Integer quarter){
        return stockGrowthMapper.selectEntities(code,year,quarter);
    }

    @Override
    public List<Map> listMap(String code, Integer year, Integer quarter){
        return stockGrowthMapper.selectListMap(code,year,quarter);
    }

    @Override
    public IPage<Map> loadList4API(String code, Integer year, Integer quarter, int pageSize, int pageNo, String key) {
        return null;
    }

    @Override
    @Transactional
    public Collection<StockGrowth> syncStockGrowthFromBaoStock(StockInfo stock, Integer year, Integer quarter){
        Collection list=new ArrayList();
        try {
            String[] args = new String[]{"python", "G:\\workgroup\\stock\\python\\stock-growth4java.py",stock.getCode(),String.valueOf(year),String.valueOf(quarter)};
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
                            StockGrowth sp=new StockGrowth();
                            sp.setYear(year);
                            sp.setQuarter(quarter);
                            sp.setCode(arr.getString(0));
                            sp.setPubDate(arr.getDate(1));
                            sp.setStatDate(arr.getDate(2));
                            sp.setYoyEquity(arr.getDouble(3));
                            sp.setYoyAsset(arr.getDouble(4));
                            sp.setYoyNi(arr.getDouble(5));
                            sp.setYoyEpsBasic(arr.getDouble(6));
                            sp.setYoyPni(arr.getDouble(7));
                            list.add(sp);
                        }
                    }
                }
            }
            in.close();
            pr.waitFor();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void syncStockGrowthFromBaoStockAll(Integer year, Integer quarter){
        List<StockInfo> stocks=stockSer.listNoGrowth(1, year,quarter);
        //批量更新
        if(!CollectionUtils.isEmpty(stocks)){
            System.out.println(stocks.size()+"只证券需要同步"+year+"年，"+quarter+"季度成长能力数据");
            Collection list=new ArrayList();
            for(int i=0;i<stocks.size();i++){
                StockInfo stock=stocks.get(i);
                Collection objs=this.syncStockGrowthFromBaoStock(stock,year,quarter);
                if(!CollectionUtils.isEmpty(objs)){
                    list.addAll(objs);
                }
                if(list.size()>99||list.size()>0&&i==stocks.size()-1){
                    this.saveOrUpdateBatch(list);
                    System.out.println("BaoStock季频成长能力"+list.size()+"条数据写入完成");
                    list.clear();
                }
                System.out.println("证券【"+stock.getCode()+"】 BaoStock季频成长能力【"+year+"年，"+quarter+"季度】数据获取完成,剩余"+(stocks.size()-i-1));
            }
        }
    }
}
