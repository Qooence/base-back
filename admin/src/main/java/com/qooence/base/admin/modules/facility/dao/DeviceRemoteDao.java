package com.qooence.base.admin.modules.facility.dao;

import com.qooence.base.admin.modules.facility.entity.DeviceRemoteEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 设备-遥控器表
 * 
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
@Mapper
@Repository
public interface DeviceRemoteDao extends BaseMapper<DeviceRemoteEntity> {
	
}
