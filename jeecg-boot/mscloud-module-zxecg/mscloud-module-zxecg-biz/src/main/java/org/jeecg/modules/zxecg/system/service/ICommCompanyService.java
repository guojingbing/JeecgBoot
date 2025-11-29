package org.jeecg.modules.zxecg.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.system.entity.CommCompany;

import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/15
 * @description
 */


public interface ICommCompanyService extends IService<CommCompany> {
    List getTreeList(long loginUserId);
}
