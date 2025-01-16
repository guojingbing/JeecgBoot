package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.TreeUtils;
import org.jeecg.modules.system.entity.SysTenantArea;
import org.jeecg.modules.system.service.ISysTenantAreaService;
import org.jeecg.modules.system.service.impl.SysBaseApiImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 行政区域代码 前端控制器
 * </p>
 * @Author scott
 * @since 2018-12-19
 */
@RestController
@RequestMapping("/sys/area")
@Api(tags="行政区域管理")
@Slf4j
public class SysCommAreaController {
	@Autowired
	private SysBaseApiImpl sysBaseApi;
	@Autowired
	private ISysTenantAreaService sysTenantAreaService;

	/**
	 * 查询区域树
	 * @return
	 */
	@ApiOperation("获取区域树")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public Result<Map> queryAreaTree(@RequestParam(name="deep",required=false) Integer deep,@RequestParam(name="tenantId",required=false) String tenantId,@RequestParam(name="onlyAuthorized",required=false) Boolean onlyAuthorized,HttpServletRequest request) {
		List<Map<String, Object>> areas=sysBaseApi.queryAreaTreeWithDeep(deep,tenantId,onlyAuthorized);

		for(Map<String,Object> map : areas){
			map.put("key",map.get("area_code"));
			map.put("title",map.get("area_name"));
			map.remove("area_code");
			map.remove("area_name");
		}

		List<Map<String, Object>> areaTree=TreeUtils.buildTreeGridMap(areas,"key","pcode");
		Result<Map> result = new Result<>();
		Map data = new HashMap();
		data.put("areaTree",areaTree);
		if(StringUtils.isNotBlank(tenantId)){
			List<Map<String, Object>> tenantAreas=sysBaseApi.queryAreaTreeWithDeep(deep,tenantId,true);
			data.put("tenantAreas",tenantAreas);
		}
		result.setResult(data);
		result.setSuccess(true);
		return result;
	}

	@ApiOperation(value = "设置租户区域权限", notes = "设置租户区域权限")
	@PostMapping(value = "/auth/tenant")
	public Result<String> setTenantArea(@RequestBody JSONObject body) {
		Integer tenantId=body.getInteger("tenantId");
		JSONArray areaArray=body.getJSONArray("areas");

		List<SysTenantArea> tenantAreas=null;
		if(areaArray!=null&&areaArray.size()>0){
			tenantAreas=new ArrayList<>();
			for(Object obj:areaArray){
				SysTenantArea tarea=new SysTenantArea();
				if(obj!=null){
					tarea.setAreaCode((Long)obj);
					tarea.setTenantId(tenantId);
					tenantAreas.add(tarea);
				}
			}
		}
		tenantAreas=sysTenantAreaService.setTenantAreas(tenantId,tenantAreas);
		return Result.OK("租户区域权限设置成功");
	}
}
