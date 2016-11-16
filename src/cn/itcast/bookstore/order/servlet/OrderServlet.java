package cn.itcast.bookstore.order.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.cart.domain.CartItem;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.domain.OrderItem;
import cn.itcast.bookstore.order.service.OrderException;
import cn.itcast.bookstore.order.service.OrderService;
import cn.itcast.bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class OrderServlet extends BaseServlet {
	private OrderService orderService=new OrderService();
	public String confirm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid=req.getParameter("oid");
		try {
			orderService.confirm(oid);
			req.setAttribute("msg", "交易成功");
		} catch (OrderException e) {
			req.setAttribute("msg", e.getMessage());

		}
		return "f:/jsps/msg.jsp";
	}
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("order",orderService.load(req.getParameter("oid")) );
		return "f:/jsps/order/desc.jsp";
	}
	
	public String myOrders(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user=(User) req.getSession().getAttribute("session_user");
		List<Order> orderList=orderService.findByUid(user.getUid());
		req.setAttribute("orderList", orderList);
//		System.out.println("=================="+orderList.toString());
		return "f:/jsps/order/list.jsp";
	}
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Cart cart=(Cart)req.getSession().getAttribute("cart");
	
		
		
		Order order=new Order();
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(new Date());
		order.setState(1);
		User user=(User) req.getSession().getAttribute("session_user");
		order.setOwner(user);
		order.setTotal(cart.getTotal());
		/*
		 * 设置订单条目
		 * 
		 */
		List<OrderItem> orderItemList=new ArrayList<OrderItem>();
		for(CartItem cartItem :cart.getCartItems()){
			OrderItem oi=new OrderItem();
			oi.setIid(CommonUtils.uuid());
			oi.setCount(cartItem.getCount());
			oi.setSubtotal(cartItem.getSubtotal());
			oi.setBook(cartItem.getBook());
			oi.setOrder(order);
			orderItemList.add(oi);
		}
		order.setOrderItem(orderItemList);//把订单条目添加到订单中
		//cart.clear();//清空购物车
		
		orderService.add(order);
		req.setAttribute("order", order);
		return"f:/jsps/order/desc.jsp";
	}
}
