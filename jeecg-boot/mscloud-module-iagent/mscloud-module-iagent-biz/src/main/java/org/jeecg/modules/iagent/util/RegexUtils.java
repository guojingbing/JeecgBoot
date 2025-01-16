package org.jeecg.modules.iagent.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 正则表达式工具类
 * @Author: Kingpin
 * @Date: 2024-12-18 14:42:50
 **/
public class RegexUtils {
    /**
     * 查询我二零二四年十二月十六日的报告
     * @param text
     * @return
     */
    public static String parseDate(String text){
        Calendar currentDate = Calendar.getInstance();
        text = text.replace("三十一","31")
                .replace("三十","30")
                .replace("二十九","29")
                .replace("二十八","28")
                .replace("二十七","27")
                .replace("二十六","26")
                .replace("二十五","25")
                .replace("二十四","24")
                .replace("二十三","23")
                .replace("二十二","22")
                .replace("二十一","21")
                .replace("二十","20")
                .replace("十九","19")
                .replace("十八","18")
                .replace("十七","17")
                .replace("十六","16")
                .replace("十五","15")
                .replace("十四","14")
                .replace("十三","13")
                .replace("十二","12")
                .replace("十一","11")
                .replace("十","10")
                .replace("九","9")
                .replace("八","8")
                .replace("七","7")
                .replace("六","6")
                .replace("五","5")
                .replace("四","4")
                .replace("三","3")
                .replace("二","2")
                .replace("一","1")
                .replace("零","0");
        String year=null;
        String month=null;
        String day=null;
        Pattern DATE_PATTERN = Pattern.compile("(\\d{2,4})年(\\d{1,2})月(\\d{1,2})");
        Pattern DATE_PATTERN_YEAR = Pattern.compile("(\\d{2,4})年");
        Pattern DATE_PATTERN_MONTH = Pattern.compile("(\\d{1,2})月");
        Pattern DATE_PATTERN_DAY = Pattern.compile("(\\d{1,2})[日号]");
        Pattern DATE_PATTERN_DAY_1 = Pattern.compile("[月](\\d{1,2})");
        // 使用Matcher进行匹配
        Matcher matcherDay = DATE_PATTERN_DAY.matcher(text);
        // 检查是否有匹配项
        if (matcherDay.find()) {
            // 提取年、月、日
            day = matcherDay.group(1);
        }else{
            // 使用Matcher进行匹配
            Matcher matcherDay1 = DATE_PATTERN_DAY_1.matcher(text);
            if (matcherDay1.find()) {
                // 提取年、月、日
                day = matcherDay1.group(1);
            }
        }
        if(StringUtils.isBlank(day)){
            return null;
        }
        // 使用Matcher进行匹配
        Matcher matcherMonth = DATE_PATTERN_MONTH.matcher(text);
        // 检查是否有匹配项
        if (matcherMonth.find()) {
            // 提取月
            month = matcherMonth.group(1);
        }
        // 使用Matcher进行匹配
        Matcher matcherYear = DATE_PATTERN_YEAR.matcher(text);
        // 检查是否有匹配项
        if (matcherYear.find()) {
            // 提取年
            year = matcherYear.group(1);
            if(year.length()<4){
                String pre=StringUtils.substring(currentDate.get(currentDate.YEAR)+"",0,(4-year.length()));
                year=pre+year;
            }
        }
        if(StringUtils.isBlank(month)){
            month= currentDate.get(currentDate.MONTH)+1+"";
        }

        if(StringUtils.isBlank(year)){
            if(Integer.parseInt(month)>currentDate.get(currentDate.MONTH)+1||Integer.parseInt(month)==currentDate.get(currentDate.MONTH)+1&&Integer.parseInt(day)>currentDate.get(currentDate.DATE)){
                year= currentDate.get(currentDate.YEAR)-1+"";
            }else{
                year= currentDate.get(currentDate.YEAR)+"";
            }
        }

        String dataStr=year;
        return year+"-"+(Integer.parseInt(month)<10?('0'+month):month)+"-"+(Integer.parseInt(day)<10?('0'+day):day);
    }

    /**
     * 汉字转拼音
     * @param chinese
     * @return
     */
    public static String convertToPinyin(String chinese) {
        StringBuilder sb = new StringBuilder();
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for(char c:chinese.toCharArray()){
            if(Character.isWhitespace(c)){
                continue;
            }

            try {
                if(Character.isDigit(c)){
                    String numberChiness=numberToChinese(c-'0');
                    c=numberChiness.charAt(0);
                }
                String[] pinyinArray= PinyinHelper.toHanyuPinyinStringArray(c, outputFormat);
                if(pinyinArray!=null){
                    sb.append(pinyinArray[0]);
                }else{
                    sb.append(c);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     *  数字转中文
     * @param number
     * @return
     */
    public static String numberToChinese(int number) {
        String[] chineseNumbers = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        if(number==0){
            return chineseNumbers[0];
        }
        StringBuilder sb = new StringBuilder();
        int digit;
        while (number > 0) {
            digit = number % 10;
            number /= 10;
            sb.insert(0, chineseNumbers[digit]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String text = "查询我1月4日的报告";
        // 使用Matcher进行匹配
//        System.out.println(parseDate(text));
        System.out.println(convertToPinyin(text));
        System.out.println(numberToChinese(19));
    }
}
