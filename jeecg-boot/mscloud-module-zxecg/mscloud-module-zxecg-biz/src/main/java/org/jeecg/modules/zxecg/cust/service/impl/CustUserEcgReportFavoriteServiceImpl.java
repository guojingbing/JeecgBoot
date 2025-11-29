package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportFavorite;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportFavoriteMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportFavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 * @description
 */

@Service
public class CustUserEcgReportFavoriteServiceImpl extends ServiceImpl<CustUserEcgReportFavoriteMapper, CustUserEcgReportFavorite> implements ICustUserEcgReportFavoriteService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFav(Long repId, Long loginUserId) {
        //查询是否存在相同的记录，存在的话则为取消收藏
        CustUserEcgReportFavorite favorite = this.baseMapper.getByRepIdAndUserId(repId, loginUserId);
        if (null != favorite) {
            this.removeById(favorite);
            return;
        }
        favorite = new CustUserEcgReportFavorite();
        favorite.setRepId(repId);
        favorite.setUserId(loginUserId);
        this.save(favorite);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRepId(Long repId) {
        this.baseMapper.deleteByRepId(repId);
    }
}
