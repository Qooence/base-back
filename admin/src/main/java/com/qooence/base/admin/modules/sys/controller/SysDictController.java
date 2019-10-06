package com.qooence.base.admin.modules.sys.controller;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qooence.base.admin.common.annotation.SysLog;
import com.qooence.base.admin.common.utils.RedisUtils;
import com.qooence.base.admin.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qooence.base.admin.modules.sys.entity.SysDictEntity;
import com.qooence.base.admin.modules.sys.service.SysDictService;
import com.qooence.base.admin.common.utils.R;



/**
 * 字典表
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/tree")
    @RequiresPermissions("sys:dict:list")
    public R tree(){
        List<SysDictEntity> tree = sysDictService.treeList();
        return R.ok().put("tree", tree);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public R info(@PathVariable("id") String id){
        SysDictEntity sysDictionary = sysDictService.selectById(id);
        return R.ok().put("dict", sysDictionary);
    }

    /**
     * 保存
     */
    @SysLog("保存字典")
    @PostMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public R save(@RequestBody SysDictEntity dict){
        ValidatorUtils.validateEntity(dict);
        sysDictService.save(dict);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改字典")
    @PostMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public R update(@RequestBody SysDictEntity dict){
        ValidatorUtils.validateEntity(dict);
        sysDictService.update(dict);
        return R.ok();
    }

    /**
     * 删除字典
     * @param id id
     * @return
     */
    @SysLog("删除字典")
    @RequestMapping("/delete/{id}")
    @RequiresPermissions("sys:dict:delete")
    public R deleteOne(@PathVariable Long id){
        SysDictEntity dict = sysDictService.selectById(id);

        List<SysDictEntity> child = sysDictService.queryByPCode(dict.getCode());
        if(null != child && child.size() > 0){
            List<Long> cList = new ArrayList<>();
            for(SysDictEntity d : child){
                cList.add(d.getId());
                redisUtils.delete("dict:code:"+d.getCode());
            }
            sysDictService.deleteBatchIds(cList);
        }

        sysDictService.deleteById(id);
        redisUtils.delete("dict:pCode:"+dict.getPCode());
        redisUtils.delete("dict:code:"+dict.getCode());

        return R.ok();
    }


    /**
     * 删除字典
     * @param ids ids
     * @return
     */
    @SysLog("删除字典")
    @PostMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public R delete(@RequestBody Long[] ids){
        sysDictService.deleteBatch(ids);
        return R.ok();
    }

    /**
     * 查询父类
     * @param pCode 父类标识
     * @return
     */
    @RequestMapping("/parent")
    public R selectParent(String pCode){
        List<SysDictEntity> list = sysDictService.queryByPCode(pCode);
        return R.ok().put("list", list);
    }


    @PostMapping("/exist")
    public R selectExist(@RequestBody SysDictEntity dict){
        //有id =》 是更新
        Boolean result = false;
        if(null != dict.getId()){
            SysDictEntity d = sysDictService.selectById(dict.getId());

            if(!d.getCode().equals(dict.getCode())){
                int count = sysDictService.selectCount(
                        new EntityWrapper<SysDictEntity>().eq("code", dict.getCode()));
                result = count > 0;
            }
        }else {
            int count = sysDictService.selectCount(
                    new EntityWrapper<SysDictEntity>().eq("code", dict.getCode()));
            result = count > 0;
        }

        return R.ok().put("result", result);
    }

    /**
     * 根据标识查询字典
     * @param code
     * @return
     */
    @RequestMapping("/code")
    public R selectCode(String code){
        List<SysDictEntity> list = redisUtils.getList("dict:code:"+code,SysDictEntity.class);
        if(null == list){
            list = sysDictService.selectList(new EntityWrapper<SysDictEntity>().eq("code",code));
            redisUtils.set("dict:code:"+code,list);
        }
        return R.ok().put("list",list);
    }


}
