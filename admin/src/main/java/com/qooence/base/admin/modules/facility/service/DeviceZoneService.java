package com.qooence.base.admin.modules.facility.service;

import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.modules.facility.entity.DeviceZoneEntity;

import java.util.Map;

/**
 * 设备防区表
 *
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
public interface DeviceZoneService extends IService<DeviceZoneEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

