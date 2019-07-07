package com.bdqn.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bdqn.realm.BloggerRealm;

@Configuration
public class ShiroConfig {
	
	/**
	 * 注入userRealm
	 */
	@Resource
	private BloggerRealm bloggerRealm;
	
	/**
	 * 创建ShiroFilterFactoryBean
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		// 创建ShiroFilterFactoryBean 
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		// 设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		/*
		 * shiro内置过滤器
		 * anon 无需认证
		 * authc 必须验证
		 * user 使用记住我的功能时可以直接访问
		 * perms 需要验证权限
		 * role 需要验证角色
		 */
		// 配置过滤器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String,String>();// 有序
		// 设置放行请求
		filterChainDefinitionMap.put("/login.html", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/source/**", "anon");
		filterChainDefinitionMap.put("/index.html", "anon");		
		filterChainDefinitionMap.put("/typeList", "anon");		
		filterChainDefinitionMap.put("/dateList", "anon");		
		filterChainDefinitionMap.put("/blog/**", "anon");		
		filterChainDefinitionMap.put("/comment/**", "anon");		
		// 设置身份验证请求
		filterChainDefinitionMap.put("/admin/**", "authc");
		// -----
		// 设置所有请求
		 filterChainDefinitionMap.put("/**", "authc");
		
		// 设置过滤器链
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		// 设置登陆验证（authc）失败时跳转路径
		shiroFilterFactoryBean.setLoginUrl("/login.html");
		// 设置角色验证失败时跳转路径
		// shiroFilterFactoryBean.setUnauthorizedUrl("/users/noPower");
		
		return shiroFilterFactoryBean;
	}
	
	/**
	 * 创建安全管理器
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager getDefaultWebSecurityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置关联的realm
		securityManager.setRealm(bloggerRealm);
		return securityManager;
	}
	
	
}
