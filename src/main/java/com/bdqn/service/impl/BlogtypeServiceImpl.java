package com.bdqn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bdqn.dao.BlogtypeMapper;
import com.bdqn.entity.Blogtype;
import com.bdqn.service.BlogtypeService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
@Service
public class BlogtypeServiceImpl extends ServiceImpl<BlogtypeMapper, Blogtype> implements BlogtypeService {

	@Resource
	private BlogtypeMapper blogtypeMapper;
	
	@Resource
	private RedisTemplate<String , String> redisTemplate;
	
	@Override
	public String findBlogtypeAndCount() {
		// TODO Auto-generated method stub
		// 判断缓存中是否已经存在所需数据
		String typeList = redisTemplate.opsForValue().get("blog_blogtypeAndCount");
		
		if (typeList == null || typeList.length() == 0) {
			// 从数据库中获取数据
			try {
				List<Blogtype> blogtypes = blogtypeMapper.findBlogtypeAndCount();
				// 序列化blogtypes
				typeList = JSON.toJSONString(blogtypes);
				// 将typeList 存进redis中
				redisTemplate.opsForValue().set("blog_blogtypeAndCount", typeList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return typeList;
	}

}
