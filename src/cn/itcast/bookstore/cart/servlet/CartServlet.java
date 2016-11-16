package cn.itcast.bookstore.cart.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.cart.domain.CartItem;
import cn.itcast.servlet.BaseServlet;

public class CartServlet extends BaseServlet {
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 得到车
		 */
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		String bid=req.getParameter("bid");
		Book book=new BookService().findByBid(bid);
		int count=Integer.parseInt(req.getParameter("count"));
		CartItem cartItem=new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		/*
		 * 添加条目
		 */
		cart.add(cartItem);
		
		return "f:/jsps/cart/list.jsp";
	}
	
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		String bid=req.getParameter("bid");
		cart.delete(bid);
		return "f:/jsps/cart/list.jsp";
	}
	public String clear(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		cart.clear();
		return "f:/jsps/cart/list.jsp";
		
		
	}
}
