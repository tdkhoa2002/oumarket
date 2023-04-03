/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pojo.Order;

/**
 *
 * @author Khoa Tran
 */
public class OrderService {
    public boolean addOrder(Order o) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO orders(id, total, orderDate) VALUES(?,?,?)"; // SQL injection
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, o.getId());
            stm.setDouble(2, o.getTotal());
            stm.setString(3, o.getOrderDate().toString());
            
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
    
    public List<Order> getOrders() throws SQLException {
        List<Order> ordersList = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConn() ) {
            String sql = "SELECT * FROM orders";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String time = rs.getString("orderDate");
                LocalDateTime orderDate = LocalDateTime.parse(time);
                double total = rs.getDouble("total");
                ordersList.add(new Order(id, orderDate, total));
            }
        }
          return ordersList;
    }
    
}
