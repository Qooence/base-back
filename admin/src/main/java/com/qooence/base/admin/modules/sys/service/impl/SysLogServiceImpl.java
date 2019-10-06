package com.qooence.base.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.Query;
import com.qooence.base.admin.modules.sys.entity.SysLogEntity;
import com.qooence.base.admin.modules.sys.service.SysLogService;
import com.qooence.base.admin.modules.sys.dao.SysLogDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");
        List<String> timeList = (List)params.get("createDate");
        boolean f = null != timeList && timeList.size()> 0;
        String v1 = "";
        String v2 = "";
        if(f){
            v1 = timeList.get(0) + " 00:00:00";
            v2 = timeList.get(1) + " 23:59:59";
        }
        Page<SysLogEntity> page = this.selectPage(
            new Query<SysLogEntity>(params).getPage(),
            new EntityWrapper<SysLogEntity>()
                    .and(StringUtils.isNotBlank(key),"username like {0} or operation like {0}", "%" + key + "%")
                    .between(f ,"create_date",v1,v2)
                    .orderBy("create_date",false)
        );

        return new PageUtils(page);
    }
}
