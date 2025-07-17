package com.company.user.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.company.user.bean.*;



public class UserDao {

	private static final String URL="jdbc:mysql://localhost:3306/mydb";
	private static final String USERNAME="root";
	private static final String PASSWORD="admin";
	//private static final String driver= "com.mysql.jdbc.Driver";
	
	
	private static final String INSERT_USERS_SQL = "INSERT INTO routine" + "  (title, status, date) VALUES " + " (?, ?, ?);";
	private static final String SELECT_USER_BY_ID = "select id,title, status, date from routine where id =?";
	private static final String SELECT_ALL_USERS = "select * from routine";
	private static final String DELETE_USERS_SQL = "delete from routine where id = ?;";
	private static final String UPDATE_USERS_SQL = "update routine set title=?, status=?, date=? where id = ?;";
	
	public UserDao() {
		}
	
	protected Connection getConnection()
	{
		Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
	}
	
	
	//insert list
	public void insertList(User user) throws SQLException
	{
		System.out.println(INSERT_USERS_SQL);
		try(Connection connection=getConnection();
				PreparedStatement prep=connection.prepareStatement(INSERT_USERS_SQL)){
			prep.setString(1,user.getTitle());
			prep.setString(2, user.getStatus());
			prep.setString(3, user.getDate());
			
			System.out.println(prep);
			prep.executeUpdate();
			
		}catch (SQLException e) {
			printSQLException (e);
		}
	}

	
	
	//select list by id
	public User selectUser(int id) {
		User user = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String title = rs.getString("title");
				String status = rs.getString("status");
				String date = rs.getString("date");
				user = new User(id, title, status, date);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}
	
	
	//select all list
	public List<User> selectAllUsers() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<User> users = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String status = rs.getString("status");
				String date = rs.getString("date");
				users.add(new User(id, title, status, date));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;
	}
	
	
	//delete list
	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	
	
	//update list
	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
			System.out.println("updated list:"+statement);
			statement.setString(1, user.getTitle());
			statement.setString(2, user.getStatus());
			statement.setString(3, user.getDate());
			statement.setInt(4, user.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	
	
	
	//printSQLException method
	private void printSQLException(SQLException ex) {
		// TODO Auto-generated method stub
		
		for(Throwable e:ex)
		{
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	
		
}

