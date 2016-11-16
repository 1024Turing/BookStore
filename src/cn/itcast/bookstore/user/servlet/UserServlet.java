package cn.itcast.bookstore.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.user.domain.User;
import cn.itcast.bookstore.user.service.UserException;
import cn.itcast.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;

public class UserServlet extends BaseServlet {
	private UserService userService=new UserService();
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,UserException {
		request.getSession().invalidate();
		return  "r:/index.jsp";
	}
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,UserException {
		User form=CommonUtils.toBean(request.getParameterMap(),
				User.class);
		try{
			User user=userService.login(form);
			request.getSession().setAttribute("session_user", user);
			/*
			 * 登录的时候给一个购物车
			 */
			request.getSession().setAttribute("cart", new Cart());
			return "r:/index.jsp";
		}catch(UserException e){
			request.setAttribute("form", form);
			request.setAttribute("msg", e.getMessage());
			return "f:/jsps/user/login.jsp";
		}
	}

	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,UserException {
		String code=request.getParameter("code");
		try{
			userService.active(code);
			request.setAttribute("msg", "激活成功");
		}catch(UserException e){
			
			request.setAttribute("msg", e.getMessage());
		}
		
		return "f://jsps/msg.jsp";
	}
	
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		User form=CommonUtils.toBean(request.getParameterMap(), User.class);
	
		/*
		 * 补全uid code
		 */
		String uid=CommonUtils.uuid();
		form.setUid(uid);
		form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
		/*
		 * 校验表单数据
		 */
		Map<String,String> errors=new HashMap<String, String>();
		String username=form.getUsername();
		if(username==null||username.trim().isEmpty()){
			errors.put("username","用户名不能为空");
		}else if(username.length()<3 ||username.length()>10){
			errors.put("username", "用户名必须由3~10个字母组成");
		}
		
		String password=form.getPassword();
		
	
		if(password==null||password.trim().isEmpty()){
			errors.put("password", "密码不能为空");
		}else if(password.length()<6||password.length()>16){
			errors.put("password", "密码必须在6~16位之间");
		}
		
		String email=form.getEmail();
		if(email==null||email.trim().isEmpty()){
			errors.put("email", "邮箱不能为空");
		}else if(email.matches("//w+@//w//.//w")){
			errors.put("email", "密码格式不对");
		}
		
		if(errors.size()>0){
			request.setAttribute("errors",errors);
			request.setAttribute("form", form);
			return "f://jsps/user/regist.jsp";
		}
		
		try {
			userService.regist(form);
			
		} catch (UserException e) {
			
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f://jsps/user/regist.jsp";
		}
		Properties prop=new Properties();
		prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		
		String pwd=prop.getProperty("pwd");
		String from=prop.getProperty("from");
		String host=prop.getProperty("host");
		String uname=prop.getProperty("uname");
		String content=prop.getProperty("content");
		String subject=prop.getProperty("subject");
		String to=form.getEmail();
		content = MessageFormat.format(content, form.getCode());//替换{0}
		//服务器名称,用户名,密码
		Session session=MailUtils.createSession(host, uname, 
						pwd);
		//发件人邮箱,收件人邮箱,标题,正文
		Mail mail=new Mail(from,to,subject,content);
		try{
			MailUtils.send(session, mail);
		}catch(Exception e){
			throw new Exception(e);
		}
		
		request.setAttribute("msg", "恭喜,注册成功!请马上到邮箱激活");
		return "f://jsps/msg.jsp";
	}

}
