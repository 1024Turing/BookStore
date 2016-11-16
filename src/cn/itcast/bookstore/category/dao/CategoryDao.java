package cn.itcast.bookstore.category.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	QueryRunner qr=new TxQueryRunner();

	public List<Category> findAll() {
		try{
			String sql="SELECT * FROM category";
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

	public void delete(String cid) {
		try{
			String sql="delete FROM category where cid=?";
			qr.update(sql,cid);
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

	public void add(Category category) {
		try{
			String sql="insert into category values(?,?)";
			Object[] params={category.getCid(),category.getCname()};
			qr.update(sql, params);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

	public Category findByCid(String parameter) {
		try{
			String sql="SELECT * FROM category where cid=?";
			return qr.query(sql, new BeanHandler<Category>(Category.class),parameter);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public void modify(Category form) {
		try{
			String sql="update category set cname=? where cid=?";
			qr.update(sql, form.getCname(),form.getCid());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
