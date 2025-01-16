package org.jeecg.modules.zxecg.service;

import org.jeecg.modules.zxecg.vo.ZxecgUserReportVo;
import org.jeecg.modules.zxecg.vo.ZxecgUserVo;

import java.util.List;

/**
 * 从正心平台查询相关信息
 */
public interface IZxecgService {
    /**
     * 查询用户报告信息
     * @param userId
     * @param repDate
     */
    ZxecgUserReportVo getUserReportInfo(String userId, String repDate);

    /**
     * 查询用户亲友信息
     * @param userId
     * @return
     */
    List<ZxecgUserVo> getUserFriends(String userId);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    ZxecgUserVo getUserInfo(String userId);
}
