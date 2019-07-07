package com.bdqn.service;

import com.bdqn.entity.Blogger;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
public interface BloggerService extends IService<Blogger> {

	/**
	 * 根据博主用户名获取博主信息
	 * @param userName
	 * @return
	 */
	Blogger getBloggerByUserName(String userName) throws Exception;
}
