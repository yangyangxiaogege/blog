package com.bdqn.service;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bdqn.entity.Blog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
public interface BlogService extends IService<Blog> {
	
	/**
	 * 查询blog并且按照日期进行分组统计数量
	 * @return
	 * @throws Exception
	 */
	String findBlogDateAndCount();
	
	/**
	 * 根据指定的条件进行分查询
	 * @param page
	 * @param blog
	 * @return
	 */
	IPage<Blog> findBlogListByPage(Page<Blog> page,Blog blog);
	
	/**
	 * 根据博文id查询博文信息
	 * @param id
	 * @return
	 */
	Blog findBlogById(int id);
	
	
	/**
	 * 查找上一篇博客
	 * @param blogId
	 * @return
	 */
	Blog findPrevBlog(@Param("id")int blogId);

	/**
	 * 查找下一篇博客
	 * @param blogId
	 * @return
	 */
	Blog findNextBlog(@Param("id")int blogId);
	
	/**
	 * 添加博文
	 * @param blog
	 * @return
	 */
	int addBlog(Blog blog);
	
	/**
	 * 查询博文信息并分页
	 * @param page
	 * @param blog
	 * @return
	 */
	IPage<Blog> findInnerBlogListByPage(Page<Blog> page,Blog blog);
	
	
	/**
	 * 根据博客id删除博客信息
	 * @param id
	 * @return
	 */
	int deleteBlogById(int id);
	
	/**
	 * 更新博客
	 * @param blog
	 * @return
	 */
	int updateBlogById(Blog blog);
}
