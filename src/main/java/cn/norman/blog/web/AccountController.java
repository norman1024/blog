package cn.norman.blog.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.norman.blog.entity.User;
import cn.norman.blog.service.UserService;
import cn.norman.blog.util.Md5;

@Controller
@RequestMapping("/account")
public class AccountController 
{
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	@ResponseBody
	public JsonResult<User> login(String username, String password, String code, HttpServletRequest request, HttpServletResponse response)
	{
		String serverCode = (String)request.getSession().getAttribute("code");
		if(code == null || !serverCode.equals(code)) {return new JsonResult<User>("验证码无效");}
		
		User user = userService.login(username, password);
		
		String userAgent = request.getHeader("User-Agent");			//用户代理
		long now = System.currentTimeMillis();
		String token = Md5.saltMd5(userAgent+now);
		Cookie cookie = new Cookie("token", token + "|" + now);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return new JsonResult<User>(user);
	}
	
}
