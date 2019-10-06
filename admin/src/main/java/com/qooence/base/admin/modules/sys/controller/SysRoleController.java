package com.qooence.base.admin.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qooence.base.admin.common.utils.Constant;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.annotation.SysLog;
import com.qooence.base.admin.common.utils.R;
import com.qooence.base.admin.common.validator.ValidatorUtils;
import com.qooence.base.admin.modules.sys.entity.SysRoleEntity;
import com.qooence.base.admin.modules.sys.entity.SysRouteEntity;
import com.qooence.base.admin.modules.sys.service.SysRoleService;
import com.qooence.base.admin.modules.sys.service.SysRouteService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色管理
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysRouteService sysRouteService;

	/**
	 * 角色列表
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestBody Map<String, Object> params){
		//如果不是超级管理员，则只查询自己创建的角色列表
//		if(getUserId() != Constant.SUPER_ADMIN){
//			params.put("createUserId", getUserId());
//		}

		PageUtils page = sysRoleService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(){
		Map<String, Object> map = new HashMap<>();
		
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			map.put("createUserId", getUserId());
		}
		List<SysRoleEntity> list = sysRoleService.selectByMap(map);
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.selectById(roleId);

		List<Long> checkes = new ArrayList<Long>();

		if(StringUtils.isNotBlank(role.getCheckes())){
			checkes = Arrays.asList(role.getCheckes().split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
		}

		List<SysRouteEntity> tree = sysRouteService.getUserRouteTree(checkes);

		return R.ok().put("role", role).put("tree",tree);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.save(role);
		
		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.update(role);
		
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return R.ok();
	}

	/**
	 * 角色列表(给下拉框使用)
	 */
	@GetMapping("/listForSelect")
	public R listForSelect(){
		List<SysRoleEntity> list = sysRoleService.selectList(new EntityWrapper<SysRoleEntity>().ne("role_id",Constant.SUPER_ADMIN));
		return R.ok().put("list", list);
	}
}
