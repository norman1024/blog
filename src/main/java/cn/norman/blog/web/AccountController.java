package cn.norman.blog.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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
	//test for new mation
	@RequestMapping("/login")
	@ResponseBody
	public JsonResult<User> login(String name, String password, String code, HttpServletRequest request, HttpServletResponse response)
	{
		String serverCode = (String)request.getSession().getAttribute("code");
		if(code == null || !code.equals(serverCode)) {return new JsonResult<User>("验证码无效");}
		
		User user = userService.login(name, password);
		
		String userAgent = request.getHeader("User-Agent");			//用户代理
		long now = System.currentTimeMillis();
		String token = Md5.saltMd5(userAgent+now);
		Cookie cookie = new Cookie("token", token + "|" + now);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return new JsonResult<User>(user);
	}
	
	@RequestMapping(value = "/code.do", produces = "image/png")
	@ResponseBody
	public byte[] code(HttpServletRequest request) 
			throws Exception
	{
		
		byte[] buf;
		BufferedImage bufferedImage = new BufferedImage(80, 30, BufferedImage.TYPE_3BYTE_BGR);
		
		for(int y = 0; y < bufferedImage.getHeight(); y++)
		{
			for(int x = 0; x < bufferedImage.getWidth(); x++)
			{
				bufferedImage.setRGB(x, y, 0xEEEEEE);
			}
		}
		for(int i = 0; i < 1000; i++)
		{
			int x = (int)(Math.random() * 80);
			int y = (int)(Math.random() * 30);
			int rgb = (int)(Math.random() * 0xffffff);
			bufferedImage.setRGB(x, y, rgb);
		}
		Graphics2D graphics2D = bufferedImage.createGraphics();
		int rgb = (int)(Math.random() * 0xffffff);
		graphics2D.setColor(new Color(rgb));
		
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
		graphics2D.setFont(font);
		
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);			//抗锯齿
		
		int x = (int)(Math.random() * 10);
		int y = (int)(Math.random() * 5);
		String code = randomCode();
		request.getSession().setAttribute("code", code);
		
		graphics2D.drawString(code, 3 + x, 28 - y);
		
		//make five line in image
		for(int i = 0; i < 5; i++)
		{
			int x1 = (int)(Math.random() * 80);
			int x2 = (int)(Math.random() * 80);
			int y1 = (int)(Math.random() * 30);
			int y2 = (int)(Math.random() * 30);
			rgb = (int)(Math.random() * 0xffffff);
			graphics2D.setColor(new Color(rgb));
			graphics2D.drawLine(x1, y1, x2, y2);
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", baos);
		baos.close();
		buf = baos.toByteArray();
		return buf;
	}
	
	private String randomCode()
	{
		String arr = "1234567890ABCDEFGHIGKLMNabcdefghigklmn";
		char[] code = new char[4];
		for(int i = 0; i < code.length; i++)
		{
			code[i] = arr.charAt((int)(Math.random() * arr.length()));
		}
		return new String(code);
	}
	
	@RequestMapping("/checkCode.do")
	@ResponseBody
	public JsonResult<Boolean> checkCode(String code, HttpServletRequest request)
	{
		String serverCode = (String)request.getSession().getAttribute("code");
		if(serverCode == null) { return new JsonResult<Boolean>("失败!"); }
		if(code.equals(serverCode)){ return new JsonResult<Boolean>(true); }
		return new JsonResult<Boolean>("失败!");
	}

}
