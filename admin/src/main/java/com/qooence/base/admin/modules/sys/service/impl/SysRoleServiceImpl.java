package com.qooence.base.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qooence.base.admin.common.utils.Constant;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.modules.sys.redis.UserPermissionsRedis;
import com.qooence.base.admin.modules.sys.redis.UserRouteRedis;
import com.qooence.base.admin.modules.sys.service.*;
import com.qooence.base.admin.common.utils.Query;
import com.qooence.base.admin.modules.sys.dao.SysRoleDao;
import com.qooence.base.admin.modules.sys.entity.SysRoleEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
	@Autowired
	private SysRoleRouteService sysRoleRouteService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private UserRouteRedis routeRedis;

    @Autowired
    private UserPermissionsRedis permissionsRedis;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String roleName = (String)params.get("roleName");

		Page<SysRoleEntity> page = this.selectPage(
			new Query<SysRoleEntity>(params).getPage(),
			new EntityWrapper<SysRoleEntity>()
				.like(StringUtils.isNotBlank(roleName),"role_name", roleName)
				.ne("role_id",Constant.SUPER_ADMIN)
		);

		return new PageUtils(page);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleEntity role) {
        role.setCreateTime(new Date());
        this.insert(role);

		//保存角色与路由关系
		sysRoleRouteService.saveOrUpdate(role.getRoleId(),role.getMenuIdList());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {
        this.updateById(role);

		//保存角色与路由关系
		sysRoleRouteService.saveOrUpdate(role.getRoleId(),role.getMenuIdList());

        // 删除缓存
        routeRedis.deleteAll();
        permissionsRedis.deleteAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.deleteBatchIds(Arrays.asList(roleIds));

        //删除角色与路由关联
        sysRoleRouteService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);

        // 删除缓存
        routeRedis.deleteAll();
        permissionsRedis.deleteAll();
    }


    @Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return baseMapper.queryRoleIdList(createUserId);
	}

}
