package com.qooence.base.admin.modules.facility.service;

import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.modules.facility.entity.AlarmEntity;

import java.util.Map;

/**
 * 报警表
 *
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
public interface AlarmService extends IService<AlarmEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

