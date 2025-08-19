package org.jeecg.modules.zxecg.phoenix.service;

import java.util.List;

import org.jeecg.modules.zxecg.phoenix.entity.JeecgOrderTicket;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 订单机票
 * @Author: jeecg-boot
 * @Date:  2019-02-15
 * @Version: V1.0
 */
public interface IJeecgOrderTicketService extends IService<JeecgOrderTicket> {

    /**
     * 通过订单id查询订单机票
     * @param mainId 订单id
     * @return 订单机票集合
     */
	public List<JeecgOrderTicket> selectTicketsByMainId(String mainId);
}
