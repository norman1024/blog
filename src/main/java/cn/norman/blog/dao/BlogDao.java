package cn.norman.blog.dao;

import java.util.List;
import java.util.Map;

public interface BlogDao {
	
	List<Map<String, Object>> findBlogByUserId();
	
}
