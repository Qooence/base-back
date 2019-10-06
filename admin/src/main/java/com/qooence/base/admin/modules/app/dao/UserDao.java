package com.qooence.base.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qooence.base.admin.modules.app.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}
