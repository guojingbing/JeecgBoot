package org.jeecg.modules.cust.cust.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cust.cust.user.entity.CustUser;

import java.util.Map;

/**
 * @Description: 终端用户
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface CustUserMapper extends BaseMapper<CustUser> {
    Map getUserByThirdUserId(@Param("thirdType") String thirdType, @Param("appid") String appid, @Param("thirdUserId") String thirdUserId);
}
