package org.jeecg.modules.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.stock.entity.StockInfo;
import org.jeecg.modules.stock.entity.StockKline;
import org.jeecg.modules.stock.exception.TushareAPIException;
import org.jeecg.modules.stock.mapper.StockKlineMapper;
import org.jeecg.modules.stock.service.IStockInfoService;
import org.jeecg.modules.stock.service.IStockKlineService;
import org.jeecg.modules.stock.util.TushareAPIHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class StockKlineServiceImpl extends ServiceImpl<StockKlineMapper, StockKline> implements IStockKlineService {
    @Autowired
    StockKlineMapper enMapper;
    @Resource
    IStockInfoService stockSer;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<StockKline> list(String code, String startDate, String endDate,String frequency){
        return enMapper.selectByDateRange(code,startDate,endDate,null,frequency);
    }
    @Override
    public List<Map> listMap(String code, String startDate, String endDate){
        return enMapper.selectMapByDateRange(code,startDate,endDate);
    }
    @Override
    public IPage<Map> loadList4API(String code, String startDate, String endDate, int pageSize, int pageNo, String key){
        Page<Map> page = new Page<>(pageNo, pageSize);
        return enMapper.loadList4API(page,code,startDate,endDate,key);
    }

    @Override
    public void syncStockValuationFromBaoStock(Integer type,String code, String startDate, String endDate,String frequency,String adjustflag,boolean computeDayline){
        List<StockInfo> stocks=null;
        //批量更新
        if(code==null){
            stocks=stockSer.listNoKline(type,null,endDate);
        }else{
            StockInfo stock=stockSer.getById(code);
            if(stock!=null){
                stocks=new ArrayList<>();
                stocks.add(stock);
            }
            stocks.add(stock);
        }
        if(!CollectionUtils.isEmpty(stocks)){
            System.out.println(stocks.size()+"只证券需要同步"+startDate+"到"+endDate+"的K线数据");
            List<StockKline> list=new ArrayList();
            for(int i=0;i<stocks.size();i++){
                StockInfo stock=stocks.get(i);
                List<StockKline> klines=this.list(stock.getCode(),startDate,endDate,frequency);
                Map dmap=new HashMap();
                StockKline preLine=null;
                if(!CollectionUtils.isEmpty(klines)){
                    for(StockKline kline:klines){
                        dmap.put(DateUtils.formatDate(kline.getDate(),"yyyy-MM-dd"),DateUtils.formatDate(kline.getDate(),"yyyy-MM-dd"));
                    }
                    preLine=klines.get(0);
                }
                try {
                    String[] args = new String[]{"python", "G:\\workgroup\\stock\\python\\stock-kline4java.py",stock.getCode(),startDate,endDate,frequency,adjustflag};
                    Process pr = Runtime.getRuntime().exec(args);
                    BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(),"GBK"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        JSONArray arr=null;
                        try{
                            arr= JSON.parseArray(line);
                        }catch (Exception ex){
                        }
                        if(arr!=null&&arr.size()>0){
                            for(Object obj:arr){
                                JSONArray objArr=(JSONArray)obj;
                                if(objArr!=null&&objArr.size()>0){
//                                System.out.println("re>>>"+arr);
                                    String dateStr=objArr.getString(1);
                                    if(!CollectionUtils.isEmpty(list)){
                                        preLine=list.get(list.size()-1);
                                    }
                                    //不存在的数据插入
                                    if(dmap.isEmpty()||!dmap.containsKey(dateStr)){
                                        StockKline kline=new StockKline();
                                        kline.setCode(objArr.getString(0));
                                        kline.setDate(DateUtils.parseDate(dateStr,"yyyy-MM-dd"));
                                        kline.setOpen(objArr.getDouble(2));
                                        kline.setHigh(objArr.getDouble(3));
                                        kline.setLow(objArr.getDouble(4));
                                        kline.setDayClose(objArr.getDouble(5));
                                        kline.setPreClose(objArr.getDouble(6));
                                        kline.setVolume(objArr.getDouble(7));
                                        kline.setAmount(objArr.getDouble(8));
                                        kline.setTurn(objArr.getDouble(9));
                                        kline.setPctChg(objArr.getDouble(10));
                                        kline.setPbMrq(objArr.getDouble(11));
                                        kline.setPeTtm(objArr.getDouble(12));
                                        kline.setPsTtm(objArr.getDouble(13));
                                        kline.setPcfNcfTtm(objArr.getDouble(14));
                                        kline.setAdjustFlag(objArr.getShort(15));
                                        kline.setTradeStatus(objArr.getShort(16));
                                        kline.setIsSt(objArr.getShort(17));
                                        kline.setFrequency(frequency);

                                        //计算日线
                                        if(computeDayline&&preLine!=null){
                                            kline=computeStockDayLineByPre(preLine,kline);
                                        }
                                        if(kline!=null){
                                            list.add(kline);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //每处理100条保存一次
                    if((i%10==0||i==stocks.size()-1)&&!CollectionUtils.isEmpty(list)){
                        this.saveBatch(list,50);
                        System.out.println(list.size()+"条Kline数据写入完成");
                        list.clear();
                    }
                    System.out.println("证券"+stock.getCode()+"BaoStock Kline数据获取处理完成,剩余"+(stocks.size()-i-1));
                    in.close();
                    pr.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public StockKline computeStockDayLine(String code, String date){
        //查询日期前60日K线数据
        List<StockKline> list=enMapper.selectByDateRange(code,null,date,60,"d");
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        if(!DateUtils.formatDate(list.get(0).getDate(),"yyyy-MM-dd").equalsIgnoreCase(date)){
            return null;
        }
        //计算5、10、20、30、60日线
        double sumClosePrice=0;
        Double vMa5=null,vMa10=null,vMa20=null,vMa30=null,vMa60=null;
        for(int i=0;i<list.size();i++){
            StockKline line=list.get(i);
            sumClosePrice+=line.getDayClose();
            if(i<5){
                vMa5=sumClosePrice/(i+1);
            }else if(i<10){
                vMa10=sumClosePrice/(i+1);
            }else if(i<20){
                vMa20=sumClosePrice/(i+1);
            }else if(i<30){
                vMa30=sumClosePrice/(i+1);
            }else if(i<60){
                vMa60=sumClosePrice/(i+1);
            }
        }
        //更新数据
        StockKline curLine=list.get(0);
        curLine.setVMa5(vMa5);
        curLine.setVMa10(vMa10);
        curLine.setVMa20(vMa20);
        curLine.setVMa30(vMa30);
        curLine.setVMa60(vMa60);
        return curLine;
    }

    @Override
    public void computeAllStockDayLine(Integer type, String startDate, String endDate){
       List<Map> list=enMapper.selectAllWithoutVMaRange(type,startDate,endDate);
       if(CollectionUtils.isEmpty(list)){
           return;
       }
       Collection entities=new ArrayList();
       int i=0;
       for(Map obj:list){
           StockKline kline=this.computeStockDayLine((String) obj.get("code"),DateUtils.formatDate((Date)obj.get("date"),"yyyy-MM-dd"));
           if(kline!=null){
               entities.add(kline);
           }
           i++;
           System.out.println("证券【"+obj.get("code")+"】日线计算完成");
           if(entities.size()==100||entities.size()>0&&i==list.size()-1){
               this.batchUpdateKlines(entities);
               System.out.println(entities.size()+"条日线更新完成，剩余"+(list.size()-i));
               entities.clear();
           }
       }
    }

    @Override
    public StockKline computeStockDayLineByPre(String code, String date){
        //查询日期两日K线数据
        List<StockKline> list=enMapper.selectByDateRange(code,null,date,2,"d");
        if(CollectionUtils.isEmpty(list)||list.size()<2){
            return null;
        }
        if(!DateUtils.formatDate(list.get(0).getDate(),"yyyy-MM-dd").equalsIgnoreCase(date)){
            return null;
        }
        //计算5、10、20、30、60日线
        Double vMa5=null,vMa10=null,vMa20=null,vMa30=null,vMa60=null;
        //EMA(X，N)求X的N日指数平滑移动平均。算法是：若Y=EMA(X，N)，则Y=〔2*X+(N-1)*Y’〕/(N+1)，其中Y’表示上一周期的Y值。
        StockKline curLine=list.get(0);
        StockKline preLine=list.get(1);
        if(preLine.getVMa5()!=null){
            vMa5=(2*curLine.getDayClose()+4*preLine.getVMa5())/6;
        }
        if(preLine.getVMa10()!=null){
            vMa10=(2*curLine.getDayClose()+9*preLine.getVMa10())/11;
        }
        if(preLine.getVMa20()!=null){
            vMa20=(2*curLine.getDayClose()+19*preLine.getVMa20())/21;
        }
        if(preLine.getVMa30()!=null){
            vMa30=(2*curLine.getDayClose()+29*preLine.getVMa30())/31;
        }
        if(preLine.getVMa60()!=null){
            vMa60=(2*curLine.getDayClose()+59*preLine.getVMa60())/61;
        }

        //更新数据
        curLine.setVMa5(vMa5);
        curLine.setVMa10(vMa10);
        curLine.setVMa20(vMa20);
        curLine.setVMa30(vMa30);
        curLine.setVMa60(vMa60);
        return curLine;
    }

    @Override
    public StockKline computeStockDayLineByPre(StockKline preLine, StockKline curLine){
        if(preLine==null||curLine==null){
            return null;
        }
        //计算5、10、20、30、60日线
        Double vMa5=null,vMa10=null,vMa20=null,vMa30=null,vMa60=null;
        //EMA(X，N)求X的N日指数平滑移动平均。算法是：若Y=EMA(X，N)，则Y=〔2*X+(N-1)*Y’〕/(N+1)，其中Y’表示上一周期的Y值。
        if(preLine.getVMa5()!=null){
            vMa5=(2*curLine.getDayClose()+4*preLine.getVMa5())/6;
        }
        if(preLine.getVMa10()!=null){
            vMa10=(2*curLine.getDayClose()+9*preLine.getVMa10())/11;
        }
        if(preLine.getVMa20()!=null){
            vMa20=(2*curLine.getDayClose()+19*preLine.getVMa20())/21;
        }
        if(preLine.getVMa30()!=null){
            vMa30=(2*curLine.getDayClose()+29*preLine.getVMa30())/31;
        }
        if(preLine.getVMa60()!=null){
            vMa60=(2*curLine.getDayClose()+59*preLine.getVMa60())/61;
        }

        //更新数据
        curLine.setVMa5(vMa5);
        curLine.setVMa10(vMa10);
        curLine.setVMa20(vMa20);
        curLine.setVMa30(vMa30);
        curLine.setVMa60(vMa60);
        return curLine;
    }

    @Override
    public void computeAllStockDayLineByPre(Integer type, String date){
        List<StockInfo> stocks=stockSer.list(null,1);
        if(CollectionUtils.isEmpty(stocks)){
            return;
        }
        List entities=new ArrayList();
        for(int i=0;i<stocks.size();i++){
            StockInfo stock=stocks.get(i);
            StockKline line=this.computeStockDayLineByPre(stock.getCode(),date);
            entities.add(line);
            if(entities.size()==100||entities.size()>0&&i==stocks.size()-1){
                this.batchUpdateKlines(entities);
                System.out.println(entities.size()+"条日线更新完成，剩余"+(stocks.size()-i));
                entities.clear();
            }
        }
    }

    @Override
    @Transactional
    public void computeStockMaxMin(String code, String curDate){
        //查询日期前一年K线数据
        try {
            Date sDate=org.apache.commons.lang.time.DateUtils.addYears(DateUtils.parseDate(curDate,"yyyy-MM-dd"),-1);
            String startDate=DateUtils.formatDate(sDate,"yyyy-MM-dd");
            List<StockKline> list=enMapper.selectByDateRange(code,startDate,curDate,null,"d");
            if(CollectionUtils.isEmpty(list)){
                return;
            }
            double sumClosePrice=0;
            Double max=null,min=null,avg=null,mid=null;
            for(int i=0;i<list.size();i++){
                StockKline line=list.get(i);
                sumClosePrice+=line.getDayClose();
                if(max==null||max<line.getDayClose()){
                    max=line.getDayClose();
                }
                if(min==null||min>line.getDayClose()){
                    min=line.getDayClose();
                }
                avg=sumClosePrice/(i+1);
                if(max!=null&&min!=null){
                    mid=(max+min)/2;
                }
            }
            //更新数据
            StockInfo stock=stockSer.getById(code);
            if(stock!=null){
                stock.setYearHigh(max);
                stock.setYearLow(min);
                stock.setYearAvg(avg);
                stock.setYearMid(mid);
                stockSer.saveOrUpdate(stock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void computeAllStockMaxMin(Integer type, String curDate){
        List<StockInfo> list = stockSer.list(type,null);
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        for(StockInfo obj:list){
            this.computeStockMaxMin(obj.getCode(),curDate);
        }
    }

    @Override
    @Transactional
    public void batchUpdateKlines(Collection klines){
        this.saveOrUpdateBatch(klines);
    }

    @Override
    @Transactional
    public void getKlineDaily(List<String> codes,String startDate,String endDate){
        JSONObject params=new JSONObject();
        if(!CollectionUtils.isEmpty(codes)){
            params.put("ts_code",StringUtils.join(codes, ","));
        }
        if(!StringUtils.isBlank(startDate)){
            params.put("start_date",startDate);
        }
        if(!StringUtils.isBlank(endDate)){
            params.put("end_date",endDate);
        }
        try {
            //获取指定日期范围日线行情
            JSONObject obj = TushareAPIHttpClient.executePostAPI("daily",params,null);
            if(obj==null){
                return;
            }
        } catch (TushareAPIException e) {
            e.printStackTrace();
        }
    }
}
