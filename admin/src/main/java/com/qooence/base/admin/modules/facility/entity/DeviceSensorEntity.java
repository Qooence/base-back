package com.qooence.base.admin.modules.facility.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备配件表
 * 
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
@TableName("device_sensor")
public class DeviceSensorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 设备型号
	 */
	private String devType;
	/**
	 * 防区id
	 */
	private String zoneId;
	/**
	 * 防区编号
	 */
	private String zoneNumber;
	/**
	 * 配件编号
	 */
	private String number;
	/**
	 * 配件名称
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
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
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
	 * 设置：防区id
	 */
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	/**
	 * 获取：防区id
	 */
	public String getZoneId() {
		return zoneId;
	}
	/**
	 * 设置：防区编号
	 */
	public void setZoneNumber(String zoneNumber) {
		this.zoneNumber = zoneNumber;
	}
	/**
	 * 获取：防区编号
	 */
	public String getZoneNumber() {
		return zoneNumber;
	}
	/**
	 * 设置：配件编号
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 获取：配件编号
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 设置：配件名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：配件名称
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
