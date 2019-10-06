package com.qooence.base.admin.modules.facility.service;

import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.modules.facility.entity.DeviceRemoteEntity;

import java.util.Map;

/**
 * 设备-遥控器表
 *
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
public interface DeviceRemoteService extends IService<DeviceRemoteEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

