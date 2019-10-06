package com.qooence.base.admin.modules.facility.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.Query;

import com.qooence.base.admin.modules.facility.dao.DeviceSensorDao;
import com.qooence.base.admin.modules.facility.entity.DeviceSensorEntity;
import com.qooence.base.admin.modules.facility.service.DeviceSensorService;


@Service("deviceSensorService")
public class DeviceSensorServiceImpl extends ServiceImpl<DeviceSensorDao, DeviceSensorEntity> implements DeviceSensorService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        Page<DeviceSensorEntity> page = this.selectPage(
                new Query<DeviceSensorEntity>(params).getPage(),
                new EntityWrapper<DeviceSensorEntity>()
                        .like(StringUtils.isNotBlank(name),"name",name)
        );

        return new PageUtils(page);
    }

}
