package com.qooence.base.admin.modules.app.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qooence.base.admin.common.exception.RRException;
import com.qooence.base.admin.modules.app.dao.UserDao;
import com.qooence.base.admin.modules.app.entity.UserEntity;
import com.qooence.base.admin.modules.app.service.UserService;
import com.qooence.base.admin.common.validator.Assert;
import com.qooence.base.admin.modules.app.form.LoginForm;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	@Override
	public UserEntity queryByMobile(String mobile) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(mobile);
		return baseMapper.selectOne(userEntity);
	}

	@Override
	public long login(LoginForm form) {
		UserEntity user = queryByMobile(form.getMobile());
		Assert.isNull(user, "手机号或密码错误");

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new RRException("手机号或密码错误");
		}

		return user.getUserId();
	}
}
