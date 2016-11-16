package cn.itcast.bookstore.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;






import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.domain.OrderItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr=new TxQueryRunner();
	/*
	 * 添加订单
	 */
	public void add(Order order){
		try{
			String sql="insert into orders values(?,?,?,?,?,?)";
			/*
			 * 时间格式转换
			 */
			Timestamp timestamp=new Timestamp(order.getOrdertime().getTime());
			Object[] params={order.getOid(),timestamp,order.getTotal(),
					order.getState(),order.getOwner().getUid(),order.getAddress()};
			qr.update(sql,params);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/*
	 * 插入条目
	 */
	public void addOrderItemList(List<OrderItem> list){
		try{
			String sql="insert into orderitem values(?,?,?,?,?)";
			Object[][]params=new Object[list.size()][];
			for(int i=0;i<list.size();i++){
				OrderItem item=list.get(i);
				params[i]=new Object[]{
						item.getIid(),item.getCount(),item.getSubtotal(),
						item.getOrder().getOid(),item.getBook().getBid()
				};
			}
			qr.batch(sql, params);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/*
	 * 根据id来查询订单,得到订单的集合
	 */
	public List<Order> findByUid(String uid) {
		try{
			String sql="select * from orders where uid=?";
			List<Order> orderList=qr.query(sql, new BeanListHandler<Order>(Order.class),uid);

			/*
			 *  循环遍历每个Order，为其加载它自己所有的订单条目
			 */
			for(Order order:orderList){
				loadOrderItems(order);
				//System.out.println("=======每个order是"+order);
			}
			return orderList;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	/*
	 * 
	 */
	private void loadOrderItems(Order order) throws SQLException {
		String sql="select * from orderitem i,book b where i.bid=b.bid and oid=?";
		List<Map<String,Object>>mapList=qr.query(sql, new MapListHandler(),order.getOid());
		//加载map集合,返回一个订单条目集合
		//System.out.println("========"+mapList.toString());
		List<OrderItem>orderItemList=toOrderItemList(mapList);
		order.setOrderItem(orderItemList);
	}
	/**
	 * 
	 * 
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList=new ArrayList<OrderItem>();
		//遍历map集合,根据map得到订单条目
		for(Map<String,Object> map:mapList){
			OrderItem item=toOrderItem(map);
			
			orderItemList.add(item);
			//System.out.println("orderItemList"+orderItemList.toString());
		}
		return orderItemList;
	}
	/**
	 * 
	 * 
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		
		
		orderItem.setBook(book);
		return orderItem;
	}
	public Order load(String oid) {
		try{
			String sql="select * from orders where oid=?";
			
			Order order=qr.query(sql, new BeanHandler<Order>(Order.class),oid);
			loadOrderItems(order);
			return order;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public int getStateByOid(String oid){
		try{
			String sql="select state from orders where oid=?";
			return (Integer) qr.query(sql, new ScalarHandler(),oid);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}	
	
	public void updateState(String oid,int state){
		try{
			String sql="update orders set state=? where oid=?";
			qr.update(sql);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
}
