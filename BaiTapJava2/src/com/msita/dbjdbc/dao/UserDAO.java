package com.msita.dbjdbc.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.msita.dbjdbc.bo.User;
import com.msita.dbjdbc.utils.ConnectionFactory;

public class UserDAO {
	public ArrayList<User> findUserByUsername(String username){
		return new ArrayList<User>();
	}
	public ArrayList<User> getAllUser(){
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<User> userList = new ArrayList<User>();
		
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM student");
			while (resultSet.next()) {
				userList.add(convertToUser(resultSet));
			}
		} catch (SQLException e) {
			//Handle errors for JDBC
			e.printStackTrace();
		} finally {
			//finally block used to close resources
	        if (resultSet != null) {
                try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (statement != null) {
            	try {
            		statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (connection != null) {
            	try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
		}
		
		return userList;
	}

	public ArrayList<User> getAllUser1(String username){
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<User> userList = new ArrayList<User>();

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			String sql = "SELECT * FROM student where username = '"+username+"'";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				userList.add(convertToUser(resultSet));
			}
		} catch (SQLException e) {
			//Handle errors for JDBC
			e.printStackTrace();
		} finally {
			//finally block used to close resources
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return userList;
	}


	public void addUser(User user){
		Connection connection = null;
		Statement statement = null;
		String insertSQL = "INSERT INTO student (username,password,age) VALUES ('"+ user.getUserName()+ "','"+ user.getPassword() + "','"+ user.getAge() + "')";
		System.out.println(insertSQL);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(insertSQL);
		} catch (SQLException e) {
			//Handle errors for JDBC
			e.printStackTrace();
		} finally {
			//finally block used to close resources
			if (statement != null) {
            	try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
            if (connection != null) {
            	try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
		}
	}
	public void update(int id,String newPassword){
		Connection connection = null;
		Statement statement = null;
		String insertSQL = "UPDATE student SET password ="+newPassword+" WHERE id="+id+"";
		System.out.println(insertSQL);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(insertSQL);
		} catch (SQLException e) {
			//Handle errors for JDBC
			e.printStackTrace();
		} finally {
			//finally block used to close resources
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void addUserViaPreparedStatement(User user){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO student (username,password,age) VALUES (?,?,?)";
		try {
			connection = ConnectionFactory.getConnection();	
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setInt(3, user.getAge());
			preparedStatement.executeUpdate();
	        
		} catch (SQLException e) {
			//Handle errors for JDBC
			e.printStackTrace();
		} finally {
			//finally block used to close resources
            if (preparedStatement != null) {
            	try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (connection != null) {
            	try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
		}
	}
	
	
	public User addUserViaPreparedStatementReturnId(User user){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String insertSQL = "INSERT INTO student (username,password,age) VALUES (?,?,?)";
		String[] returnId = { "ID" };
		try {
			connection = ConnectionFactory.getConnection();	
			preparedStatement = connection.prepareStatement(insertSQL, returnId);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setInt(3, user.getAge());
			preparedStatement.executeUpdate();
			
	        resultSet = preparedStatement.getGeneratedKeys();
	        
	        if (resultSet.next()) {
				user.setId(resultSet.getInt(1));
			}
	        
		} catch (SQLException e) {
			//Handle errors for JDBC
			e.printStackTrace();
		} finally {
			//finally block used to close resources
	        if (resultSet != null) {
                try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (preparedStatement != null) {
            	try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (connection != null) {
            	try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
		}
		return user;
	}
	public boolean deleteUserList(ArrayList<User> userList) {
		Connection connection = null;
		Statement statement = null;
		boolean result = true;
		try {
			String idList = generateIdList(userList);
			String sql = "DELETE FROM user WHERE id IN " + idList;
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			//Handle errors for JDBC
			e.printStackTrace();
			result = false;
		} finally {
			//finally block used to close resources
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private String generateIdList(ArrayList<User> userList) {
		String prefix = "(";
		String suffix = ")";
		String idList = "";
		for (User user : userList) {
			idList = idList + user.getId() + ",";
		}
		idList = idList.substring(0, idList.length() - 1);
		idList = prefix + idList + suffix;
		return idList;
	}

	private static User convertToUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(1));		
		user.setUserName(rs.getString(2));		
		user.setPassword(rs.getString(3));		
		user.setAge(rs.getInt(4));		
		return user;
	}
}
