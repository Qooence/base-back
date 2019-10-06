package com.qooence.base.admin.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 * @author jyq
 * @email qooence@qooence.uu.me
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
