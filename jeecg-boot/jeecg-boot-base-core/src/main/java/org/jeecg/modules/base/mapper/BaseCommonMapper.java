package org.jeecg.modules.base.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.dto.LogDTO;
import org.jeecg.common.system.vo.DictModel;

import java.util.List;
import java.util.Map;

/**
 * @Description: BaseCommonMapper
 * @author: jeecg-boot
 */
public interface BaseCommonMapper {

    /**
     * 保存日志
     * @param dto
     */
    @InterceptorIgnore(illegalSql = "true", tenantLine = "true")
    void saveLog(@Param("dto")LogDTO dto);

    /**
     * 通过dict_code查询数据字典子项
     * @param dictCode
     * @return
     */
    List<Map<String,Object>> selectDictItemsByDictCode(@Param("dictCode") String dictCode);
    List<DictModel> queryDictItemsByCode(@Param("code") String code);
}
