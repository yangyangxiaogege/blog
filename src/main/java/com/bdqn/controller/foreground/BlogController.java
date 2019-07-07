package com.bdqn.controller.foreground;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bdqn.entity.Blog;
import com.bdqn.lucene.BlogIndex;
import com.bdqn.service.BlogService;
import com.bdqn.service.CommentService;

@Controller
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;
	@Resource
	private CommentService commentService;
	
	@RequestMapping("/articles/{id}")
	public String view(@PathVariable("id")int id,Model model) {
		// 获取数据
		Blog blog = blogService.findBlogById(id);
		// 获取关键字
		String keyWord = blog.getKeyWord();
		// 判断该博客是否有关键字
		if (StringUtils.isEmpty(keyWord)) {
			model.addAttribute("keyWords", null);
		}else {
			String [] keyWords = keyWord.split(" "); // 每隔关键字以空格隔开
			model.addAttribute("keyWords", keyWords);
		}
		
		// 查询上一篇和下一篇
		String prevAndNextHtml = generatePrevAndNextLink(blogService.findPrevBlog(id), blogService.findNextBlog(id));
		// 修改阅读数量
		blog.setClickHit(blog.getClickHit()+1);
		blogService.updateById(blog);
		
		// 存储博客信息
		model.addAttribute("blog", blog);
		// 存储上一篇和下一篇html 链接
		model.addAttribute("prevAndNextHtml", prevAndNextHtml);
		// 存储评论信息
		model.addAttribute("commentList", commentService.findCommentListByBlogIdAndState(id, 2)); // 2表示评论已经审核通过
		// 设置内容模板
		model.addAttribute("pageContent", "foreground/blog/view");
		
		return "/index";
	}
	
	/**
	 * 生成博文上一篇和下一篇连接
	 * @return
	 */
	public String generatePrevAndNextLink(Blog prev,Blog next) {
		StringBuffer sb = new StringBuffer();
		if (prev == null || prev.getId() == null) {
			sb.append("<p>上一篇：没有上一篇了</p>");
		}else {
			sb.append("<p>上一篇：<a href='/blog/articles/"+prev.getId()+"'>"+prev.getTitle()+"</a></p>");
		}
		
		if (next == null || next.getId() == null) {
			sb.append("<p>下一篇：没有下一篇了</p>");
		}else {
			sb.append("<p>下一篇：<a href='/blog/articles/"+next.getId()+"'>"+next.getTitle()+"</a></p>");
		}
		
		return sb.toString();
	}
	
	/**
	 * 查询博文
	 * @param page 页码
	 * @param keyWord
	 * @return
	 */
	@PostMapping("/query")
	public String query(String keyWord,@RequestParam(value="page",required=false,defaultValue="1")Integer page,Model model) {
		int pageSize = 3; // 每页显示博文数量
		BlogIndex blogIndex = new BlogIndex();
		try {
			// 查询博文信息
			List<Blog> list = blogIndex.searchBlog(keyWord);
			
			int toIndex = list.size()>=page*pageSize ? page*pageSize : list.size();
			// 从查询出的博文列表中，获取当前需要的部分博文信息
			List<Blog> result = list.subList((page-1)*pageSize, toIndex);
			
			model.addAttribute("result", result);
			model.addAttribute("keyWord", keyWord);
			model.addAttribute("total", list.size());
			
			model.addAttribute("pageCode", genPagination(page, pageSize, list.size(), keyWord));
			model.addAttribute("pageContent", "foreground/common/result");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/index";
	}
	
	/**
	 * 生成分页代码
	 * @param targetUrl
	 * @param totalNum
	 * @param currentPage
	 * @param pageSize
	 * @param keyWord
	 * @return
	 */
	public String genPagination(int page,int pageSize,int total,String keyWord) {
		StringBuffer sb = new StringBuffer();
		// 获取总页数
		int totalPage = total % pageSize == 0 ? total/pageSize : (total/pageSize)-1;
		
		sb.append("<nav aria-label='...'>");
		sb.append(" <ul class='pager'>");
		// 判断是否有上一页
		if(page>1) {
			sb.append("<li><a href='/blog/query?page="+(page-1)+"&keyWord="+keyWord+"'>上一页</a></li>");
		}else {
			sb.append(" <li class='disabled'><a>上一页</a></li>");
		}
		// 判断是否有下一页
		if (page<total) {
			sb.append("<li><a href='/blog/query?page="+(page+1)+"&keyWord="+keyWord+"'>下一页</a></li>");
		}else {
			sb.append(" <li class='disabled'><a>下一页</a></li>");
		}
		sb.append("</ul>");
		sb.append("</nav>");
		return sb.toString();
	}

}
