package org.jeecg.modules.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.jeecg.common.util.IpUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.system.entity.*;
import org.jeecg.modules.system.mapper.SysRoleMapper;
import org.jeecg.modules.system.mapper.SysRolePermissionMapper;
import org.jeecg.modules.system.mapper.SysTenantMapper;
import org.jeecg.modules.system.service.ISysPermissionService;
import org.jeecg.modules.system.service.ISysRolePermissionService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {
	@Autowired
	private ISysPermissionService sysPermissionService;
	@Autowired
	private SysTenantMapper sysTenantMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRolePermissionMapper sysRolePermissionMapper;

	@Override
	public void saveRolePermission(String roleId, String permissionIds) {
		String ip = "";
		try {
			//获取request
			HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
			//获取IP地址
			ip = IpUtils.getIpAddr(request);
		} catch (Exception e) {
			ip = "127.0.0.1";
		}
		LambdaQueryWrapper<SysRolePermission> query = new QueryWrapper<SysRolePermission>().lambda().eq(SysRolePermission::getRoleId, roleId);
		this.remove(query);
		List<SysRolePermission> list = new ArrayList<SysRolePermission>();
        String[] arr = permissionIds.split(",");
		for (String p : arr) {
			if(oConvertUtils.isNotEmpty(p)) {
				SysRolePermission rolepms = new SysRolePermission(roleId, p);
				rolepms.setOperateDate(new Date());
				rolepms.setOperateIp(ip);
				list.add(rolepms);
			}
		}
		this.saveBatch(list);
	}

	@Override
	public void saveRolePermission(String roleId, String permissionIds, String lastPermissionIds) {
		String ip = "";
		try {
			//获取request
			HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
			//获取IP地址
			ip = IpUtils.getIpAddr(request);
		} catch (Exception e) {
			ip = "127.0.0.1";
		}
		List<String> add = getDiff(lastPermissionIds,permissionIds);
		if(add!=null && add.size()>0) {
			List<SysRolePermission> list = new ArrayList<SysRolePermission>();
			for (String p : add) {
				if(oConvertUtils.isNotEmpty(p)) {
					SysRolePermission rolepms = new SysRolePermission(roleId, p);
					rolepms.setOperateDate(new Date());
					rolepms.setOperateIp(ip);
					list.add(rolepms);
				}
			}
			this.saveBatch(list);
		}
		
		List<String> delete = getDiff(permissionIds,lastPermissionIds);
		if(delete!=null && delete.size()>0) {
			for (String permissionId : delete) {
				this.remove(new QueryWrapper<SysRolePermission>().lambda().eq(SysRolePermission::getRoleId, roleId).eq(SysRolePermission::getPermissionId, permissionId));
			}
		}
	}

	@Override
	public void updateRolePermissionFromTenantPack(String roleId, Integer tenantId){
//		List<String> lastPermissionIdList=null;
	    if(StringUtils.isBlank(roleId)){
	    	if(tenantId==null){
	    		return;
			}
			//查询租户管理员角色
			SysTenant sysTenant = sysTenantMapper.querySysTenant(tenantId);
			if(sysTenant==null){
				return;
			}
			//查询租户管理员角色
			SysRole role=sysRoleMapper.getRoleNoTenant(sysTenant.getHouseNumber()+"-Admin");
			if(role==null){
				return;
			}
			roleId=role.getId();
//			LambdaQueryWrapper<SysRolePermission> query = new QueryWrapper<SysRolePermission>().lambda().eq(SysRolePermission::getRoleId, roleId);
//			List<SysRolePermission> rolePermissions=sysRolePermissionMapper.selectList(query);
//			lastPermissionIdList = rolePermissions.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        }
		//删除角色原有权限
		this.remove(new QueryWrapper<SysRolePermission>().lambda().eq(SysRolePermission::getRoleId, roleId));
		//查询租户产品包权限
		List<SysPermission> permissions=sysPermissionService.queryTenantPermissions(tenantId);
		if(!CollectionUtils.isEmpty(permissions)){
			List<String> permissionIdList = permissions.stream().map(SysPermission::getId).collect(Collectors.toList());
			//保存角色权限
			saveRolePermission(roleId, String.join(",",permissionIdList), null);
		}
	}
	
	/**
	 * 从diff中找出main中没有的元素
	 * @param main
	 * @param diff
	 * @return
	 */
	private List<String> getDiff(String main,String diff){
		if(oConvertUtils.isEmpty(diff)) {
			return null;
		}
		if(oConvertUtils.isEmpty(main)) {
			return Arrays.asList(diff.split(","));
		}
		
		String[] mainArr = main.split(",");
		String[] diffArr = diff.split(",");
		Map<String, Integer> map = new HashMap(5);
		for (String string : mainArr) {
			map.put(string, 1);
		}
		List<String> res = new ArrayList<String>();
		for (String key : diffArr) {
			if(oConvertUtils.isNotEmpty(key) && !map.containsKey(key)) {
				res.add(key);
			}
		}
		return res;
	}

}
