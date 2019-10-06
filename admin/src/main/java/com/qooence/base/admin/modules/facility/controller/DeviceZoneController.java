package com.qooence.base.admin.modules.facility.controller;

import java.util.Arrays;
import java.util.Map;

import com.qooence.base.admin.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qooence.base.admin.modules.facility.entity.DeviceZoneEntity;
import com.qooence.base.admin.modules.facility.service.DeviceZoneService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.R;



/**
 * 设备防区表
 *
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
@RestController
@RequestMapping("facility/zone")
public class DeviceZoneController {
    @Autowired
    private DeviceZoneService deviceZoneService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("facility:zone:list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = deviceZoneService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("facility:zone:info")
    public R info(@PathVariable("id") String id){
        DeviceZoneEntity deviceZone = deviceZoneService.selectById(id);

        return R.ok().put("zone", deviceZone);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("facility:zone:save")
    public R save(@RequestBody DeviceZoneEntity deviceZone){
        deviceZoneService.insert(deviceZone);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("facility:zone:update")
    public R update(@RequestBody DeviceZoneEntity deviceZone){
        ValidatorUtils.validateEntity(deviceZone);
        deviceZoneService.updateAllColumnById(deviceZone);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("facility:zone:delete")
    public R delete(@RequestBody String[] ids){
        deviceZoneService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
