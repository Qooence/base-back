package com.qooence.base.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qooence.base.admin.modules.sys.entity.SysDictEntity;

import java.util.List;

/**
 * 字典表
 * @author jyq
 * @email qooence@qooence.uu.me6
 */
public interface SysDictService extends IService<SysDictEntity> {

    List<SysDictEntity> treeList();

    List<SysDictEntity> queryByPCode(String pCode);

    void save(SysDictEntity dict);

    void update(SysDictEntity dict);

    void deleteBatch(Long[] ids);
}

