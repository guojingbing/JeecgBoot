package org.jeecg.modules.zxecg.cust.constant;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/5
 */


public class ReportConst {
    //1~13,15,16心率统计图表|用户记录心电图|室颤|室速|心动过缓|室上速|停搏|房颤|房室传导阻滞|室上性早搏|室性早搏|散点图|其他典型条图|hrv|空白填充|索引页|房性逸搏|实现逸搏|心率震荡|心搏展示
    public final static String REPORT_ALL_ITEM="1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,28,33";

    public final static int REPORT_ITEM_HOME=0;
    public final static int REPORT_ITEM_HR=1;
    public final static int REPORT_ITEM_EVENT=2;
    public final static int REPORT_ITEM_VF=3;
    public final static int REPORT_ITEM_VT=4;
    public final static int REPORT_ITEM_BC=5;
    public final static int REPORT_ITEM_SVT=6;
    public final static int REPORT_ITEM_PAUSE=7;
    public final static int REPORT_ITEM_AF=8;
    public final static int REPORT_ITEM_BLOCK=9;
    public final static int REPORT_ITEM_SVE=10;
    public final static int REPORT_ITEM_VE=11;
    public final static int REPORT_ITEM_BLANK=15;//空白填充
    public final static int REPORT_ITEM_INDEX =16;//索引页
    public final static int REPORT_ITEM_SCATTER=12;
    public final static int REPORT_ITEM_OTHER=13;
    public final static int REPORT_ITEM_HRV_LIST=14;//hrv表格
    public final static int REPORT_ITEM_HRV_CHART=24;//hrv图表
    public final static int REPORT_ITEM_SVEB=17;//房性逸搏
    public final static int REPORT_ITEM_VEB=18;//室性逸搏
    public final static int REPORT_ITEM_HRT=19;//心率震荡
    public final static int REPORT_ITEM_ANNO=20;//报告片段是否显示心搏

    public final static int REPORT_ITEM_SIGN=21;//显示签名
    public final static int REPORT_AUTO_SIGN=29;//分析人自动签名
    public final static int REPORT_ITEM_DATE=23;//打印报告日期
    public final static int REPORT_ITEM_AP=25;//心房起搏
    public final static int REPORT_ITEM_VP=26;//心室起搏
    public final static int REPORT_ITEM_AVP=27;//房室起搏
    public final static int REPORT_ITEM_HOUR_EVENT=28;//每小时心律失常
    public final static int REPORT_ITEM_JEB=30; //交接性逸搏

    public final static int REPORT_VALID_TIME=31;//打印有效时长信息

    public final static int REPORT_ITEM_FREE=32;//报告自由留图项目

    public final static int REPORT_ITEM_FSHR=33;//最快最慢心率

    public final static int REPORT_ITEM_SA=34;//打印房早未下传数量

    public final static int REPORT_ITEM_PRESCRIBER=35;//显示申请医师

    public final static int REPORT_ITEM_ONE_MIN=36;//全程浏览自主留图

    public final static int REPORT_ITEM_YEAR=37;//用户出生年份是否显示

    public final static int REPORT_ITEM_DURATION=38;//是否打印记录时长

}
