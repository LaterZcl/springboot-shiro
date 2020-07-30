package com.later.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@RequestMapping(name="测试", value="/hello", method=RequestMethod.POST)
	@ResponseBody
	public String hello() {
		System.err.println("UserController.hello()");
		return "Hello,大帅比张成林";
	}
	
	@RequestMapping(name="测试thymeleaf", value="/testThymeleaf", method=RequestMethod.GET)
	public String testThymeleaf(Model model) {
		//存入数据
		model.addAttribute("name", "大帅比张成林");
		//返回test.html
		return "test";
	}
	
	@RequestMapping(name="用户添加功能", value="/add", method=RequestMethod.GET)
	public String add() {
		return "/user/add";
	}
	
	@RequestMapping(name="用户修改功能", value="/update", method=RequestMethod.GET)
	public String update() {
		return "/user/update";
	}
	
	@RequestMapping(name="拦截跳转登录页面", value="/toLogin", method=RequestMethod.GET)
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping(name="未授权页面", value="/unauthorized", method=RequestMethod.GET)
	public String unauthorized() {
		return "unauthorized";
	}
	
	@RequestMapping(name="登录", value="/login", method=RequestMethod.POST)
	public String login(String name, String pwd, Model model) {
		/**
		 * 使用Shiro编写认证操作
		 */
		//1.获取Subject
		Subject subject = SecurityUtils.getSubject();
		//2.封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
		//3.执行登录方法
		try {
			//无异常登录成功
			subject.login(token);
			return "redirect:/testThymeleaf";
		} catch (UnknownAccountException e) {
			model.addAttribute("msg", "未知账户");
			return "login";
		} catch (IncorrectCredentialsException e) {
			model.addAttribute("msg", "密码错误");
			return "login";
		}
	}
}
