package com.velocity;

import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		User user = new User();
		Admin admin = new Admin();
		System.out.println(" \t\t\tWellcome Into Shopping ");
		System.out.println("\n Enter The Choice ");
		System.out.println("\n 1. User \t 2.Admin \t 3.Exit");
		int ch = sc.nextInt();
		switch(ch) 
		  {
		        case 1:user.userMainMenu();
		        	break;
		        case 2:admin.MainMenu();
		        	
		        default:
		}
		sc.close();
	}
}
