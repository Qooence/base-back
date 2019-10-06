package com.qooence.base.admin.modules.app.service;


import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.modules.app.entity.UserEntity;
import com.qooence.base.admin.modules.app.form.LoginForm;

/**
 * 用户
 * @author jyq
 * @email qooence@qooence.uu.me
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(LoginForm form);
}
