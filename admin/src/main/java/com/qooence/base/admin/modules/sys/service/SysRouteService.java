package com.qooence.base.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.modules.sys.entity.SysRouteEntity;

import java.util.List;

/**
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-11-22 17:51:40
 */
public interface SysRouteService extends IService<SysRouteEntity> {

    /**
     * 获取用户路由列表
     */
    List<SysRouteEntity> getUserRouteList(Long userId);

    /**
     * 获取路由树结构数据
     * @param checkes 已勾选路由ID列表
     * @return
     */
    List<SysRouteEntity> getUserRouteTree(List<Long> checkes);

    /**
     * 新增
     * @param route
     */
    void save(SysRouteEntity route);

    /**
     * 更新
     * @param route
     */
    void update(SysRouteEntity route);

    /**
     * 删除
     * @param routeIds
     */
    void deleteBatch(Long[] routeIds);

}

