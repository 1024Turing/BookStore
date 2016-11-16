package cn.itcast.bookstore.user.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	private QueryRunner qr=new TxQueryRunner();
	/*
	 * 根据用户名来查询
	 */
	public User findByUserName(String username){
		try{
			String sql="select * from user where username=?";
			
			return qr.query(sql, new BeanHandler<User>(User.class),username);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/*
	 * 根据邮箱查询
	 */
	public User findByUserEmail(String email){
		try{
			String sql="select * from user where email=?";
			
			return qr.query(sql, new BeanHandler<User>(User.class),email);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/*
	 * 插入用户
	 */
	public void addUser(User user){
		try{
			String sql="insert into user values(?,?,?,?,?,?)";
			Object[] params={user.getUid(),user.getUsername(),user.getPassword(),user.getEmail()
					,user.getCode(),user.isState()};
			 qr.update(sql, params);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public User findByUserCode(String code) {
		try{
			String sql="select * from user where code=?";
			return qr.query(sql, new BeanHandler<User>(User.class),code);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	public void active(String uid, boolean b) {
		try{
			String sql="update user set state=? where uid=? ";
			qr.update(sql,b,uid);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

}
