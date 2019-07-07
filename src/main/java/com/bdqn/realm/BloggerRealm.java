package com.bdqn.realm;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.bdqn.entity.Blogger;
import com.bdqn.service.BloggerService;

/**
 * 自定义realm
 * @author ASUS
 *
 */
@Component
public class BloggerRealm extends AuthorizingRealm{

	@Resource
	private BloggerService bloggerService;
	
	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	// 验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		// 获取当前登陆用户名
		String userName = (String) token.getPrincipal();
		
		try {
			Blogger blogger = bloggerService.getBloggerByUserName(userName);
			if (blogger != null) {
				// 进行身份验证
				AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(blogger.getUserName(),blogger.getPassword(),"");
				
				return authenticationInfo;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
