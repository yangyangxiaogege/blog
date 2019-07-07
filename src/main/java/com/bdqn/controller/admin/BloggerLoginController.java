package com.bdqn.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bdqn.entity.Blogger;

/**
 * 博主登陆控制器
 * @author yxy
 *
 */
@Controller
public class BloggerLoginController {

	/**
	 * 去到登陆页面
	 * @return
	 */
	@RequestMapping("/login.html")
	public String toLogin() {
		return "login";
	}
	
	/**
	 * 去到首页
	 * @return
	 */
	@RequestMapping("/admin/main")
	public String toMain() {
		return "admin/main";
	}
	/**
	 * 登陆操作
	 * @return
	 */
	@PostMapping("/login")
	public String login(Blogger blogger,Model model) {
		// 获取当前登陆主题
		Subject subject = SecurityUtils.getSubject();
		// 创建令牌
		UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(),blogger.getPassword());
		try {
			// 登陆
			subject.login(token);
			// 登陆成功后，将用户存入session 中
			Session session = subject.getSession();
			session.setAttribute("currentUser", blogger);
			// 重定向到首页
			return "redirect:/admin/main";
		} catch (UnknownAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("errorMsg", "用户名不存在");
		}catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "密码有误");
		}
		
		return "login";
		
	}
}
