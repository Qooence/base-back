package com.qooence.base.admin.modules.sys.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Sets;
import com.qooence.base.admin.common.utils.Constant;
import com.qooence.base.admin.modules.sys.dao.SysRoleDao;
import com.qooence.base.admin.modules.sys.dao.SysRoleRouteDao;
import com.qooence.base.admin.modules.sys.dao.SysRouteDao;
import com.qooence.base.admin.modules.sys.dao.SysUserDao;
import com.qooence.base.admin.modules.sys.entity.SysRoleEntity;
import com.qooence.base.admin.modules.sys.entity.SysRouteEntity;
import com.qooence.base.admin.modules.sys.redis.UserPermissionsRedis;
import com.qooence.base.admin.modules.sys.redis.UserRouteRedis;
import com.qooence.base.admin.modules.sys.service.SysRouteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;


@Service("sysRouteService")
public class SysRouteServiceImpl extends ServiceImpl<SysRouteDao, SysRouteEntity> implements SysRouteService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleRouteDao sysRoleRouteDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private UserRouteRedis routeRedis;

    @Autowired
    private UserPermissionsRedis permissionsRedis;
    public List<SysRouteEntity> getUserRouteList(Long userId) {
        List<SysRouteEntity> topList;
        List<SysRouteEntity> routeList;
        List<SysRouteEntity> treeList;
        // 获取缓存中的数据
        treeList = routeRedis.get(Long.toString(userId));
        if(treeList == null){
            //系统管理员，拥有最高权限
            if(userId == Constant.SUPER_ADMIN){
                topList = this.selectList(new EntityWrapper<SysRouteEntity>().eq("pid",0).orderBy("order_num"));
                routeList = this.selectList(new EntityWrapper<SysRouteEntity>().ne("pid",0).ne("type",2).orderBy("order_num"));
            }else{
                //用户路由列表
                List<Long> routeIdList = sysUserDao.queryAllRouteId(userId);
                topList = this.selectList(new EntityWrapper<SysRouteEntity>().in("id",routeIdList).eq("pid",0).orderBy("order_num"));
                routeList = this.selectList(new EntityWrapper<SysRouteEntity>().in("id",routeIdList).ne("pid",0).ne("type",2).orderBy("order_num"));
            }
            treeList = tree(routeList,topList,null);
            // 保存到缓存中
            routeRedis.save(treeList,Long.toString(userId));
        }
        return treeList;
    }

    @Override
    public List<SysRouteEntity> getUserRouteTree(List<Long> checkes) {
        List<SysRouteEntity> topList = this.selectList(new EntityWrapper<SysRouteEntity>().eq("pid",0).orderBy("order_num"));
        List<SysRouteEntity> routeList = this.selectList(new EntityWrapper<SysRouteEntity>().ne("pid",0).orderBy("order_num"));
        return tree(routeList,topList,checkes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRouteEntity route){
        this.insert(route);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRouteEntity route){
        this.updateAllColumnById(route);
        // 删除缓存
        routeRedis.deleteAll();
        permissionsRedis.deleteAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] routeIds){

        this.deleteBatchIds(Arrays.asList(routeIds));

        //查询拥有这些路由的角色
        List<SysRoleEntity> roleList = sysRoleDao.queryRoleByRouteIds(routeIds);

        //修改角色中的checkes(分配权限树勾选的节点)
        if(roleList.size() > 0){
            for (Long routeId : routeIds) {
                roleList.forEach(role -> {
                    List<Long> checkes = Arrays.asList(role.getCheckes().split(","))
                                            .stream()
                                            .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                    checkes.remove(routeId);
                    role.setCheckes(StringUtils.join(checkes, ","));
                    sysRoleDao.updateById(role);
                });
            }
        }

        //删除角色与路由关联
        sysRoleRouteDao.deleteBatchRoute(routeIds);

        // 删除缓存
        routeRedis.deleteAll();
        permissionsRedis.deleteAll();
    }

    /**
     * 装配成树结构
     * @param routeList 非顶级类目集合
     * @param topList 顶级类目集合-结果集
     * @return
     */
    private List<SysRouteEntity> tree(List<SysRouteEntity> routeList,List<SysRouteEntity> topList,List<Long> checkes){
        if(ObjectUtil.isNotNull(routeList)){
            //过滤条件set，
            Set<Long> set = Sets.newHashSetWithExpectedSize(routeList.size());
            topList.forEach(route -> getChild(route,routeList,set,checkes));
            return topList;
        }
        return null;
    }

    /**
     * 递归获取子目录
     * @param route 上级
     * @param routeList 所有数据集合
     * @param set 已循坏对象ID集合
     */
    private void getChild(SysRouteEntity route,List<SysRouteEntity> routeList,Set<Long> set,List<Long> checkes){
        List<SysRouteEntity> childList = new ArrayList<>();
        routeList.stream()
                //判断是否已循坏过当前对象
                .filter(r -> !set.contains(r.getId()))
                //判断是否父子关系
                .filter(r -> NumberUtil.compare(r.getPid(),route.getId())== 0)
                .filter(r -> set.size() <= routeList.size())
                .forEach(r -> {
                    //放入set,递归循环时可以跳过这个子目录，提高循环效率
                    set.add(r.getId());
                    //获取当前类目的子类目
                    getChild(r,routeList,set,checkes);
                    // 设置已勾选的节点
                    if(null != checkes){
                        checkes.forEach(item -> {
                            if(item == r.getId()){
                                r.setChecked(true);
                            }
                        });
                    }
                    childList.add(r);
                });
        route.setChildren(childList);
    }

}
