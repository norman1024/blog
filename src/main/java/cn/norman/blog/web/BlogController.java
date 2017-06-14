package cn.norman.blog.web;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.norman.blog.service.BlogServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/blog")
public class BlogController implements Serializable {
	
	@Autowired
	private BlogServiceImp blogService;
	
	@RequestMapping("/list")
	@ResponseBody
	public JsonResult<List<Map<String, Object>>> list(String userId)
	{
		List<Map<String, Object>> list = blogService.listBlogs(userId);
		return new JsonResult<List<Map<String, Object>>>(list);
	}
	
	
}
