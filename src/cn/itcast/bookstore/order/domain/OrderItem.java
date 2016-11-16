package cn.itcast.bookstore.order.domain;

import cn.itcast.bookstore.book.domain.Book;

public class OrderItem {
	private String iid;
	private double subtotal;//小计
	private int count;
	private Order order ;//所属订单
	private Book book;//所要购买的图书
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	@Override
	public String toString() {
		return "OrderItem [iid=" + iid + ", subtotal=" + subtotal + ", count="
				+ count + ", order=" + order + ", book=" + book + "]";
	}
	
	
}
