package org.jeecg.modules.cust.cust.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cust.cust.user.entity.CustUserTrack;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户轨迹记录
 * @Author:
 * @Date:   2024-04-03
 * @Version: V1.0
 */
public interface ICustUserTrackService extends IService<CustUserTrack> {
    /**
     * 根据区域编号获取指定省份的用户跟踪信息
     * @param appid 货主小程序appid
     * @param adcode null获取全国信息
     * @param afterDate 制定日期后的最后跟踪记录
     * @param latlng 查询指定位置附近
     * @param radius 附近半径
     * @return
     */
    List<Map> getCustUserTracks(String appid, String adcode, String afterDate, String latlng, Integer radius);
}
