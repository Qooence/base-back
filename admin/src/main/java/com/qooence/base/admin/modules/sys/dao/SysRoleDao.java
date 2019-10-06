package com.qooence.base.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qooence.base.admin.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Mapper
@Repository
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);

	/**
	 * 查询用户对应的角色、角色名字符串
	 * @param userId
	 * @return
	 */
	Map<String,String> queryRoleNameByUserId(Long userId);

	/**
	 * 查询拥有这些路由的角色
	 * @param routeIds 路由id列表
	 * @return
	 */
	List<SysRoleEntity> queryRoleByRouteIds(Long[] routeIds);
}
