/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import pojo.Product;



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
                
//                sql = "SELECT * from categories WHERE id =?";
//                
//                stm = conn.prepareCall(sql);
//                
//                stm.setInt(1, p.getCategoryId());
//                
//                 ResultSet rs = stm.executeQuery();
//                
//                 while(rs.next()) {
//                     p.setCategoryName(rs.getString("name"));
//                 }
                 System.out.println(p.getCategoryName());
                
                return true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }
        }
    }
    
    public List<Product> getProducts(String kw) throws SQLException {
        PromotionService promoService = new PromotionService();
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
                 Product p = new Product(rs.getString("id"), rs.getString("name"), rs.getInt("category_id"), rs.getDouble("price"), 
                         rs.getInt("quantity"), rs.getString("unit"), rs.getInt("promotion_id"));
                 sql = "SELECT * from categories WHERE id =?";
                
                stm = conn.prepareCall(sql);
                
                stm.setInt(1, p.getCategoryId());
                
                 ResultSet rs1 = stm.executeQuery();
                
                 while(rs1.next()) {
                     p.setCategoryName(rs1.getString("name"));
                 }
                 
                 sql = "SELECT * from promotion WHERE id =?";
                
                stm = conn.prepareCall(sql);
                
                stm.setInt(1, p.getPromotion_id());
                
                 ResultSet rs2 = stm.executeQuery();
                
                 while(rs2.next()) {
                     p.setPromotion_name(rs2.getString("name"));
                 }
                 
                 double priceDiscounted = 0;
                 priceDiscounted = promoService.getDiscountedPrice(p);
                    DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Số # thay thế cho số 0
                    decimalFormat.setRoundingMode(RoundingMode.CEILING); // Đặt chế độ làm tròn
                    String formattedNumber = decimalFormat.format(priceDiscounted);
                    p.setPriceDiscount(Double.parseDouble(formattedNumber));
                 
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
    
    public boolean editProduct(Product p) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE products SET name =?, category_id =?, price =?, unit =?, quantity=?, promotion_id = ? WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getName());
            stm.setInt(2, p.getCategoryId());
            stm.setDouble(3, p.getPrice());
            stm.setString(4, p.getUnit());
            stm.setInt(5, p.getQuantity());
            stm.setInt(6, p.getPromotion_id());
            stm.setString(7, p.getId());
            
//            sql = "SELECT * from promotion WHERE id =?";
//                
//                stm = conn.prepareCall(sql);
//                
//                stm.setInt(1, p.getPromotion_id());
//                
//                 ResultSet rs1 = stm.executeQuery();
//                
//                 while(rs1.next()) {
//                     p.setPromotion_name(rs1.getString("name"));
//                 }
            
            

            return stm.executeUpdate() > 0;
        }
    }
}
 