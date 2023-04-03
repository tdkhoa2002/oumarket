/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import pojo.OrderDetails;
import pojo.Order;

/**
 *
 * @author Khoa Tran
 */
public class OrderDetailsService {
    public boolean saveOderDetails(OrderDetails oD, Order o) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO orderdetails(productName, price, quantity, total, order_id) VALUES(?, ?, ?, ?, ?)"; // SQL injection
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, oD.getProduct().getName());
            stm.setDouble(2, oD.getPrice());
            stm.setInt(3, oD.getQuantity());
            stm.setDouble(4, oD.getTotal());
            stm.setString(5, o.getId());

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
}
