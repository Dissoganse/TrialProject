package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Driver;
import java.sql.Statement;
import java.sql.ResultSet;

import org.hsqldb.jdbc.JDBCDriver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bank_frontend.Main;

@SpringBootApplication
public class DemoApplication {	
	static private Main frontik = new Main();
	
	
	static private String sql_querka = new String();
	static private StringBuffer string_buffer = new StringBuffer();
	
	public static void main(String[] args) {
		Connection con = null;
		String[] statementoza = null;
		Statement stata;
		File is_queried = new File("viola.txt");
		
		try {
			DriverManager.registerDriver(new JDBCDriver());
			con = DriverManager.getConnection("jdbc:hsqldb:file:db/bankdb;create=true", "SA", "");
			stata = con.createStatement();
			
			FileReader readerok = new FileReader(new File("hsqlka.sql"));
			BufferedReader buff_readerok = new BufferedReader(readerok);
			
			while((sql_querka = buff_readerok.readLine()) != null)
			{
				string_buffer.append(sql_querka);
			}
			buff_readerok.close();
			
			statementoza = string_buffer.toString().split(";");
			
			if(!is_queried.exists())
			for(int i = 0; i < statementoza.length; i++)
				stata.executeUpdate(statementoza[i]);
			
			is_queried.createNewFile();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		catch(IOException e)
		{

		}

		
		frontik.main(null);
		SpringApplication.run(DemoApplication.class, args);
	}
	
}
