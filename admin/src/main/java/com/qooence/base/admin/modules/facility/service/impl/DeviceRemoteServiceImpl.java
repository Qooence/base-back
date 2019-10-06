package com.qooence.base.admin.modules.facility.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.Query;

import com.qooence.base.admin.modules.facility.dao.DeviceRemoteDao;
import com.qooence.base.admin.modules.facility.entity.DeviceRemoteEntity;
import com.qooence.base.admin.modules.facility.service.DeviceRemoteService;


@Service("deviceRemoteService")
public class DeviceRemoteServiceImpl extends ServiceImpl<DeviceRemoteDao, DeviceRemoteEntity> implements DeviceRemoteService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        Page<DeviceRemoteEntity> page = this.selectPage(
                new Query<DeviceRemoteEntity>(params).getPage(),
                new EntityWrapper<DeviceRemoteEntity>()
                    .like(StringUtils.isNotBlank(name),"name",name)
        );

        return new PageUtils(page);
    }

}
