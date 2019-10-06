package com.qooence.base.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-11-22 17:51:40
 */
@TableName("sys_role_route")
public class SysRoleRouteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 角色id
	 */
	private Long roleId;
	/**
	 * 路由id
	 */
	private Long routeId;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：路由id
	 */
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	/**
	 * 获取：路由id
	 */
	public Long getRouteId() {
		return routeId;
	}

	/**
	 * 获取：角色id
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * 设置：角色id
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
