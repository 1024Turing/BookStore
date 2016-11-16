package cn.itcast.bookstore.book.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.servlet.BaseServlet;

public class BookServlet extends BaseServlet {
	private BookService bookService=new BookService();
	
	
	public String  findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	req.setAttribute("bookList", bookService.findAll());
		return "f:/jsps/book/list.jsp";
	}
	

	public String  findByCid(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("bookList", bookService.findByCid(req.getParameter("cid")));
		return "f:/jsps/book/list.jsp";
		
	}
	public String  findByBid(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("book",bookService.findByBid(req.getParameter("bid")) );
		return "f:/jsps/book/desc.jsp";
		
	}
	
}
