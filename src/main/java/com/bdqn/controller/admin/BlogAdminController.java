package com.bdqn.controller.admin;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.entity.Blog;
import com.bdqn.lucene.BlogIndex;
import com.bdqn.service.BlogService;
import com.bdqn.service.CommentService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * <p>
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {
	
	@Resource
	private BlogService blogService;
	
	@Resource
	private CommentService commentService;
	
	/**
	 * 去到写博客页面
	 * @return
	 */
	@RequestMapping("/writeBlog")
	public String toWriteBlog() {
		return "/admin/writeBlog";
	}
	
	/**
	 * 添加博文
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/addBlog")
	@ResponseBody
	public Object addBlog(Blog blog) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		int count = blogService.addBlog(blog);
		try {
			
			BlogIndex blogIndex = new BlogIndex();
			// 为博文添加索引
			blogIndex.addIndex(blog);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (count > 0) {
			map.put("success", true);
		}else {
			map.put("success", false);
		}
		
		return map;
	}
	
	/**
	 * 去到博文管理页面
	 * @return
	 */
	@RequestMapping("/toManager")
	public String toBlogManager() {
		return "/admin/blogManage";
	}
	
	/**
	 * 获取博文列表
	 * 并进行分页
	 * @param blog
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(Blog blog,Long page,int rows) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		Page<Blog> blogPage = new Page<Blog>(page,rows);
		
		IPage<Blog> iPage = blogService.findInnerBlogListByPage(blogPage, blog);
		
		map.put("total", iPage.getTotal()); // 总记录数
		map.put("rows", iPage.getRecords()); // 总记录列表
		
		return map;
	}
	
	/**
	 * 批量删除博文
	 * @param ids 由id拼接成的字符串，用逗号隔开
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public Object deleteBlog(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		BlogIndex blogIndex = new BlogIndex();
		String [] blogIds = ids.split(",");
		
		// 操作成功计数器
		int successCount = 0;
		
		try {
			for (int i = 0; i < blogIds.length; i++) {
				// 删除评论
				commentService.deleteCommentByBlogId(Integer.parseInt(blogIds[i]));
				
				// 删除博文
				int count = blogService.deleteBlogById(Integer.parseInt(blogIds[i]));
				if (count > 0) {
					// 删除索引
					blogIndex.deleteIndex(blogIds[i]);
					successCount++;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (successCount == blogIds.length) {
			map.put("success", true);
		}else {
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 去到修改博客页面
	 * @return
	 */
	@RequestMapping("/toModifyBlog")
	public String toModifyBlog(int blogId,Model model) {
		
		model.addAttribute("blogId", blogId);
		
		return "/admin/modifyBlog";
	}
	
	/**
	 * 根据id查询博客
	 */
	@ResponseBody
	@RequestMapping("/findBlogById")
	public Object findBlogById(int blogId) {
		
		return blogService.findBlogById(blogId);
	}
	
	
	/**
	 * 添加博文
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/updateBlogById")
	@ResponseBody
	public Object updateBlogById(Blog blog) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		int count = blogService.updateBlogById(blog);
		try {
			
			BlogIndex blogIndex = new BlogIndex();
			// 为博文添加索引
			blogIndex.updateIndex(blog);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (count > 0) {
			map.put("success", true);
		}else {
			map.put("success", false);
		}
		
		return map;
	}
}

