package cn.norman.blog.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.norman.blog.dao.BlogDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("blogService")
public class BlogServiceImp implements BlogService 
{
	@Autowired
	private BlogDao blogDao;
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> listBlogs(String userId) 
	{
		if(userId == null || userId.trim().isEmpty()) {throw new ServiceException("userId²»ÄÜÎª¿Õ!");}
		
		return blogDao.findBlogByUserId();
	}
	
	
	
}
