package com.company.user.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.company.user.bean.User;
import com.company.user.dao.UserDao;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(urlPatterns = { "/", "/list", "/new", "/insert", "/edit", "/update", "/delete" })

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao;
    

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		userDao=new UserDao();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getServletPath();
		try{switch(action)
		{
		case "/new":
			showNew(request, response);
			break;
		case "/insert":
			insertList(request, response);
			break;
		case "/delete":
			deleteUser(request, response);
			break;
		case "/edit":
			editList(request, response);
			break;
		case "/update":
			updateUser(request, response);
			break;
		default:
			listUser(request,response);
			break;
			
		}
		}catch(SQLException ex)
		{
			throw new ServletException(ex);
		}
		
	}
	
	private void showNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		RequestDispatcher dispatcher=request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
		// TODO Auto-generated method stub
		
	} 
	//insert list
	private void insertList(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		String title=request.getParameter("title");
		String status=request.getParameter("status");
		String date=request.getParameter("date");
		User user=new User(title,status,date);
		userDao.insertList(user);
		response.sendRedirect("list");
	}
	
	//update list
	private void updateUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String status = request.getParameter("status");
		String date = request.getParameter("date");

		User user = new User(id, title, status, date);
		userDao.updateUser(user);
		response.sendRedirect("list");
	}
	
	
	//delete list
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try{
			userDao.deleteUser(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("list");

	}	
	
	
	//edit list
	private void editList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser;
		try {
			existingUser= userDao.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	//default it shows all users
	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		try{
			List<User> listUser = userDao.selectAllUsers();
			request.setAttribute("listUser", listUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
			dispatcher.forward(request, response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	} 

}
