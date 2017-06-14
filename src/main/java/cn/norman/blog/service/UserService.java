package cn.norman.blog.service;

import java.io.Serializable;

import cn.norman.blog.entity.User;

public interface UserService extends Serializable
{
	
	User login(String name, String password) throws NameOrPasswordException;
	
}
