package com.bdqn.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bdqn.dao.BlogMapper;
import com.bdqn.entity.Blog;
import com.bdqn.service.BlogService;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

	@Resource
	private BlogMapper blogMapper;

	@Resource
	private RedisTemplate<String , String> redisTemplate;

	@Override
	public String findBlogDateAndCount() {
		// TODO Auto-generated method stub
		// 判断缓存中是否已经存在所需数据
		String DateList = redisTemplate.opsForValue().get("blog_blogDateAndCount");

		if (DateList == null || DateList.length() == 0) {
			// 从数据库中获取数据
			try {
				List<Blog> blogList = blogMapper.findBlogDateAndCount();
				// 序列化blogList
				DateList = JSON.toJSONString(blogList);
				// 将DateList 存进redis中
				redisTemplate.opsForValue().set("blog_blogDateAndCount", DateList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return DateList;
	}

	@Override
	public IPage<Blog> findBlogListByPage(Page<Blog> page, Blog blog) {
		// TODO Auto-generated method stub
		try {
			return blogMapper.findBlogListByPage(page, blog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Blog findBlogById(int id) {
		// TODO Auto-generated method stub
		try {
			return blogMapper.findBlogById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Blog findPrevBlog(int blogId) {
		// TODO Auto-generated method stub
		return blogMapper.findPrevBlog(blogId);
	}

	@Override
	public Blog findNextBlog(int blogId) {
		// TODO Auto-generated method stub
		return blogMapper.findNextBlog(blogId);
	}

	@Override
	public int addBlog(Blog blog) {
		// TODO Auto-generated method stub
		// 设置发表时间
		blog.setReleaseDate(new Date());
		// 设置点击量
		blog.setClickHit(0);
		// 设置阅读量
		blog.setReplyHit(0);

		// 清空博文类型列表缓存
		redisTemplate.delete("blog_blogtypeAndCount");
		// 清空日期类型列表缓存
		redisTemplate.delete("blog_blogDateAndCount");

		return blogMapper.insert(blog);
	}

	@Override
	public IPage<Blog> findInnerBlogListByPage(Page<Blog> page, Blog blog) {
		// TODO Auto-generated method stub
		return blogMapper.findInnerBlogListByPage(page, blog);
	}

	@Override
	public int deleteBlogById(int id) {
		// TODO Auto-generated method stub
		
		// 清空博文类型列表缓存
		redisTemplate.delete("blog_blogtypeAndCount");
		// 清空日期类型列表缓存
		redisTemplate.delete("blog_blogDateAndCount");
		
		return baseMapper.deleteById(id);
	}

	@Override
	public int updateBlogById(Blog blog) {
		// TODO Auto-generated method stub

		// 清空博文类型列表缓存
		redisTemplate.delete("blog_blogtypeAndCount");
		// 清空日期类型列表缓存
		redisTemplate.delete("blog_blogDateAndCount");
		
		return baseMapper.updateById(blog);
	}

}
