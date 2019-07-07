package com.bdqn.dao;

import com.bdqn.entity.Blog;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
public interface BlogMapper extends BaseMapper<Blog> {

	/**
	 * 查询blog并且按照日期进行分组统计数量
	 * @return
	 * @throws Exception
	 */
	List<Blog> findBlogDateAndCount() throws Exception;
	
	/**
	 * 根据指定的条件进行分查询
	 * @param page
	 * @param blog
	 * @return
	 */
	IPage<Blog> findBlogListByPage(@Param("page")Page<Blog> page,@Param("blog")Blog blog) throws Exception;
	
	/**
	 * 根据博文id查询博文信息
	 * @param id
	 * @return
	 */
	Blog findBlogById(@Param("id") int id) throws Exception;
	
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
	 * 查询博文信息并分页
	 * @param page
	 * @param blog
	 * @return
	 */
	IPage<Blog> findInnerBlogListByPage(@Param("page")Page<Blog> page,@Param("blog")Blog blog);
	
}
