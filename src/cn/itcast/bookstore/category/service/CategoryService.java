package cn.itcast.bookstore.category.service;

import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.category.dao.CategoryDao;
import cn.itcast.bookstore.category.domain.Category;

public class CategoryService {
	private CategoryDao categoryDao=new CategoryDao();
	private BookDao bookDao=new BookDao();
	public List<Category> findAll() {
		return categoryDao.findAll();
		
	}

	public void delete(String cid) throws CategoryException {
		List<Book> bookList=bookDao.findByCid(cid);
		if(bookList!=null)throw new CategoryException("该类下面有书籍,不能删");
		categoryDao. delete(cid);
		
	}

	public void add(Category category) {
		categoryDao.add( category);
		
	}

	public Category findByCid(String parameter) {
		
		return categoryDao.findByCid(parameter);
	}

	public void modify(Category form) {
		 categoryDao.modify(form);
	}
	
}
