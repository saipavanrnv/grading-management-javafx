package com.mohit.gradingmanagementjavafx.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    
    public static Connection connectDb(){
        
        try{
        
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection connect = 
                DriverManager.getConnection("jdbc:mysql://localhost:3306/grading-management", "root", "0226");
            
            return connect;
            
        }catch(Exception e){e.printStackTrace();} 
        
        return null;
    }
    
}
