package com.qooence.base.admin.modules.sys.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Sets;
import com.qooence.base.admin.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.qooence.base.admin.modules.sys.dao.SysDictiDao;
import com.qooence.base.admin.modules.sys.entity.SysDictEntity;
import com.qooence.base.admin.modules.sys.service.SysDictService;
import org.springframework.transaction.annotation.Transactional;


@Service("sysDictionaryService")
public class SysDictServiceImpl extends ServiceImpl<SysDictiDao, SysDictEntity> implements SysDictService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SysDictiDao sysDictiDao;

    @Override
    public List<SysDictEntity> treeList(){
        List<SysDictEntity> topList = sysDictiDao.selectList(new EntityWrapper<SysDictEntity>().eq("p_code","0").orderBy("seq"));
        List<SysDictEntity> dictList = sysDictiDao.selectList(new EntityWrapper<SysDictEntity>().ne("p_code","0").orderBy("seq"));
        return this.tree(dictList,topList);
    }

    @Override
    public List<SysDictEntity> queryByPCode(String pCode) {
        List<SysDictEntity> list = new ArrayList<SysDictEntity>();
        list  = redisUtils.getList("dict:pCode:"+pCode, SysDictEntity.class);
        if(null == list){
            list = baseMapper.queryByPCode(pCode);
            redisUtils.set("dict:pCode:"+pCode,list);
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictEntity dict) {
        this.insert(dict);
        redisUtils.delete("dict:pCode:"+dict.getPCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictEntity dict) {
        redisUtils.delete("dict:pCode:"+dict.getPCode());
        redisUtils.delete("dict:code:"+dict.getCode());
        SysDictEntity old = this.selectById(dict.getId());
        if(!old.getCode().equals(dict.getCode())){
            List<SysDictEntity> childList =  baseMapper.queryByPCode(old.getCode());
            childList.forEach(d -> {
                d.setPCode(dict.getCode());
                this.updateAllColumnById(d);
            });
        }
        this.updateAllColumnById(dict);//全部更新

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 装配成树结构
     * @param dictList 非顶级类目集合
     * @param topList 顶级类目集合-结果集
     * @return
     */
    private List<SysDictEntity> tree(List<SysDictEntity> dictList,List<SysDictEntity> topList){
        if(ObjectUtil.isNotNull(dictList)){
            //过滤条件set，
            Set<Long> set = Sets.newHashSetWithExpectedSize(dictList.size());
            topList.forEach(route -> getChild(route,dictList,set));
            return topList;
        }
        return null;
    }

    /**
     * 递归获取子目录
     * @param dict 上级
     * @param dictList 所有数据集合
     * @param set 已循坏对象ID集合
     */
    private void getChild(SysDictEntity dict,List<SysDictEntity> dictList,Set<Long> set){
        dict.setTitle(dict.getName());
        List<SysDictEntity> childList = new ArrayList<>();
        dictList.stream()
                //判断是否已循坏过当前对象
                .filter(d -> !set.contains(d.getId()))
                //判断是否父子关系
                .filter(d -> d.getPCode().equals(dict.getCode()))
                .filter(d -> set.size() <= dictList.size())
                .forEach(d -> {
                    //放入set,递归循环时可以跳过这个子目录，提高循环效率
                    set.add(d.getId());
                    //获取当前类目的子类目
                    getChild(d,dictList,set);
                    childList.add(d);
                });
        dict.setChildren(childList);
    }

}
