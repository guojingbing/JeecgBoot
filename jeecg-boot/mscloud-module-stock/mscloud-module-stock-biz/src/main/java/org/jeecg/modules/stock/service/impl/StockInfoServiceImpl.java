package org.jeecg.modules.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.stock.entity.StockInfo;
import org.jeecg.modules.stock.mapper.StockInfoMapper;
import org.jeecg.modules.stock.service.IStockInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class StockInfoServiceImpl extends ServiceImpl<StockInfoMapper, StockInfo> implements IStockInfoService {
    @Autowired
    private StockInfoMapper stockInfoMapper;
    @Override
    public List<StockInfo> list(Integer type,Integer status){
        return stockInfoMapper.selectByType(type,status);
    }
    @Override
    public List<StockInfo> listNoKline(Integer type, Integer status, String date){
        return stockInfoMapper.selectListNotExistsKline(type, status, date);
    }
    @Override
    public List<StockInfo> listNoProfit(Integer type, Integer year, Integer quarter){
        return stockInfoMapper.selectListNotExistsProfit(type, year,quarter);
    }
    @Override
    public List<StockInfo> listNoGrowth(Integer type, Integer year, Integer quarter){
        return stockInfoMapper.selectListNotExistsGrowth(type, year,quarter);
    }
    @Override
    public List<Map> listMap(Integer type){
        return stockInfoMapper.selectMapByType(type);
    }
    @Override
    public IPage<Map> loadList4API(Integer type, int pageSize, int pageNo, String key) {
        Page<Map> page = new Page<>(pageNo, pageSize);
        return stockInfoMapper.loadList4API(page,type,key);
    }
    @Override
    @Transactional
    public void upStockInfoFromBaoStock(){
        try {
            String[] args = new String[]{"python", "G:\\workgroup\\stock\\python\\stock-basic4java.py"};
            Process pr = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(),"GBK"));
            String line;
            Collection inList=new ArrayList();
            Collection upList=new ArrayList();
            int stockType=2;
            List<StockInfo> stocks=list(stockType,null);
            List<String> codeList=new ArrayList<>();
            if(!CollectionUtils.isEmpty(stocks)){
                for(StockInfo stock:stocks){
                    codeList.add(stock.getCode());
                }
            }
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                JSONArray rsArr=null;
                try{
                    rsArr= JSON.parseArray(line);
                }catch (Exception ex){
                }
                if(rsArr!=null&&rsArr.size()>0){
                    for(Object obj:rsArr) {
                        JSONArray arr = (JSONArray) obj;
                        if (arr != null && arr.size() > 0) {
                            //是否存在历史数据
                            StockInfo stock=null;
                            if(codeList.contains(arr.getString(0).trim())){
                                int index=codeList.indexOf(arr.getString(0).trim());
                                stock=stocks.get(index);
                                if(stock.getStockStatus().shortValue()!=arr.getShort(5).shortValue()){
                                    stock.setCodeName(arr.getString(1).trim());
                                    stock.setOutDate(arr.getDate(3));
                                    stock.setStockStatus(arr.getShort(5));
                                }
                                upList.add(stock);
                            }else if(stockType==arr.getInteger(4)){
                                stock=new StockInfo();
                                stock.setCode(arr.getString(0).trim());
                                stock.setCodeName(arr.getString(1).trim());
                                stock.setIpoDate(arr.getDate(2));
                                stock.setOutDate(arr.getDate(3));
                                stock.setType(arr.getShort(4));
                                stock.setStockStatus(arr.getShort(5));
                                inList.add(stock);
                            }
                        }
                    }
                }
            }
            in.close();
            pr.waitFor();
            if(!CollectionUtils.isEmpty(upList)){
                this.updateBatchById(upList);
                System.out.println(upList.size()+"条证券数据更新完成");
            }
            if(!CollectionUtils.isEmpty(inList)){
                this.saveBatch(inList);
                System.out.println(inList.size()+"条证券数据插入完成");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
