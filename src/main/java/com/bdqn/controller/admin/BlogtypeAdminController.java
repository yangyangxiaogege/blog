package com.bdqn.controller.admin;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdqn.service.BlogtypeService;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
@Controller
@RequestMapping("/admin/blogtype")
public class BlogtypeAdminController {

	@Resource
	private BlogtypeService blogtypeService;
	
	/**
	 * 获取type 列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/typeItem")
	public String typeItem() {
		
		return blogtypeService.findBlogtypeAndCount();
	}
}

