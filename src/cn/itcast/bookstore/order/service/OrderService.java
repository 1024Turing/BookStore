package cn.itcast.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.bookstore.order.dao.OrderDao;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	private OrderDao orderDao=new OrderDao();
	public void add(Order order){
		try{
			JdbcUtils.beginTransaction();
			orderDao.add(order);
			orderDao.addOrderItemList(order.getOrderItem());
			JdbcUtils.commitTransaction();
		}catch(Exception e){
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
			
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}
	
	public List<Order> findByUid(String uid) {
		
		return orderDao.findByUid(uid);
	}

	public Order load(String oid) {
		
		return orderDao.load(oid);
	}
	
	public void confirm(String oid)throws OrderException{
		int state=orderDao.getStateByOid(oid);
		System.out.println(state);
		if(state!=3)throw new OrderException("订单状态不是3");
		
		orderDao.updateState(oid, 4);
	}
}
