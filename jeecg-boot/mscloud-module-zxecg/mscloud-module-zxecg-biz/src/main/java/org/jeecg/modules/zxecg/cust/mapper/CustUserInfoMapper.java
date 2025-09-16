package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserInfo;
import org.jeecg.modules.zxecg.cust.vo.CardInfoVo;
import org.jeecg.modules.zxecg.cust.vo.CustUserInfoVo;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/21
 * @description
 */


public interface CustUserInfoMapper extends BaseMapper<CustUserInfo> {
    List<Map> loadListPaging(Page<Map> page, Map<String, Object> likeMap, String column, String order);

    CustUserInfoVo userInfo(@Param("userId") Long userId);

    List<Map> loadVoucherListPaging(Page<Map> page, Map<String, Object> likeMap, String column, String order);

    List<Map> loadRelatedListPaging(Page<Map> page, Map<String, Object> likeMap, String column, String order);

    CustUserInfo getUserInfoByNoOrTel(@Param("userNo") String userNo, @Param("userTel") String userTel);

    CardInfoVo getVoucherInfo(@Param("cardNo") String cardNo);
}
