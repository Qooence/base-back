package com.qooence.base.admin.modules.facility.service;

import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.modules.facility.entity.DeviceEntity;

import java.util.Map;

/**
 * 设备表
 *
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
public interface DeviceService extends IService<DeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

