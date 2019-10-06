package com.qooence.base.admin.modules.facility.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备防区表
 * 
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
@TableName("device_zone")
public class DeviceZoneEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
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
	 * 防区编号
	 */
	private String number;
	/**
	 * 防区名字
	 */
	private String name;
	/**
	 * 防区类型,“1”: 普通防区;  “2”: 延时报警防区; “3”: 24小时防区; “4”: 关闭防区;
	 */
	private Integer zoneType;
	/**
	 * 报警方式, “0”: 无声报警   “1”: 有声报警
	 */
	private Integer armType;
	/**
	 * 在家布防时的报警状态设置,“0”: 不报警      “1”: 报警
	 */
	private Integer homeType;
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
	 * 设置：防区编号
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 获取：防区编号
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 设置：防区名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：防区名字
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：防区类型,“1”: 普通防区;  “2”: 延时报警防区; “3”: 24小时防区; “4”: 关闭防区;
	 */
	public void setZoneType(Integer zoneType) {
		this.zoneType = zoneType;
	}
	/**
	 * 获取：防区类型,“1”: 普通防区;  “2”: 延时报警防区; “3”: 24小时防区; “4”: 关闭防区;
	 */
	public Integer getZoneType() {
		return zoneType;
	}
	/**
	 * 设置：报警方式, “0”: 无声报警   “1”: 有声报警
	 */
	public void setArmType(Integer armType) {
		this.armType = armType;
	}
	/**
	 * 获取：报警方式, “0”: 无声报警   “1”: 有声报警
	 */
	public Integer getArmType() {
		return armType;
	}
	/**
	 * 设置：在家布防时的报警状态设置,“0”: 不报警      “1”: 报警
	 */
	public void setHomeType(Integer homeType) {
		this.homeType = homeType;
	}
	/**
	 * 获取：在家布防时的报警状态设置,“0”: 不报警      “1”: 报警
	 */
	public Integer getHomeType() {
		return homeType;
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
