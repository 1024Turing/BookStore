package cn.itcast.bookstore.category.web.admin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.service.CategoryException;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class AdminCategoryServlet extends BaseServlet {
	CategoryService categoryService=new CategoryService();
	public String modify(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Category form=CommonUtils.toBean(req.getParameterMap(), Category.class);
		form.setCid(req.getParameter("cid"));
	
		categoryService.modify(form);
		
		return findAll(req,resp);
	}
	public String modifyPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("category", categoryService.findByCid(req.getParameter("cid")));
		return "f:/adminjsps/admin/category/mod.jsp";
	}
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Category> categoryList=categoryService.findAll();
		req.setAttribute("categoryList",categoryList);
		
		
		return "f:/adminjsps/admin/category/list.jsp";
	}
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			categoryService.delete(req.getParameter("cid"));
			return findAll(req,resp);
		} catch (CategoryException e) {
			req.setAttribute("msg", e.getMessage());
			return "f:/adminjsps/msg.jsp";
			
		}
		
	}
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Category category=CommonUtils.toBean(req.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());
		categoryService.add(category);
		return findAll(req,resp);
	}
}
