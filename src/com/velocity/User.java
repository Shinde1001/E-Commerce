package com.velocity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class User implements DataBase {
	 Connection con = null;
	 PreparedStatement ps = null;
	 Scanner sc = new Scanner(System.in);  
     static String nm;
	 static TreeMap<Integer, Integer> arr;
	 
   public void userMainMenu() {
	 
	 System.out.println("\t\t Wellcome ");
	 System.out.println(" \nEnter Your Choice ");
	 System.out.println(" 1. Registration\t 2.Login");
	 int ch = sc.nextInt();
	  
	 switch (ch) {
	    case 1:userRegistration();

	    	break;
	    case 2:userLogin();

			break;
	
	    default:
	    	break;
	   }
       }
 
 
   public void userRegistration() {
	  
	  long mobilenum;
	  System.out.println("\t\t Wellcome To Registraction");
	  
	  System.out.println("Please Enter Your Name::");
	  String str = sc.next();
	  System.out.println("Please Enter Your Surname::");
	   String nm =str+" "+sc.next();
	  System.out.println("Please Enter City::");
	   String ct = sc.next();
	  System.out.println("Please Enter Your Mobile Number::");
	  mobilenum = sc.nextLong(); 
	  
	  if(!getLoginDetail().containsKey(mobilenum)||getLoginDetail().isEmpty()) {
		  System.out.println(" Enter Password::");
		  int pwd = sc.nextInt();
		  getLoginDetail().put(mobilenum, pwd);//map
		  userDb(nm, ct, mobilenum,pwd);
		  System.out.println("Please Login ");
		  
		 }
	  else {
		  System.out.println("Allredy Registared");
		  System.out.println("Do You Wants to Login Press \t 1.Yes \t 2.No");
		  int a = sc.nextInt();
		  if(a==1) {
			  userLogin();
		  }
	  }
	  
	 }
  
   
   public void userDb(String nm,String ct,long ph,int pwd) {
	  
	   try {
		   con = getConnection();
		   ps = con.prepareStatement("insert into user(Id, Name, City, Phone,pasword)values(?,?,?,?,?)");
		   ps.setInt(1, 0);
		   ps.setString(2, nm);
		   ps.setString(3, ct);
		   ps.setLong(4, ph);
		   ps.setInt(5, pwd);
		   int i = ps.executeUpdate();
		   System.out.println(" You Have Registered Succefully " +i);
		   
	   
	   } catch (Exception e) {
		 e.printStackTrace();
		 }
	   System.out.println("Press 1 For Login ");
	   int i = sc.nextInt();
	   if(i==1) {
		   userMainMenu();
	   }
	   else {
		   System.out.println("You Entered Wrong Input");
		   userMainMenu();
	   }
   }
   
   
   public LinkedHashMap<Long,Integer> getLoginDetail() {
	   con = getConnection();
	   LinkedHashMap<Long, Integer> detail = new LinkedHashMap<Long,Integer>();
	   try {
		ps = con.prepareStatement("Select  Phone, pasword from user");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
		detail.put(rs.getLong(1),rs.getInt(2));
		}
	   }
		 catch (Exception e) {
			 e.printStackTrace();
		} 
	   return detail; 
   }
   
   
   public  void userLogin() {
    		System.out.print("\tEnter Your Mobile Number::");
    		long mob = sc.nextLong();
    		System.out.print("\tEnter Your Password::");
    		int pwd = sc.nextInt();
    		if(getLoginDetail().containsValue(pwd)==getLoginDetail().containsKey(mob)) {
    			 nm = getUserDtail().get(mob);
    			System.out.println("\n\t ****Welcome**** \t **"+ nm +"****** \t ****Welcome****");
    			loginMainMenu();	
    		}
    		else {
    			System.out.println("\n\tYou Entered Wrong Details ");
    			System.out.println("\t Please Try Again");
    			System.out.println("\t Press 1. Registration  \t 2.Main Menu ");
    			int ch = sc.nextInt();
    			if(ch==1) {
    				userRegistration();
    			}
    			else {
    				userMainMenu();
    			}
    		}
      }
   
   
   public void loginMainMenu() {
	   
	   System.out.println("\t Enter Your Choice ");
	   System.out.println("1.Product List");
	   System.out.println("2.View Your Cart ");
	   System.out.println("3.Bill Details");
	   int ch = sc.nextInt();
	   			switch (ch) {
	   							case 1:selectProduct();
	   									
	   								break;
	   							case 2:viewCart();
	   								
	   								break;
	   							case 3:
	   								
	   								break;

	   							default:
	   								break;
	}
	   
   }
   
   
   public void viewCart() {
	   	   Set<Integer> s = arr.keySet();//Pid & quantity 101,102
	   	   System.out.println("\t\t Selected Product Detail");
	   	   System.out.println("  \t Name \t     Price \t  Quantity \t  C-GST \t S-GST \t  Total Price ");
	   	   int sum=1;
	   	   String st1 = "";
	   	   double gradTotal = 0;
	   	   for(int x:s) {
		   String st = getProductDetail().get(x);   
		   st1 = st1+","+st;
		   String str = "select * from product where pname ="+"'"+st+"'";
		   try {
			con = getConnection();
			ps = con.prepareStatement(str);
			ResultSet rs =ps.executeQuery(str);
			
             while(rs.next()) {
            	 sum = (rs.getInt(4)*arr.get(x));
            	 double total = (sum*0.18)+sum;
            	 gradTotal = gradTotal +total;
            	 System.out.println("\n"+"\t"+st+"\t"+"\t"+rs.getInt(4)+"\t         "+arr.get(x)+"\t        9%   \t        9%   \t     "+total);
            	
		}
		   }
		   catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	   }
	   	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");  
		   LocalDateTime now = LocalDateTime.now();  
		   String str = dtf.format(now);  
	   	System.out.println("Press 1----> Billing ");   
	   	int i = sc.nextInt();
	   	if(i==1) {
	   		
	   		viewHistory(nm ,st1,  gradTotal,str);
	   		System.out.println("\n Order Placed Succesfully Thank you");
	   		System.out.println("-------------------VISIT AGAIN-------------------- ");
	   	}
	   	else {
	   		System.out.println("You Enterd Wrong Option");
	   		
	   	}
	   
   }
   
   
   public  void selectProduct() {
	  int ch;
	  viewProductList();
	 TreeMap<Integer, Integer> a = new TreeMap<Integer,Integer>();
	  System.out.println("\t Please Add Product To Cart ");
		
		 do{
			 System.out.print("Enter Product Id ::");
			 int s = sc.nextInt();
			 if(s>100) {
				 System.out.print("Enter Quntity:: ");
			 int q = sc.nextInt();
			 a.put(s, q);
			 System.out.println("Add More   Yes----> 1   No----> 0");
			 ch = sc.nextInt();
			 }
			 else {
				 System.out.println("Invalid Id ");
				 System.out.println(" Please Select Valid Id Press 1----> Yes   0---->No");
				 ch = sc.nextInt();
			 }
		 }
		 while(ch!=0);
		 System.out.println("-------------------------------------------------------------------------------");
		 System.out.println("Selected Product List");
		 System.out.println("\n-------------------------------------------------------------------------------");
		 System.out.println("\n Product Id\t     Product Name\t    Product Quintity");
	     arr = a;
	     Set<Integer> s = arr.keySet();
	     for (Integer i : s) {
	    	 System.out.print("    "+i+"       ");
	    	 String st = getProductDetail().get(i);
	    	 System.out.print("       "+st+"            ");
	    	 System.out.println("        "+arr.get(i));
	    	 
		}
	     System.out.println(" Products Added Sucessfully To The Cart");
			System.out.println("Press 1.Cart 2.Previous Menu ");
			int n = sc.nextInt();
			if(n==1) {
				viewCart();
			         }
			else {
				loginMainMenu();
			     }  
	     
	  }
   
   
   public LinkedHashMap<Integer,String> getProductDetail() {
	   con = getConnection();
	   LinkedHashMap<Integer, String> product = new LinkedHashMap<Integer,String>();
	   try {
		ps = con.prepareStatement("Select  Pid, Pname from product");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
		product.put(rs.getInt(1),rs.getString(2));
		}
	   }
		 catch (Exception e) {
			 e.printStackTrace();
		} 
	   return product; 
  }
   
   
   public void viewHistory(String unm,String pn,double gt,String dt ) {
		  
	   try {
		   con = getConnection();
		   ps = con.prepareStatement("insert into history(id, Uname, Pname , Ptotal,Date)values(?,?,?,?,?)");
		   ps.setInt(1, 0000);
		   ps.setString(2, unm);
		   ps.setString(3,pn);
		   ps.setDouble(4, gt);
		   ps.setString(5, dt);
		   ps.executeUpdate();
	   
	   } catch (Exception e) {
		 e.printStackTrace();
	}
   }
       
  
   public LinkedHashMap<Long, String> getUserDtail(){
	   con = getConnection();
	    LinkedHashMap<Long, String> user = new LinkedHashMap<Long,String>();
	   try {
		ps = con.prepareStatement("SELECT Phone , Name FROM user");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
		  user.put(rs.getLong(1),rs.getString(2));
		}
	   }
		 catch (Exception e) {
			 e.printStackTrace();
		} 
	  return user;
   }
   
   
   @Override
   public void viewProductList() {
    	   con = getConnection();
		   try {
			ps = con.prepareStatement("Select * from product");
			ResultSet rs = ps.executeQuery();
			System.out.println("Product Id \t Product Name \t Product Description \tProduct Price \tProduct Quantity ");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5));
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
  
}

