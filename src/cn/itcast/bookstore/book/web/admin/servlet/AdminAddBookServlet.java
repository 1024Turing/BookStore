package cn.itcast.bookstore.book.web.admin.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;

public class AdminAddBookServlet extends HttpServlet {

	private BookService bookService=new BookService();
	private CategoryService categoryService=new CategoryService();
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

	response.setContentType("text/html;charset=utf-8");
	PrintWriter out = response.getWriter();
	
	DiskFileItemFactory factory=new DiskFileItemFactory(15*1024,new File("f:"));
	ServletFileUpload sfu=new ServletFileUpload(factory);
	
	try{
		List<FileItem> fileItemList=sfu.parseRequest(request);
		Map<String,String> map=new HashMap<String, String>();
		for(FileItem fileItem : fileItemList){
			if(fileItem.isFormField()){
				map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
			}
		}
		Book book=(Book)CommonUtils.toBean(map, Book.class);
	
		book.setBid(CommonUtils.uuid());
		String savePath=this.getServletContext().getRealPath("/book_img");
	
		String saveName=CommonUtils.uuid()+"_"+fileItemList.get(1).getName();
		if(!saveName.toLowerCase().endsWith("jpg")){
			request.setAttribute("msg", "你上传的图片不是jpg格式");
			request.setAttribute("categoryList", categoryService.findAll());
		}
		File file=new File(savePath,saveName);
		File file2=new File("D:\\workspace2\\BookStore\\WebRoot\\book_img",saveName);
		fileItemList.get(1).write(file);
		fileItemList.get(1).write(file2);
		book.setImage("book_img/"+saveName);
		Category category=CommonUtils.toBean(map, Category.class);
		book.setCategory(category);
		

		bookService.add(book);
		request.getRequestDispatcher("/admin/AdminBookServlet?method=findAll").forward(request, response);
	}catch(Exception e){
		throw new RuntimeException(e);
	}
}


}


