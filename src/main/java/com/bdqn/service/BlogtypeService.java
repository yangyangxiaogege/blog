package com.bdqn.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bdqn.entity.Blogtype;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
public interface BlogtypeService extends IService<Blogtype> {
	
	/**
	 * 查询所有的博文类型和该类型下的数量
	 * @return
	 */
	String findBlogtypeAndCount();
}
