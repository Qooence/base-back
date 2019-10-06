package com.qooence.base.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.modules.sys.dao.SysRoleDao;
import com.qooence.base.admin.modules.sys.entity.SysUserEntity;
import com.qooence.base.admin.modules.sys.redis.UserPermissionsRedis;
import com.qooence.base.admin.modules.sys.redis.UserRedis;
import com.qooence.base.admin.modules.sys.redis.UserRouteRedis;
import com.qooence.base.admin.modules.sys.service.SysUserService;
import com.qooence.base.admin.common.utils.Query;
import com.qooence.base.admin.modules.sys.dao.SysUserDao;
import com.qooence.base.admin.modules.sys.service.SysUserRoleService;
import org.apache.commons.lang.RandomStringUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private UserRouteRedis routeRedis;

	@Autowired
	private UserPermissionsRedis permissionsRedis;

	@Autowired
	private UserRedis userRedis;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<SysUserEntity> page = new Query<SysUserEntity>(params).getPage();
		List<SysUserEntity> list = baseMapper.queryListByParam(page,params);
		for (SysUserEntity user: list) {
			Map<String,String> rolemap = sysRoleDao.queryRoleNameByUserId(user.getUserId());
			if(null != rolemap && StringUtils.isNotBlank(rolemap.get("roleIds"))){
				List<Long> roleIds = Arrays.asList(rolemap.get("roleIds").split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
				user.setRoleIdList(roleIds);
			}
			if(null != rolemap && StringUtils.isNotBlank(rolemap.get("roleNames"))){
				user.setRoleNameList(Arrays.asList(rolemap.get("roleNames").trim().split(",")));
			}
		}
		page.setRecords(list);
		return new PageUtils(page);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return baseMapper.queryByUserName(username);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		this.insert(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		user.setUpdateTime(new Date());
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			SysUserEntity u = baseMapper.selectById(user.getUserId());
			if(!u.getPassword().equals(user.getPassword())){
				user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
			}
		}
		this.updateById(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

		// 删除缓存
		routeRedis.deleteAll();
		permissionsRedis.deleteAll();
		userRedis.delete(Long.toString(user.getUserId()));
	}

	@Override
	public void deleteBatch(Long[] userIds) {
		this.deleteBatchIds(Arrays.asList(userIds));

		for(Long userId : userIds){
			//保存用户与角色关系
			sysUserRoleService.saveOrUpdate(userId, null);
			// 删除缓存中的user
			userRedis.delete(Long.toString(userId));
		}

		// 删除缓存
		routeRedis.deleteAll();
		permissionsRedis.deleteAll();
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
	}
}
