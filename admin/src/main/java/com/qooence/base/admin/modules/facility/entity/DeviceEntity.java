package com.qooence.base.admin.modules.facility.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备表
 * 
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
@TableName("device")
public class DeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 设备型号
	 */
	private String devType;
	/**
	 * 设备名
	 */
	private String name;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 设备的当前状态, “1”: 布防;  “2”: 撤防;  “3”: 在家布防;  “4”: SOS报警
	 */
	private String state;
	/**
	 * 设备内置发码器开关,“0”: 关闭    “1”: 打开
	 */
	private String switchState;
	/**
	 * mac地址
	 */
	private String mac;
	/**
	 * 本机ip地址
	 */
	private String hostId;
	/**
	 * 服务器ip
	 */
	private String serverIp;
	/**
	 * 服务器端口
	 */
	private Integer serverPort;
	/**
	 * 是否在线，0：离线，1：在线
	 */
	private String isOnline;
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
	 * 设置：设备名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：设备名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：版本号
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 获取：版本号
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置：设备的当前状态, “1”: 布防;  “2”: 撤防;  “3”: 在家布防;  “4”: SOS报警
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取：设备的当前状态, “1”: 布防;  “2”: 撤防;  “3”: 在家布防;  “4”: SOS报警
	 */
	public String getState() {
		return state;
	}
	/**
	 * 设置：设备内置发码器开关,“0”: 关闭    “1”: 打开
	 */
	public void setSwitchState(String switchState) {
		this.switchState = switchState;
	}
	/**
	 * 获取：设备内置发码器开关,“0”: 关闭    “1”: 打开
	 */
	public String getSwitchState() {
		return switchState;
	}
	/**
	 * 设置：mac地址
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * 获取：mac地址
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * 设置：本机ip地址
	 */
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	/**
	 * 获取：本机ip地址
	 */
	public String getHostId() {
		return hostId;
	}
	/**
	 * 设置：服务器ip
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	/**
	 * 获取：服务器ip
	 */
	public String getServerIp() {
		return serverIp;
	}
	/**
	 * 设置：服务器端口
	 */
	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}
	/**
	 * 获取：服务器端口
	 */
	public Integer getServerPort() {
		return serverPort;
	}
	/**
	 * 设置：是否在线，0：离线，1：在线
	 */
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	/**
	 * 获取：是否在线，0：离线，1：在线
	 */
	public String getIsOnline() {
		return isOnline;
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
