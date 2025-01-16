package org.jeecg.modules.zxecg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.vo.ZxecgUserVo;

import java.util.List;

/**
 * 心电工作站软件报告操作
 * @author tanyn
 * @version JDK 8
 * @date 2024/9/18
 * @description TODO
 */
public interface ZxecgCustUserMapper extends BaseMapper<ZxecgUserVo> {
    /**
     * 查询用户亲友信息
     * @param userId
     * @return
     */
	List<ZxecgUserVo> getUserFriends(@Param("userId") Long userId);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    ZxecgUserVo getUserInfo(@Param("userId") Long userId);
}
