package com.qooence.base.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-11-22 17:51:40
 */
@TableName("sys_route")
public class SysRouteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id
	 */
	@TableId
	private Long id;
	/**
	 * 父id
	 */
	private Long pid;
	/**
	 * 路由路径
	 */
	private String path;
	/**
	 * 路由名
	 */
	private String name;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	private Integer type;
	/**
	 * 组件路径
	 */
	private String componentPath;
	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	private String perms;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 隐藏路由
	 */
	private Integer hideInMenu;
	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean expand = true;

	@TableField(exist=false)
	private List<?> children;

	@TableField(exist=false)
	private Boolean checked;


	/**
	 * 设置：Id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：Id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：父id
	 */
	public void setPid(Long pid) {
		this.pid = pid;
	}
	/**
	 * 获取：父id
	 */
	public Long getPid() {
		return pid;
	}
	/**
	 * 设置：路由路径
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取：路由路径
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置：路由名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：路由名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：类型     0：目录   1：菜单   2：按钮
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型     0：目录   1：菜单   2：按钮
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：组件路径
	 */
	public void setComponentPath(String componentPath) {
		this.componentPath = componentPath;
	}
	/**
	 * 获取：组件路径
	 */
	public String getComponentPath() {
		return componentPath;
	}
	/**
	 * 设置：授权(多个用逗号分隔，如：user:list,user:create)
	 */
	public void setPerms(String perms) {
		this.perms = perms;
	}
	/**
	 * 获取：授权(多个用逗号分隔，如：user:list,user:create)
	 */
	public String getPerms() {
		return perms;
	}
	/**
	 * 设置：图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取：图标
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置：隐藏路由
	 */
	public void setHideInMenu(Integer hideInMenu) {
		this.hideInMenu = hideInMenu;
	}
	/**
	 * 获取：隐藏路由
	 */
	public Integer getHideInMenu() {
		return hideInMenu;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	public List<?> getChildren() {
		return children;
	}

	public void setChildren(List<?> children) {
		this.children = children;
	}

	public Boolean getExpand() {
		return expand;
	}

	public void setExpand(Boolean expand) {
		this.expand = expand;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
