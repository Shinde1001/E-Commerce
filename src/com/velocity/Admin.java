package com.velocity;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.Scanner;



public class Admin implements DataBase {
	Connection connection = null;
	PreparedStatement ps = null;
	Formatter formatter = new Formatter();
	Scanner sc = new Scanner(System.in);
	public void MainMenu() {
		System.out.println("\n\t\t Welcome Admin ");
		System.out.println("\t\t Enter Your Choice ");
		System.out.println(" 1.View Product List \t 2.View User List \t 3.User History ");
		int ch = sc.nextInt();
		switch (ch) {
		case 1:viewProductList();
			
			break;
        case 2:getUserList();
			
			break;
        case 3:getHistory();
		
		break;	
		default:
			break;
		}
	}
	
	
	public void getUserList() {
		
		connection = getConnection();
		   try {
			ps = connection.prepareStatement("Select * from user");
			ResultSet rs = ps.executeQuery();
			System.out.println("\nUser Id \t User Name \t      Contact \t       User City ");
			while(rs.next()) {
				
				System.out.println(rs.getInt(1)+"\t\t  "+rs.getString(2)+"\t\t  "+rs.getLong(3)+"\t\t  "+rs.getString(4));
			}
		   }
		   catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	
	public void getHistory() {
			getUserList();
			System.out.print("Please Enter The Name And Surname Of User To Display History:: ");
			System.out.println("Name::");
			String fn = sc.next();
			System.out.println("Surname::");
			String ln = sc.next();
			String st1 = fn+" "+ln;		 
			String st = "select * from history where Uname="+"'"+st1+"'";
			connection = getConnection();
			try {
				ps = connection.prepareStatement(st);
				ResultSet rs = ps.executeQuery();
				System.out.println("   id   \t Pname    \t                      Ptotal   \t      Date");
				while(rs.next()) {
				System.out.println( rs.getInt(1)+"      "+rs.getString(3)+"             \t\t"+rs.getDouble(4)+"      "+rs.getString(5));	
				}
			} catch (SQLException e) {
				 
				e.printStackTrace();
			}
			
	}
	
	
	@Override
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","root");
		
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return connection;
	}



	
	@Override
	public void viewProductList() {
		connection = getConnection();
		   try {
			ps = connection.prepareStatement("Select * from product");
			ResultSet rs = ps.executeQuery();
			System.out.println("Product Id \t Product Name \t Product Description \tProduct Price \tProduct Quantity ");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}









}
