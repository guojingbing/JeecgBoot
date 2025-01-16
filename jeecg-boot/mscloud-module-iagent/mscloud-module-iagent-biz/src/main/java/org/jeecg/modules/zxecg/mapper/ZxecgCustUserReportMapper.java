package org.jeecg.modules.zxecg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.vo.ZxecgUserReportVo;

/**
 * 心电工作站软件报告操作
 * @author tanyn
 * @version JDK 8
 * @date 2024/9/18
 * @description
 */
public interface ZxecgCustUserReportMapper extends BaseMapper<ZxecgUserReportVo> {
    /**
     * 查询用户报告
     * @param userId 必填
     * @param repDate 选填，不指定日志查询用户最近报告
     * @return
     */
	ZxecgUserReportVo getUserReport(@Param("userId") Long userId, @Param("repDate") String repDate);
}
