package com.qooence.base.admin.service;

import com.qooence.base.admin.datasources.DataSourceNames;
import com.qooence.base.admin.datasources.annotation.DataSource;
import com.qooence.base.admin.modules.sys.entity.SysUserEntity;
import com.qooence.base.admin.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试多数据源
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Service
public class DataSourceTestService {
    @Autowired
    private SysUserService sysUserService;

    public SysUserEntity queryUser(Long userId){
        return sysUserService.selectById(userId);
    }

    @DataSource(name = DataSourceNames.SECOND)
    public SysUserEntity queryUser2(Long userId){
        return sysUserService.selectById(userId);
    }
}
