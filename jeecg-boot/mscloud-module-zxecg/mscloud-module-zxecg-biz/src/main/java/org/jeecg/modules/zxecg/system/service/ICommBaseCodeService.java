package org.jeecg.modules.zxecg.system.service;

import org.jeecg.modules.zxecg.system.vo.CommBaseCodeDetailVO;

import java.util.List;
import java.util.Map;

/**
 * 测试接口
 */
public interface ICommBaseCodeService {
    /**
     * 根据codeStrings获取指定字典的字典项
     *
     * @param typeNo
     * @param codeStrings
     * @return 返回map结果以便获取
     */
    Map<String, CommBaseCodeDetailVO> getCodeDetailsByCodeStrings(String typeNo, List<String> codeStrings);

    /**
     * 根据codeNames获取指定字典的字典项
     *
     * @param typeNo
     * @param codeNames
     * @return 返回map结果以便获取
     */
    Map<String, CommBaseCodeDetailVO> getCodeDetailsByCodeNames(String typeNo, List<String> codeNames);

    /**
     * 根据CodeIds获取指定字典的字典项
     *
     * @param typeNo
     * @param codeIds
     * @return 返回map结果以便获取
     */
    Map<String, CommBaseCodeDetailVO> getCodeDetailsByCodeIds(String typeNo, List<Long> codeIds);

    /**
     * 获取指定字典的字典项
     *
     * @param typeNo
     * @return 返回map结果以便获取
     */
    Map<Long, CommBaseCodeDetailVO> getCodeDetailsByTypeNo(String typeNo);

    /**
     * 查询报告打印项
     * @param typeNo
     * @return
     */
    List<Map<String, Object>> getPrintItems(String typeNo);
}
