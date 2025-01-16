package org.jeecg.modules.cust.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2024-10-10 14:35:17
 **/
public class TestUtil {
    /**
     * 指定范围生成随机数
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumber(int min,int max){
        Random rand=new Random();
        int num=rand.nextInt(max-min+1)+min;
        return num;
    }

    /**
     * 大乐透，排除指定号码生成机选号
     * @param aExcludes 前区排除
     * @param bExcludes 后区排除
     * @return
     */
    public static List<String> lottoryDLTRand(List<Integer> aExcludes,List<Integer> bExcludes){
        List<Integer> aList=new ArrayList<>();
        for(int i=1;i<36;i++ ){
            aList.add(i);
        }
        List<Integer> bList=new ArrayList<>();
        for(int i=1;i<13;i++ ){
            bList.add(i);
        }
        if(!CollectionUtils.isEmpty(aExcludes)){
            for(Integer re:aExcludes){
                aList.remove(re);
            }
        }
        if(!CollectionUtils.isEmpty(bExcludes)){
            for(Integer re:bExcludes){
                bList.remove(re);
            }
        }

        List<Integer> aSelList=new ArrayList<>();
        for(int i=0;i<5;i++){
            int index=getRandomNumber(0,aList.size()-1);
            int selNum=aList.get(index);
            aSelList.add(selNum);
            aList.remove(index);
        }

        List<String> reList=new ArrayList<>();
        aSelList=aSelList.stream().sorted().collect(Collectors.toList());
        String aSelStr=StringUtils.join(aSelList,",");
        reList.add(aSelStr);

        List<Integer> bSelList=new ArrayList<>();
        for(int i=0;i<2;i++){
            int index=getRandomNumber(0,bList.size()-1);
            int selNum=bList.get(index);
            bSelList.add(selNum);
            bList.remove(index);
        }

        bSelList=bSelList.stream().sorted().collect(Collectors.toList());
        String bSelStr=StringUtils.join(bSelList,",");
        reList.add(bSelStr);
        return reList;
    }

    /**
     * 自定义规则机选12注大乐透
     * 规则：
     * 1、人工选择一注
     * 2、所有号码机选一注
     * 3、排除人工选择的号机选X(total-3)注
     * 4、排除机选所有号机选一注
     * @param aSels 前区主选
     * @param bSels 后区主选
     * @param total 总共多少注
     */
    public static void myRuleDLT(List<Integer> aSels,List<Integer> bSels,int total){
        System.out.println("主选："+StringUtils.join(aSels,",")+"    "+StringUtils.join(bSels,","));
        List<String> areList=lottoryDLTRand(null,null);
        System.out.println("全机选："+StringUtils.join(areList,"    "));

        //排除主选后机选
        List<Integer> asList= new ArrayList<>();
        List<Integer> bsList= new ArrayList<>();
        for(int i=0;i<total-3;i++){
            List<String> reList=lottoryDLTRand(aSels,bSels);
            System.out.println("否我机选"+(i+1)+"："+StringUtils.join(reList,"    "));

            for(String num:reList.get(0).split(",")){
                asList.add(Integer.parseInt(num));
            }
            for(String num:reList.get(1).split(",")){
                bsList.add(Integer.parseInt(num));
            }
        }
        //排除机选后再机选
        asList=asList.stream().distinct().sorted().collect(Collectors.toList());
        bsList=bsList.stream().distinct().sorted().collect(Collectors.toList());
        List<String> reList=lottoryDLTRand(asList,bsList);
        System.out.println("信我机选："+StringUtils.join(reList,"    "));
//        System.out.println("否我机选号码："+StringUtils.join(asList,",")+"    "+StringUtils.join(bsList,","));
    }

    public static void main(String[] args){
        List<Integer> aSels= Arrays.asList(6,12,28,30,33);
        List<Integer> bSels= Arrays.asList(2,7);
        myRuleDLT(aSels,bSels,5);
    }
}
