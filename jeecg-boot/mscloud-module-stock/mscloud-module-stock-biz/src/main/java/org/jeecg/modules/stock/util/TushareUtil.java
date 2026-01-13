package org.jeecg.modules.stock.util;

import org.apache.commons.lang.StringUtils;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2021-06-22 17:41:01
 **/
public class TushareUtil {
    public static String baoStcokCodeToTushare(String stockCode) {
        String marketFlag = StringUtils.substringBefore(stockCode, ".");
        String code = StringUtils.substringAfter(stockCode, ".");
        return code + "." + marketFlag.toUpperCase();
    }

    public static void main(String args[]) {
        System.out.println(TushareUtil.baoStcokCodeToTushare("sh.000001"));
        System.out.println(497/100);
    }
}
