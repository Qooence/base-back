package com.qooence.base.admin.modules.facility.service;

import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.modules.facility.entity.DeviceSensorEntity;

import java.util.Map;

/**
 * 设备配件表
 *
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
public interface DeviceSensorService extends IService<DeviceSensorEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

