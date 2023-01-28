package com.velocity;

import java.sql.Connection;
 

public interface DataBase {
	public default  Connection getConnection() {
		return null;
 	}
	
	
	public default void viewProductList() {
		
	}
}
