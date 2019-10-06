package com.qooence.base.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qooence.base.admin.common.utils.R;
import com.qooence.base.admin.modules.sys.dao.SysUserTokenDao;
import com.qooence.base.admin.modules.sys.entity.SysUserTokenEntity;
import com.qooence.base.admin.modules.sys.oauth2.TokenGenerator;
import com.qooence.base.admin.modules.sys.redis.UserTokenRedis;
import com.qooence.base.admin.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
	//12小时后过期
	private final static long EXPIRE = 3600 * 12;

	@Autowired
	private UserTokenRedis tokenRedis;

	@Override
	public R createToken(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		SysUserTokenEntity tokenEntity = this.selectById(userId);
		if(tokenEntity == null){
			tokenEntity = new SysUserTokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			this.insert(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			this.updateById(tokenEntity);
		}

		tokenRedis.save(tokenEntity,token,EXPIRE);

		R r = R.ok().put("token", token).put("expire", EXPIRE);

		return r;
	}

	@Override
	public void logout(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		// redis token过期后会自动删除 退出时不需要删除token
		//修改token
		SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
		tokenEntity.setUserId(userId);
		tokenEntity.setToken(token);
		this.updateById(tokenEntity);
	}
}
