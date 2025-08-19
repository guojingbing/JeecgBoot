package org.jeecg.modules.zxecg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.zxecg.system.vo.CommBaseCodeDetailVO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommBaseCodeMapper extends BaseMapper<CommBaseCodeDetailVO> {
    /**
     * 根据codeStrings获取指定typeNo字典的字典项
     * @param typeNo
     * @param codeStrings
     * @return
     */
    List<CommBaseCodeDetailVO> getCodeDetailsByCodeStrings(@Param("typeNo") String typeNo, @Param("codeStrings") List<String> codeStrings);

    /**
     * 根据codeNames获取指定typeNo字典的字典项
     * @param typeNo
     * @param codeNames
     * @return
     */
    List<CommBaseCodeDetailVO> getCodeDetailsByCodeNames(@Param("typeNo") String typeNo, @Param("codeNames") List<String> codeNames);
}
