package org.jeecg.modules.cust.cust.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cust.cust.user.entity.CustUserTrack;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户轨迹记录
 * @Author:
 * @Date:   2024-04-03
 * @Version: V1.0
 */
public interface CustUserTrackMapper extends BaseMapper<CustUserTrack> {
    List<Map> getCustUserTracks(@Param("appid") String appid, @Param("adcode") String adcode, @Param("afterDate") String afterDate, @Param("lat") Double lat, @Param("lng") Double lng, @Param("radius") Integer radius);
}
