package com.qooence.base.admin.modules.facility.controller;

import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qooence.base.admin.modules.facility.service.AlarmService;
import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.R;



/**
 * 报警表
 *
 * @author jyq
 * @email qooence@qooence.uu.me
 * @date 2018-12-04 19:15:25
 */
@RestController
@RequestMapping("facility/alarm")
public class AlarmController {
    @Autowired
    private AlarmService alarmService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("facility:alarm:list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = alarmService.queryPage(params);

        return R.ok().put("page", page);
    }
}
