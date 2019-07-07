package com.bdqn.controller.foreground;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.entity.Blog;
import com.bdqn.service.BlogService;
import com.bdqn.service.BlogtypeService;
import com.bdqn.utils.PageUtil;

@Controller
public class IndexController {

	@Resource
	private BlogtypeService blogtypeService;
	
	@Resource
	private BlogService blogService;
	
	/**
	 * 跳转至index页面
	 * @return
	 */
	@RequestMapping("/index.html")
	public String index(Blog blog,@RequestParam(value="page",required=false,defaultValue="1")Long current,Model model) {
		// 创建page对象，参数1：当前页码，参数2：每页显示多少条信息
		Page<Blog> page = new Page<Blog>(current,5);
		// 获取分页信息
		IPage<Blog> pageBlog = blogService.findBlogListByPage(page, blog);
		// 获取分页列表，并将其存储进作用域中
		model.addAttribute("blogList", pageBlog.getRecords());
		
		// 拼接查询参数
		StringBuffer param = new StringBuffer();
		if (blog.getTypeId() != null) {
			param.append("typeId="+blog.getTypeId());
		}
		if (blog.getReleaseDateStr() != null && blog.getReleaseDateStr() != "") {
			param.append("releaseDateStr="+blog.getReleaseDateStr());
		}
		// 生成分页模型（分页器html），并将其放进作用于中
		model.addAttribute("pageCode", PageUtil.genPagination("/index.html", pageBlog.getTotal(), current.intValue(), 5, param.toString()));
		
		model.addAttribute("pageContent", "foreground/common/main");
		return "/index";
	}
	
	@ResponseBody
	@RequestMapping("/typeList")
	public String typeList() {
		return blogtypeService.findBlogtypeAndCount();
	}

	@ResponseBody
	@RequestMapping("/dateList")
	public String dateList() {
		return blogService.findBlogDateAndCount();
	}
}
