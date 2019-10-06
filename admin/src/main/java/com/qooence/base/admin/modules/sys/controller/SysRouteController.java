package com.qooence.base.admin.modules.sys.controller;

import java.util.List;
import java.util.Set;

import com.qooence.base.admin.common.annotation.SysLog;
import com.qooence.base.admin.common.utils.Constant;
import com.qooence.base.admin.common.utils.R;
import com.qooence.base.admin.common.validator.ValidatorUtils;
import com.qooence.base.admin.modules.sys.entity.SysRouteEntity;
import com.qooence.base.admin.modules.sys.service.ShiroService;
import com.qooence.base.admin.modules.sys.service.SysRouteService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-11-22 17:51:40
 */
@RestController
@RequestMapping("sys/route")
public class SysRouteController extends AbstractController{
    @Autowired
    private SysRouteService sysRouteService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public R nav(){
      List<SysRouteEntity> routeList = sysRouteService.getUserRouteList(getUserId());
      Set<String> permissions = shiroService.getUserPermissions(getUserId());
      return R.ok().put("routeList", routeList).put("permissions", permissions);
    }

    /**
     * 获取由路由组成的树
     * @return
     */
    @GetMapping("/tree")
    @RequiresPermissions("sys:route:list")
    public R tree(){
        List<SysRouteEntity> tree = sysRouteService.getUserRouteTree(null);
        return R.ok().put("tree",tree);
    }

    /**
     * 获取由路由(按钮除外)组成的树
     * @return
     */
    @GetMapping("/selectTree")
    public R selectTree(){
        List<SysRouteEntity> tree = sysRouteService.getUserRouteList((long)Constant.SUPER_ADMIN);
        return R.ok().put("tree",tree);
    }


    /**
     * 路由信息
     */
    @GetMapping("/info/{routeId}")
    @RequiresPermissions("sys:route:info")
    public R info(@PathVariable("routeId") Long routeId){
        SysRouteEntity route = sysRouteService.selectById(routeId);
        return R.ok().put("route", route);
    }

    /**
     * 保存路由
     */
    @SysLog("保存路由")
    @PostMapping("/save")
    @RequiresPermissions("sys:route:save")
    public R save(@RequestBody SysRouteEntity route){
        ValidatorUtils.validateEntity(route);
        sysRouteService.save(route);
        return R.ok();
    }

    /**
     * 修改路由
     */
    @SysLog("修改路由")
    @PostMapping("/update")
    @RequiresPermissions("sys:route:update")
    public R update(@RequestBody SysRouteEntity route){
        ValidatorUtils.validateEntity(route);
        sysRouteService.update(route);
        return R.ok();
    }

    /**
     * 删除路由
     */
    @SysLog("删除路由")
    @PostMapping("/delete")
    @RequiresPermissions("sys:route:delete")
    public R delete(@RequestBody Long[] routeIds){
        sysRouteService.deleteBatch(routeIds);
        return R.ok();
    }

}
