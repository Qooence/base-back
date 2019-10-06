package com.qooence.base.admin.modules.facility.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qooence.base.admin.modules.facility.service.DeviceService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.R;

/**
 * 设备表
 *
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
@RestController
@RequestMapping("facility/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("facility:device:list")
    public R list(@RequestBody Map<String, Object> data){
        PageUtils page = deviceService.queryPage(data);

        return R.ok().put("page", page);
    }

}
