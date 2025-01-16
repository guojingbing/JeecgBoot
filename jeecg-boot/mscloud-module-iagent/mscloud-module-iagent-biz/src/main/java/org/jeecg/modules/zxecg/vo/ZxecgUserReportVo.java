package org.jeecg.modules.zxecg.vo;

import lombok.Data;

/**
 * @Description: 正心数据库用户信息
 * @Author: Kingpin
 * @Date: 2024-12-17 18:25:04
 **/
@Data
public class ZxecgUserReportVo {
    private String repId;
    private String repNo;
    private String reportDate;
    private Integer conclusionType;//结论类型：1、医师结论；2、智能筛查结论
    private String repConclusion;//报告结论
    private String pdfFilePath;//pdf相对路径
    private String pdfUrl;//pdf下载地址
    private String hospName;//医院名称
    private String doctorName;//分析医师
}
