package cn.itcast.bookstore.user.service;



import cn.itcast.bookstore.user.dao.UserDao;
import cn.itcast.bookstore.user.domain.User;

public class UserService {
	private UserDao userDao=new UserDao();
	
	public void regist(User form) throws UserException{
		
		String email=form.getEmail();		
		User _user=userDao.findByUserEmail(email);
		if(_user!=null){
			throw new UserException("邮箱已经被注册") ;		
		}
		String username=form.getUsername();		
		User user1=userDao.findByUserName(username);
		if(user1!=null){
			throw new UserException("用户名已经被注册") ;		
		}
		
		userDao.addUser(form);
	}

	public void active(String code) throws UserException {
		
		User _user=userDao.findByUserCode(code);
		if(_user==null)throw new UserException("该用户没有注册,激活失败");
		if(_user.isState()) throw new UserException("您好,您已经激活,不要在激活,除非你想死");
		userDao.active(_user.getUid(),true);
	}

	public User login(User form) throws UserException {
		User _user=userDao.findByUserName(form.getUsername());
		if(_user==null)throw new UserException("该用户没有注册,登录失败");
		if(!_user.getPassword().equalsIgnoreCase(form.getPassword())){
			throw new UserException("密码错误,登录失败");
		}
			
		return _user;
	}
	
}
