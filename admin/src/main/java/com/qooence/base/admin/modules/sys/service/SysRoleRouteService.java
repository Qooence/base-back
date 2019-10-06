package com.qooence.base.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.modules.sys.entity.SysRoleRouteEntity;

import java.util.List;

/**
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-11-22 17:51:40
 */
public interface SysRoleRouteService extends IService<SysRoleRouteEntity> {

    void saveOrUpdate(Long roleId, List<Long> RouteIdList);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

    /**
     * 根据路由ID数组，批量删除
     */
    int deleteBatchRoute(Long[] routeIds);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryRouteIdList(Long roleId);
}

