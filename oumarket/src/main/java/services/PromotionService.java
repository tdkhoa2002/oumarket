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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import pojo.Category;
import pojo.Product;
import pojo.Promotion;

/**
 *
 * @author Khoa Tran
 */
public class PromotionService {
        public List<Promotion> getPromotions(String kw) throws SQLException {
            List<Promotion> promos = new ArrayList<>();
            try (Connection conn = JdbcUtils.getConn()) {
                Statement stm = conn.createStatement();

                ResultSet rs = stm.executeQuery("SELECT * FROM promotion");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double value = rs.getInt("value");
                    String start = rs.getString("start");
                    String end = rs.getString("end");
                    
                    promos.add(new Promotion(id, name, value, start, end));
                }
            }
            return promos;
        }
        
        public boolean deletePromotion(int id) throws SQLException {
            try (Connection conn = JdbcUtils.getConn()) {
                String sql = "DELETE FROM promotion WHERE id=?";
                PreparedStatement stm = conn.prepareCall(sql);
                stm.setInt(1, id);

                return stm.executeUpdate() > 0;
            }
        }
    
    public boolean addPromotion(Promotion p) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO promotion(name, value, start, end) VALUES(?, ?, ?, ?)"; // SQL injection
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getName());
            stm.setDouble(2, p.getValue());
            stm.setString(3, p.getTimeStart().toString());
            stm.setString(4, p.getTimeEnd().toString());

            stm.executeUpdate();

            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }
        }
    }
    
    public static boolean isPromotionActive(Promotion promotion) {
        LocalDate now = LocalDate.now();
        return now.isAfter(promotion.getTimeStart()) && now.isBefore(promotion.getTimeEnd());
    }

    public static double getDiscountedPrice(Product product) throws SQLException {
        double priceDiscount = 0;
        double price = product.getPrice();
        try ( Connection conn = JdbcUtils.getConn() ) {
            String sql = "Select value from promotion where id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, product.getPromotion_id());
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                priceDiscount =  rs.getDouble("value");
            }
        }
        price *= (1 - priceDiscount / 100);
//        if (product.getPromotion_id()!= null && isPromotionActive(product.getPromotion())) {
//            double discountPercentage = product.getPromotion().getValue();
//            price *= (1 - discountPercentage / 100);
//        }
        return price;
    }
}
