package com.scheduler.dataprovider;

import java.sql.*;

 
class DbConnect {
    Connection conn;
    final String url = "jdbc:mysql://localhost:3306/Project4";
    final String user = "root";
    final String password = "password";
 
    DbConnect() {
        try {
        	conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
        	
        }
    }
    
    public ResultSet executeSQL(String sql) {
    	ResultSet rs;
    	rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			
		}
    	return rs;
    }
}    
