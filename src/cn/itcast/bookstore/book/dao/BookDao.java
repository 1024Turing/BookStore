package cn.itcast.bookstore.book.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr=new TxQueryRunner();

	public List<Book> findAll() {
		try{
			String sql="SELECT * FROM book where del=false";
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	
	}

	public List<Book> findByCid(String cid) {
		try{
			String sql="SELECT * FROM book where cid=? and del=false";
			return qr.query(sql, new BeanListHandler<Book>(Book.class),cid);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	
	}

	public Book findByBid(String bid) {
		
		try{
			String sql="SELECT * FROM book where bid=?";
			Map<String,Object>map= qr.query(sql, new MapHandler(),bid);
			Category category=CommonUtils.toBean(map, Category.class);
			Book book=CommonUtils.toBean(map, Book.class);
			book.setCategory(category);
			return book;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public void delete(String bid) {
		try{
			String sql="update book set del=true where bid= ?";
			qr.update(sql,bid);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public void mod(Book book) {
		try{
			String sql="update book set bname=?,price=?,author=? where bid= ?";
			qr.update(sql,book.getBname(),book.getPrice(),book.getAuthor(),book.getBid());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

	public void add(Book book) {
		try{
			String sql="insert into book values(?,?,?,?,?,?,?)";
			Object[]params={book.getBid(),book.getBname(),book.getPrice(),book.getAuthor()
					,book.getImage(),book.getCategory().getCid(),0};
			qr.update(sql,params);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		
	}
	
}
