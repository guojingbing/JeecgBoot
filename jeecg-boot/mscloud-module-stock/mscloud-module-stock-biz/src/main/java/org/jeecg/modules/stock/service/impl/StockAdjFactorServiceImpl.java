package org.jeecg.modules.stock.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.stock.entity.StockAdjFactor;
import org.jeecg.modules.stock.entity.StockInfo;
import org.jeecg.modules.stock.exception.TushareAPIException;
import org.jeecg.modules.stock.mapper.StockAdjFactorMapper;
import org.jeecg.modules.stock.service.IStockAdjFactorService;
import org.jeecg.modules.stock.service.IStockInfoService;
import org.jeecg.modules.stock.util.TushareAPIHttpClient;
import org.jeecg.modules.stock.util.TushareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class StockAdjFactorServiceImpl extends ServiceImpl<StockAdjFactorMapper, StockAdjFactor> implements IStockAdjFactorService {
    @Autowired
    private StockAdjFactorMapper adjFactorMapper;
    @Resource
    IStockInfoService stockSer;

    @Override
    public List<StockAdjFactor> list(Integer type, Integer status) {
        return null;
    }

    @Override
    @Transactional
    public void getTushareADJFactors(List<String> codes,String startDate,String endDate){
        JSONObject params=new JSONObject();
        if(!CollectionUtils.isEmpty(codes)){
            params.put("ts_code", StringUtils.join(codes, ","));
        }
        if(!StringUtils.isBlank(startDate)){
            params.put("start_date",startDate);
        }
        if(!StringUtils.isBlank(endDate)){
            params.put("end_date",endDate);
        }
        try {
            //获取指定日期范围日线行情
            JSONObject obj = TushareAPIHttpClient.executePostAPI("adj_factor",params,null);
            if(obj==null){
                return;
            }
        } catch (TushareAPIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void syncADJFactors(String startDate,String endDate){
        if(StringUtils.isBlank(endDate)){
            endDate= DateUtils.formatDate(new Date(),"yyyyMMdd");
        }
        JSONObject params=new JSONObject();

        List<StockInfo> stocks=stockSer.list(1,null);
        if(CollectionUtils.isEmpty(stocks)){
            return;
        }
        int step=stocks.size()/100;
        int mod=stocks.size()%100;
        for(int i=0;i<step;i++){
            List<String> codes=new ArrayList<>();
            for(int j=i*100;j<100*(i+1);j++){
                if(j<stocks.size()){
                    StockInfo s=stocks.get(j);
                    codes.add(TushareUtil.baoStcokCodeToTushare(s.getCode()));
                }
            }
            params.put("ts_code", StringUtils.join(codes, ","));

            if(!StringUtils.isBlank(startDate)){
                params.put("start_date",startDate);
            }
            params.put("end_date",endDate);
            try {
                //获取指定日期范围日线行情
                JSONObject obj = TushareAPIHttpClient.executePostAPI("adj_factor",params,null);
                if(obj==null){
                    return;
                }
                List<StockAdjFactor> factors=adjFactorMapper.selectRange(null,startDate,endDate);
                Map hisMap=null;
                if(!CollectionUtils.isEmpty(factors)){
                    hisMap=new HashMap<>();
                    for(StockAdjFactor fac:factors){
                        String key=fac.getCode()+""+DateUtils.formatDate(fac.getTradeDate(),"yyyyMMdd");
                        if(!hisMap.containsKey(key)){
                            hisMap.put(key,fac);
                        }
                    }
                }
            } catch (TushareAPIException e) {
                e.printStackTrace();
            }
        }
    }
}
