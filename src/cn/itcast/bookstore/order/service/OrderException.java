package cn.itcast.bookstore.order.service;

public class OrderException extends Exception {

	public OrderException() {
		
	}

	public OrderException(String message) {
		super(message);
		
	}

	public OrderException(Throwable cause) {
		super(cause);
		
	}

	public OrderException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
