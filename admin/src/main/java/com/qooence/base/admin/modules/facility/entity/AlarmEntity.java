package com.qooence.base.admin.modules.facility.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 报警表
 * 
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
@TableName("alarm")
public class AlarmEntity implements Serializable {
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
	 * 报警信息类型,“1”: 防拆报警;2”: 主机紧急报警;“3”: 有线防区报警;“4”: 遥控器报警 ；5: 无线防区报警;6”: 配件低压;“7”: 遥控器低压
	 */
	private String alarmType;
	/**
	 * 配件编号
	 */
	private String sensorNum;
	/**
	 * 防区编号
	 */
	private String zoneNum;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
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
	 * 设置：报警信息类型,“1”: 防拆报警;2”: 主机紧急报警;“3”: 有线防区报警;“4”: 遥控器报警 ；5: 无线防区报警;6”: 配件低压;“7”: 遥控器低压
	 */
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	/**
	 * 获取：报警信息类型,“1”: 防拆报警;2”: 主机紧急报警;“3”: 有线防区报警;“4”: 遥控器报警 ；5: 无线防区报警;6”: 配件低压;“7”: 遥控器低压
	 */
	public String getAlarmType() {
		return alarmType;
	}
	/**
	 * 设置：配件编号
	 */
	public void setSensorNum(String sensorNum) {
		this.sensorNum = sensorNum;
	}
	/**
	 * 获取：配件编号
	 */
	public String getSensorNum() {
		return sensorNum;
	}
	/**
	 * 设置：防区编号
	 */
	public void setZoneNum(String zoneNum) {
		this.zoneNum = zoneNum;
	}
	/**
	 * 获取：防区编号
	 */
	public String getZoneNum() {
		return zoneNum;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
