package cn.itcast.bookstore.book.web.admin.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class AdminBookServlet extends BaseServlet {
	private BookService bookService=new BookService();
	private CategoryService categoryService=new CategoryService();

	public String addPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("categoryList", categoryService.findAll());
		
		return "f:/adminjsps/admin/book/add.jsp";
	}
	public String mod(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Book book=CommonUtils.toBean(req.getParameterMap(), Book.class);
		bookService.mod(book);
		findAll(req,resp);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	public String del(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		bookService.delete(req.getParameter("bid"));
		findAll(req,resp);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("bookList", bookService.findAll());
		return "f:/adminjsps/admin/book/list.jsp";
	}
	public String findByBid(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Book book= bookService.findByBid(req.getParameter("bid"));
		Category category=(Category) categoryService.findByCid(book.getCategory().getCid());
		req.setAttribute("book", book);
		req.setAttribute("category", category);
		System.out.println(bookService.findByBid(req.getParameter("bid")));
	
		return "f:/adminjsps/admin/book/desc.jsp";
	}
}
