package com.qooence.base.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * 字典表
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@TableName("sys_dict")
public class SysDictEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 字典标识
	 */
	private String code;
	/**
	 * 字典父标识
	 */
	private String pCode;
	/**
	 * 排序
	 */
	private Integer seq;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;

	@TableField(exist=false)
	private List<SysDictEntity> children;

	@TableField(exist=false)
	private String title;

	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：字典标识
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：字典标识
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：排序
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * 获取：排序
	 */
	public Integer getSeq() {
		return seq;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 获取：字典父标识
	 * @return
	 */
	public String getPCode() {
		return pCode;
	}

	/**
	 * 设置：字典父标识
	 * @param pCode
	 */
	public void setPCode(String pCode) {
		this.pCode = pCode;
	}

	public List<SysDictEntity> getChildren() {
		return children;
	}

	public void setChildren(List<SysDictEntity> children) {
		this.children = children;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
