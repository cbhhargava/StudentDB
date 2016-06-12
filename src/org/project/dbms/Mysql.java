
package org.project.dbms;

//import java.lang.*;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;

//import com.mysql.jdbc.Connection;
//import java.sql.PrepareStatement;
import java.sql.*;
import java.io.*;

import javax.servlet.*;



@WebServlet(urlPatterns ={"/Mysql"})
public class Mysql extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;
	Statement stmt;
	ResultSet result;
	 
	public String message;
	String name;
	String roll;
	 
	public void init() throws ServletException{
	  message = "Hello World";
	}
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException{

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
	  
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/shub","root","root");
			stmt=conn.createStatement();
			String btn=req.getParameter("btn");
			roll=req.getParameter("roll");
			name=req.getParameter("name");
	    	if(btn.equals("insert")){
	    		try{
	    		PreparedStatement statement=conn.prepareStatement("INSERT INTO stu_details VALUES('"+name+"','"+roll+"');");
	    		statement.executeUpdate();
	    		String op=new String("Data inserted successfully !");
	    		out.println(op);
	    		}
	    		catch(Exception e){
	    		out.println("Duplicate entry.Data cannot be inserted");
	    		}
	    	}
	    	else if(btn.equals("delete")){
	    		PreparedStatement statement=conn.prepareStatement("delete from stu_details where ROLL_NO = '"+roll+"';");
	    		statement.executeUpdate();
	    		out.write("Data deleted successfully !");
	    	}
	    	else if(btn.equals("update")){
	    		PreparedStatement statement=conn.prepareStatement("update stu_details set NAME='"+name+"' where ROLL_NO='"+roll+"';");
	    		statement.executeUpdate();
	    		out.println("Data updated successfully !");
	    	}
	    	else if(btn.equals("retrive")){
	    		PreparedStatement  statement=conn.prepareStatement("select * from stu_details;");
	    		ResultSet result=statement.executeQuery();
	    		out.write("<table>");
	    		out.write("<tbody>");
	    		out.write("<tr>");
    			out.write("<td>");
    			out.write("NAME");
    			out.write("<\td>");
    			out.write("<td>");
    			out.write("ROLL_NO");
    			out.write("<\td>");
    			out.write("<\tr>");
    			
	    		while(result.next())
	    		{	
	    			//out.write("<tb>\n");
	    			out.write("<br>");
	    			out.write("<tr>");
	    			out.write("<td>");
	    			out.write(result.getString(1));
	    			out.write("<\td>");
	    			out.write("<td>");
	    			out.write(result.getString(2));
	    			out.write("<\td>");
	    			out.write("<br>");
	    			out.write("<\tr>");
	    			//out.write("<\tb>");
	    		}
	    		out.write("<\tbody>");
	    		out.write("<\table>");
	    		
	    	}
	    	else if(btn.equals("stuname")){
	    		PreparedStatement  statement=conn.prepareStatement("select * from stu_details where ROLL_NO='"+roll+"';");
	    		ResultSet result=statement.executeQuery();
	    		result.next();
	    			out.println(result.getString(1)+"<br>");
	    	}
	    	else if(btn.equals("sturoll")){
	    		PreparedStatement  statement=conn.prepareStatement("select * from stu_details where NAME='"+name+"';");
	    		ResultSet result=statement.executeQuery();
	    		result.next();
	    			out.println(result.getString(2)+"<br>");
	    	}
	    	
	    	
		}
		catch(Exception e){
			throw new Error(e);
			//System.out.print(e.getMessage());
		}
		finally{
			try{
			PreparedStatement statement=conn.prepareStatement("select * from stu_details;");
              //String s="select * from stu_details;";
							
				 ResultSet result=statement.executeQuery();
				if(PDF.genPDF(result))
					System.out.println("\nPDF created :D");
				else
					System.out.println("\nPDF thusss :(");
				while(result.next()){
					System.out.println(result.getInt("ROLL_NO")+"\t"+result.getString("NAME"));
				}				
			}catch(Exception e){
				e.printStackTrace();
			}
		}	  
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		service(req, res);
	}

	public void destroy(){
	    try {   
	    	stmt.close();
	    	conn.close();
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
	}
}



	
	