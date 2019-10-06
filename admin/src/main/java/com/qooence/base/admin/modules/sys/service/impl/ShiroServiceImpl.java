package com.qooence.base.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qooence.base.admin.common.utils.Constant;
import com.qooence.base.admin.modules.sys.dao.SysRouteDao;
import com.qooence.base.admin.modules.sys.dao.SysUserDao;
import com.qooence.base.admin.modules.sys.dao.SysUserTokenDao;
import com.qooence.base.admin.modules.sys.entity.SysRouteEntity;
import com.qooence.base.admin.modules.sys.entity.SysUserEntity;
import com.qooence.base.admin.modules.sys.entity.SysUserTokenEntity;
import com.qooence.base.admin.modules.sys.redis.UserPermissionsRedis;
import com.qooence.base.admin.modules.sys.service.ShiroService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Autowired
    private SysRouteDao sysRouteDao;

    @Autowired
    private UserPermissionsRedis userPermissionsRedis;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;
        // 获取缓存中的数据
        permsList = userPermissionsRedis.get(Long.toString(userId));
        if(permsList == null){
            //系统管理员，拥有最高权限
            if(userId == Constant.SUPER_ADMIN){
                List<SysRouteEntity>  routePerms = sysRouteDao.selectList(new EntityWrapper<SysRouteEntity>());
                permsList = new ArrayList<>(routePerms.size());
                for(SysRouteEntity route : routePerms){
                    permsList.add(route.getPerms());
                }
            }else{
                permsList = sysUserDao.queryAllPerms(userId);
            }
            // 保存到缓存中
            userPermissionsRedis.save(permsList,Long.toString(userId));
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}
