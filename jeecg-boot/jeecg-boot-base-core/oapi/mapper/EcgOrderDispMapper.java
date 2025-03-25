package org.jeecg.modules.openapi.oapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface EcgOrderDispMapper extends BaseMapper<String> {

    Map selectDispByRep(@Param("repId") String repId);


    void updateDispRep(@Param("repId") String repId, @Param("downloadUrl") String downloadUrl);


}
