package com.qooence.base.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qooence.base.admin.modules.sys.entity.SysRoleRouteEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-11-22 17:51:40
 */
@Mapper
@Repository
public interface SysRoleRouteDao extends BaseMapper<SysRoleRouteEntity> {
    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

    /**
     * 根据路由ID数组，批量删除
     */
    int deleteBatchRoute(Long[] routeIds);

    /**
     * 根据角色ID，获取路由ID列表
     */
    List<Long> queryRouteIdList(Long roleId);
}
