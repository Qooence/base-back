package com.qooence.base.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qooence.base.admin.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Mapper
@Repository
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllRouteId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	/**
	 * 根据条件查询用户列表
	 * @param params
	 * @return
	 */
	List<SysUserEntity> queryListByParam(Page<SysUserEntity> page,Map<String, Object> params);

}
