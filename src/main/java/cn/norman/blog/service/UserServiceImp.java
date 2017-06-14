package cn.norman.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.norman.blog.entity.User;
import cn.norman.blog.dao.UserDao;
import cn.norman.blog.util.Md5;
import org.mybatis.spring.SqlSessionFactoryBean;

@Service("userService")
public class UserServiceImp implements UserService
{
	@Autowired
	private UserDao userDao;
	
	@Transactional( readOnly = true)
	public User login(String name, String password) throws NameOrPasswordException 
	{
		if(name == null || name.trim().isEmpty()) { throw new NameOrPasswordException("�û�������Ϊ��!"); }
		if(password == null || password.trim().isEmpty()) { throw new NameOrPasswordException("���벻��Ϊ��!"); }
		
		User user = userDao.findUserByName(name);
		if(user == null) { throw new NameOrPasswordException("�û������������!"); }
		if(user.getPassword().equals(Md5.md5(password)) || user.getPassword().equals(Md5.saltMd5(password)))
		{
			return user;
		}
		throw new NameOrPasswordException("�û������������!");
	}

}
