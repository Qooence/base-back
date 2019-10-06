package com.qooence.base.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qooence.base.admin.modules.sys.entity.SysRouteEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-11-22 17:51:40
 */
@Mapper
@Repository
public interface SysRouteDao extends BaseMapper<SysRouteEntity> {

}
