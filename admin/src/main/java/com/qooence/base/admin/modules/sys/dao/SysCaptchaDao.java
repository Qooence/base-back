package com.qooence.base.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qooence.base.admin.modules.sys.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;
/**
 * 验证码
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Mapper
public interface SysCaptchaDao extends BaseMapper<SysCaptchaEntity> {

}
