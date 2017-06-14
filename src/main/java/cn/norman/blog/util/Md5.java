package cn.norman.blog.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

public class Md5 
{
	
	private static final String salt = "cn.norman.blog";
	/**
	 * ¼ÓÑÎ
	 */
	public static String saltMd5(String str)
	{
		try 
		{
			byte[] data = str.getBytes("utf-8");
			MessageDigest messageDigest = MessageDigest.getInstance("md5");
			messageDigest.update(data);
			messageDigest.update(salt.getBytes("utf-8"));
			byte[] md5 = messageDigest.digest();
			String code = Base64.encodeBase64String(md5);
			return code;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * ²»¼ÓÑÎ
	 */
	public static String md5(String str)
	{
		try 
		{
			byte[] data = str.getBytes("utf-8");
			MessageDigest messageDigest = MessageDigest.getInstance("md5");
			messageDigest.update(data);
			byte[] md5 = messageDigest.digest();
			String code = Base64.encodeBase64String(md5);
			return code;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
}
