package cn.itcast.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

public class Cart {
	/*
	 * 购物车为了方便查询,使用Map
	 * 用图书的id做键,条目做值
	 * 
	 */
	private Map<String,CartItem> map=new LinkedMap();//LinkedMap便于查询
	/*
	 * 合计=所有条目小计之和
	 * 处理了二进制的运算问题
	 */
	public double getTotal(){
		BigDecimal total=new BigDecimal("0");
		for(CartItem cartItem: map.values()){
			BigDecimal subTotal=new BigDecimal(""+cartItem.getSubtotal());
			total=total.add(subTotal);
		}
		return total.doubleValue();
	}
	/*
	 * 添加指定条目
	 * A:如果购物车里有,就合并条目的数量
	 * B:如果购物车么有,就添加到够物车
	 */
	public void add(CartItem cartItem){
		if(map.containsKey(cartItem.getBook().getBid())){
			CartItem _cartItem=map.get(cartItem.getBook().getBid());//返回原条目
			_cartItem.setCount(cartItem.getCount()+_cartItem.getCount());//设置原条目的数量
			map.put(cartItem.getBook().getBid(), _cartItem);
		}else{
			map.put(cartItem.getBook().getBid(), cartItem);
		}
	}
	/*
	 * 删除指定条目
	 */
	public void delete(String bid){
		map.remove(bid);
	}
	/*
	 * 清空购物车
	 */
	public void clear(){
		map.clear();
	}
	/*
	 * 获取所有条目,根据值来获取键
	 */
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
}
