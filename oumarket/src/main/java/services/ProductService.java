/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import pojo.Product;
import pojo.Category;
import utils.MessageBox;



/**
 *
 * @author Khoa Tran
 */
public class ProductService {
    public boolean addProduct(Product p) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO products(id, name, category_id, price, unit, quantity) VALUES(?, ?, ?, ?, ?, ?)"; // SQL injection
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getId());
            stm.setString(2, p.getName());
            stm.setInt(3, p.getCategoryId());
            stm.setDouble(4, p.getPrice());
            stm.setString(5, p.getUnit());
            stm.setInt(6, p.getQuantity());

            stm.executeUpdate();

            try {
                conn.commit();
//                sql = "SELECT name from categories WHERE id = 1";
//                
//                stm = conn.prepareCall(sql);
//                
//                stm.setInt(1, p.getCategoryId());
//                stm.executeQuery();
//                
//                 ResultSet rs = stm.executeQuery();
//                
//                 while(rs.next()) {
//                     p.setCategoryName(rs.getString("name"));
//                 }
                
                return true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }
        }
    }
    
    public List<Product> getProducts(String kw) throws SQLException {
        List<Product> results = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConn() ) {
            String sql = "SELECT * FROM products";
//            if(kw != null && !kw.isEmpty()){
//                sql += "Where name like concat (''%', ?, '%')";
//            }
            PreparedStatement stm = conn.prepareCall(sql);
//            if (kw != null && !kw.isEmpty())
//                    stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                 Product p = new Product(rs.getString("id"), rs.getString("name"), rs.getInt("category_id"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("unit"));
                 results.add(p);
             }
        }
          return results;
    }
    
    public boolean deleteProduct(String id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM products WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);
            
            return stm.executeUpdate() > 0;
        }
    }
    
    public boolean editProduct(String id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE FROM products WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);
            
            return stm.executeUpdate() > 0;
        }
    }
}
