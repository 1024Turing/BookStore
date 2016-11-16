package cn.itcast.bookstore.user.service;

public class UserException extends Exception {
	
	public UserException(){
		super();
	}
	
	public UserException(String msg){
		super(msg);
	}
}
