package com.qooence.base.admin.modules.sys.dao;

import com.qooence.base.admin.modules.sys.entity.SysDictEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典表
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Mapper
@Repository
public interface SysDictiDao extends BaseMapper<SysDictEntity> {

    /**
     * 根据获取字典
     * @param pCode 父类标识
     * @return
     */
    List<SysDictEntity> queryByPCode(String pCode);

}
