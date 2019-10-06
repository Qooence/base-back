package com.qooence.base.admin.modules.facility.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.Query;

import com.qooence.base.admin.modules.facility.dao.DeviceZoneDao;
import com.qooence.base.admin.modules.facility.entity.DeviceZoneEntity;
import com.qooence.base.admin.modules.facility.service.DeviceZoneService;


@Service("deviceZoneService")
public class DeviceZoneServiceImpl extends ServiceImpl<DeviceZoneDao, DeviceZoneEntity> implements DeviceZoneService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        Page<DeviceZoneEntity> page = this.selectPage(
                new Query<DeviceZoneEntity>(params).getPage(),
                new EntityWrapper<DeviceZoneEntity>()
                        .like(StringUtils.isNotBlank(name),"name",name)
        );

        return new PageUtils(page);
    }

}
