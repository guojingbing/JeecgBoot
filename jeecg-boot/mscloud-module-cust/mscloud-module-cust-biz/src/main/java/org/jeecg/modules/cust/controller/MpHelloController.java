package org.jeecg.modules.cust.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.modules.cust.service.IMpHelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "cust示例")
@RestController
@RequestMapping("/cust")
@Slf4j
public class MpHelloController {
	@Resource
	private ISysBaseAPI sysBaseAPI;
	@Resource
	private IMpHelloService jeecgHelloService;

	@ApiOperation(value = "hello", notes = "对外服务接口")
	@GetMapping(value = "/api/comm/hello")
	public String sayHello() {
//		sysBaseAPI.getAllSysDepart();
		log.info(" ---我被调用了--- ");
		return jeecgHelloService.hello();
	}
}
