package com.qooence.base.admin.modules.sys.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qooence.base.admin.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
