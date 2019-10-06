package com.qooence.base.admin.modules.facility.controller;

import java.util.Arrays;
import java.util.Map;

import com.qooence.base.admin.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qooence.base.admin.modules.facility.entity.DeviceSensorEntity;
import com.qooence.base.admin.modules.facility.service.DeviceSensorService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.R;



/**
 * 设备配件表
 *
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
@RestController
@RequestMapping("facility/sensor")
public class DeviceSensorController {
    @Autowired
    private DeviceSensorService deviceSensorService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("facility:sensor:list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = deviceSensorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("facility:sensor:info")
    public R info(@PathVariable("id") String id){
        DeviceSensorEntity deviceSensor = deviceSensorService.selectById(id);

        return R.ok().put("sensor", deviceSensor);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("facility:sensor:save")
    public R save(@RequestBody DeviceSensorEntity deviceSensor){
        deviceSensorService.insert(deviceSensor);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("facility:sensor:update")
    public R update(@RequestBody DeviceSensorEntity deviceSensor){
        ValidatorUtils.validateEntity(deviceSensor);
        deviceSensorService.updateAllColumnById(deviceSensor);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("facility:sensor:delete")
    public R delete(@RequestBody String[] ids){
        deviceSensorService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
