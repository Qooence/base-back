package com.qooence.base.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qooence.base.admin.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 系统用户Token
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Mapper
@Repository
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);
	
}
