package com.qooence.base.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.modules.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 * @author jyq
 * @email qooence@qooence.uu.me
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void save(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
