package cn.itcast.bookstore.category.service;

public class CategoryException extends Exception {

	public CategoryException() {
		
	}

	public CategoryException(String message) {
		super(message);
		
	}

	public CategoryException(Throwable cause) {
		super(cause);
		
	}

	public CategoryException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
