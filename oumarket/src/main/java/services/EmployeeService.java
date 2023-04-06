/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import pojo.Employee;

/**
 * CRUD (insert, update, delete, findAll)
 * @author ASUS
 */
public class EmployeeService {

    public static List<Employee> findAll() {
        List<Employee> employeeList = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        try {
            //lay tat ca danh sach nhan vien
            con = JdbcUtils.getConn();
            
            //query
            String sql = "select * from employee";
            statement = con.createStatement();
            
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Employee std = new Employee(resultSet.getInt("id")
                        ,resultSet.getString("fullname"));
                employeeList.add(std);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
      //ket thuc
        return employeeList ;  
    }   
    public static void insert(Employee std){
        
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = JdbcUtils.getConn();
            
            String sql = "insert into employee(fullname)";
            statement = con.prepareCall(sql);
            
            statement.setString(1, std.getName());
            
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public static void update(Employee std){
        
        Connection con = null;
        PreparedStatement statement = null;
        try {
            
            con = JdbcUtils.getConn();
            
            String sql = "update employee set fullname=? where id = ?";
            statement = con.prepareCall(sql);
            
            statement.setString(1, std.getName());
            statement.setInt(2, std.getId());
            
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public static void delete(int id){
        
        Connection con = null;
        PreparedStatement statement = null;
        try {
          
            con = JdbcUtils.getConn();
            
            String sql = "delete from employee where id = ?";
            statement = con.prepareCall(sql);
            
            statement.setInt(1, id);
            
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
