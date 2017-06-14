package cn.norman.blog.service;

import java.util.List;
import java.util.Map;

public interface BlogService {
	
	List<Map<String, Object>> listBlogs(String userId);
	
}
