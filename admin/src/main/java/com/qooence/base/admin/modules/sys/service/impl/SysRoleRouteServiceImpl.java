package com.qooence.base.admin.modules.sys.service.impl;

import com.qooence.base.admin.modules.sys.dao.SysRoleRouteDao;
import com.qooence.base.admin.modules.sys.entity.SysRoleRouteEntity;
import com.qooence.base.admin.modules.sys.service.SysRoleRouteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;



@Service("sysRoleRouteService")
public class SysRoleRouteServiceImpl extends ServiceImpl<SysRoleRouteDao, SysRoleRouteEntity> implements SysRoleRouteService {


    @Override
    public void saveOrUpdate(Long roleId, List<Long> RouteIdList) {
        //先删除角色与路由关系
        deleteBatch(new Long[]{roleId});

        if(RouteIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        List<SysRoleRouteEntity> list = new ArrayList<>(RouteIdList.size());
        for(Long routeId : RouteIdList){
            SysRoleRouteEntity sysRoleRouteEntity = new SysRoleRouteEntity();
            sysRoleRouteEntity.setRouteId(routeId);
            sysRoleRouteEntity.setRoleId(roleId);

            list.add(sysRoleRouteEntity);
        }
        this.insertBatch(list);
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        return baseMapper.deleteBatch(roleIds);
    }

    @Override
    public int deleteBatchRoute(Long[] routeIds){
        return baseMapper.deleteBatchRoute(routeIds);
    }

    @Override
    public List<Long> queryRouteIdList(Long roleId) {
        return baseMapper.queryRouteIdList(roleId);
    }
}
