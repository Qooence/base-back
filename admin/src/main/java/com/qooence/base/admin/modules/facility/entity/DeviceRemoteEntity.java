package com.qooence.base.admin.modules.facility.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备-遥控器表
 * 
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
@TableName("device_remote")
public class DeviceRemoteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 设备id
	 */
	private String devId;
	/**
	 * 设备型号
	 */
	private String devType;
	/**
	 * 遥控器编号
	 */
	private String number;
	/**
	 * 遥控器名
	 */
	private String name;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：设备id
	 */
	public void setDevId(String devId) {
		this.devId = devId;
	}
	/**
	 * 获取：设备id
	 */
	public String getDevId() {
		return devId;
	}
	/**
	 * 设置：设备型号
	 */
	public void setDevType(String devType) {
		this.devType = devType;
	}
	/**
	 * 获取：设备型号
	 */
	public String getDevType() {
		return devType;
	}
	/**
	 * 设置：遥控器编号
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 获取：遥控器编号
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 设置：遥控器名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：遥控器名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
