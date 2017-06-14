package cn.norman.blog.dao;

import cn.norman.blog.entity.User;

public interface UserDao 
{
	
	User findUserByName(String name);
	
}
