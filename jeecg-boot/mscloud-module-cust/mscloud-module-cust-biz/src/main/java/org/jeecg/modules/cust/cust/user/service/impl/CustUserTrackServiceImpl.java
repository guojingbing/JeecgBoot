package org.jeecg.modules.cust.cust.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.cust.cust.user.entity.CustUserTrack;
import org.jeecg.modules.cust.cust.user.mapper.CustUserTrackMapper;
import org.jeecg.modules.cust.cust.user.service.ICustUserTrackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户轨迹记录
 * @Author:
 * @Date:   2024-04-03
 * @Version: V1.0
 */
@Service
public class CustUserTrackServiceImpl extends ServiceImpl<CustUserTrackMapper, CustUserTrack> implements ICustUserTrackService {
    @Resource
    CustUserTrackMapper custUserTrackMapper;

    @Override
    public List<Map> getCustUserTracks(String appid, String adcode, String afterDate, String latlng, Integer radius) {
        Double lat=null;
        Double lng=null;
        if(StringUtils.isNotBlank(latlng)){
            String[] latlngArr=latlng.split(",");
            if(latlngArr!=null){
                lat=Double.parseDouble(latlngArr[0]);
                lng=Double.parseDouble(latlngArr[1]);
            }
        }
        return custUserTrackMapper.getCustUserTracks(appid,adcode,afterDate,lat,lng,radius);
    }
}
