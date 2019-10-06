package com.qooence.base.admin.modules.facility.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.Query;

import com.qooence.base.admin.modules.facility.dao.AlarmDao;
import com.qooence.base.admin.modules.facility.entity.AlarmEntity;
import com.qooence.base.admin.modules.facility.service.AlarmService;


@Service("alarmService")
public class AlarmServiceImpl extends ServiceImpl<AlarmDao, AlarmEntity> implements AlarmService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        List<String> timeList = (List)params.get("createTime");
        boolean f = null != timeList && timeList.size()> 0;
        String v1 = "";
        String v2 = "";
        if(f){
            v1 = timeList.get(0) + " 00:00:00";
            v2 = timeList.get(1) + " 23:59:59";
        }
        Page<AlarmEntity> page = this.selectPage(
                new Query<AlarmEntity>(params).getPage(),
                new EntityWrapper<AlarmEntity>()
                    .between(f ,"create_time",v1,v2)
                    .orderBy("create_time",false)
        );

        return new PageUtils(page);
    }

}
