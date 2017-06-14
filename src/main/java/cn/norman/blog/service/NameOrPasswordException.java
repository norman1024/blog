package cn.norman.blog.service;

public class NameOrPasswordException extends RuntimeException 
{
	
	public NameOrPasswordException(){}
	
	public NameOrPasswordException(String message) {super(message);}
	
}
