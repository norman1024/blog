package cn.tedu.note.test;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.User;

public class TestCase {
	
	ApplicationContext ctx;
	@Before
	public void init(){
		ctx = 
		new ClassPathXmlApplicationContext(
			"spring-mybatis.xml",
			"spring-service.xml");
	}
	//@Test //测试MyBatis配置
	public void testMapperScanner(){
		Object obj = 
			ctx.getBean("mapperScanner");
		System.out.println(obj); 
	}
	//@Test//测试UserDAO的Save方法
	public void testSaveUser(){
		UserDao dao = 
			ctx.getBean(
			"userDao", UserDao.class);
		System.out.println(dao);
		String id=UUID.randomUUID().toString();
		System.out.println(id);
		User user=new User(
			id,"Tom","123","","Tomcat");
		dao.saveUser(user); 
	}
	
	@Test
	public void testFindUserById(){
		String id="8a9875ab-0af9-4a0b-a605-e3b30c29f3c4";
		UserDao dao = 
			ctx.getBean("userDao", UserDao.class);
		User user=dao.findUserById(id);
		System.out.println(user); 
	}
}









