package com.qooence.base.admin.modules.facility.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.Query;

import com.qooence.base.admin.modules.facility.dao.DeviceDao;
import com.qooence.base.admin.modules.facility.entity.DeviceEntity;
import com.qooence.base.admin.modules.facility.service.DeviceService;


@Service("deviceService")
public class DeviceServiceImpl extends ServiceImpl<DeviceDao, DeviceEntity> implements DeviceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        Page<DeviceEntity> page = this.selectPage(
                new Query<DeviceEntity>(params).getPage(),
                new EntityWrapper<DeviceEntity>()
                    .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }

}
