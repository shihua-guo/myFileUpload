package com.briup.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEMP_FOLDER = "../../file";
	private static final String PATH_FOLDER = "../../file";
    /**
     * Default constructor. 
     */
    public Upload() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 设置编码  
        response.setCharacterEncoding("utf-8");  
        response.setContentType("text/html;charset=UTF-8");  
        // 获得磁盘文件条目工厂  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
          
        // 如果没以下两行设置的话，上传大的文件会占用很多内存，  
        // 设置暂时存放的存储室 ,这个存储室,可以和最终存储文件的目录不同  
        /** 
         * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tmp 
         * 格式的 然后再将其真正写到 对应目录的硬盘上 
         */  
        factory.setRepository(new File(TEMP_FOLDER));  
        // 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室  
        factory.setSizeThreshold(1024 * 1024);  
  
        // 高水平的API文件上传处理  
        ServletFileUpload upload = new ServletFileUpload(factory);  
          
        try {  
            // 提交上来的信息都在这个list里面  
            // 这意味着可以上传多个文件  
            // 请自行组织代码  
            List<FileItem> list = upload.parseRequest(request);  
            // 获取上传的文件  
            FileItem item = getUploadFileItem(list);  
            // 获取文件名  
            String filename = getUploadFileName(item);  
  
            System.out.println("存放目录:" + PATH_FOLDER);  
            System.out.println("文件名:" + filename);  
  
            // 真正写到磁盘上  
            item.write(new File(PATH_FOLDER, filename)); // 第三方提供的  
                                // 输出信息,前端页面获取,这里用的json格式             
            PrintWriter writer = response.getWriter();  
              
            writer.print("{");  
            writer.print("msg:\"文件大小:"+item.getSize()+",文件名:"+filename+"\"");  
            writer.print("}");  
              
            writer.close();  
          
        } catch (FileUploadException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}

	private String getUploadFileName(FileItem item) {
		// TODO Auto-generated method stub
		return item.getFieldName();
	}

	private FileItem getUploadFileItem(List<FileItem> list) {
		for(FileItem fi:list){
			System.out.println(fi.getFieldName());
			
		}
		// TODO Auto-generated method stub
		return list.get(0);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}
