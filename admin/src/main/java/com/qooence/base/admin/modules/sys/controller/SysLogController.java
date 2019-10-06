package com.qooence.base.admin.modules.sys.controller;

import com.qooence.base.admin.common.utils.PageUtils;
import com.qooence.base.admin.common.utils.R;
import com.qooence.base.admin.modules.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 系统日志
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@PostMapping("/list")
	@RequiresPermissions("sys:log:list")
	public R list(@RequestBody Map<String, Object> data){
		PageUtils page = sysLogService.queryPage(data);
		return R.ok().put("page", page);
	}
}
