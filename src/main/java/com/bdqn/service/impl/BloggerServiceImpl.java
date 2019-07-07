package com.bdqn.service.impl;

import com.bdqn.entity.Blogger;
import com.bdqn.dao.BloggerMapper;
import com.bdqn.service.BloggerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
@Service
public class BloggerServiceImpl extends ServiceImpl<BloggerMapper, Blogger> implements BloggerService {

	@Override
	public Blogger getBloggerByUserName(String userName) throws Exception{
		// TODO Auto-generated method stub
		QueryWrapper<Blogger> queryWrapper = new QueryWrapper<Blogger>();
		// 参数1 是数据库列名
		queryWrapper.eq("userName", userName);
		return baseMapper.selectOne(queryWrapper);
	}

}
