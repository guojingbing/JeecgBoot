package org.jeecg.modules.cust.cust.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.cust.cust.user.entity.CustUserThirdAccount;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Description: 社区分享终端用户
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface CustUserThirdAccountMapper extends BaseMapper<CustUserThirdAccount> {
    IPage<Map> loadList4API(Page<Map> page, BigDecimal lng, BigDecimal lat, String searchKey, String userId, String shopId);
}
