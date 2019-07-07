package com.bdqn.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@EnableTransactionManagement//开启事务管理器
@Configuration//表示该类是配置类
@MapperScan("com.bdqn.dao")//扫描mapper接口，目的是关联自定义的mapper接口
public class PageHelperMPConfig {

	/**
	 * 分页插件
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
}
