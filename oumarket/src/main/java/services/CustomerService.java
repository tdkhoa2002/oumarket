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
import javafx.scene.control.Alert;
import pojo.Customer;
import utils.MessageBox;


/**
 *
 * @author Khoa Tran
 */
public class CustomerService {
    public List<Customer> getCustomers(String kw) throws SQLException {
        List<Customer> customersList = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConn() ) {
            String sql = "SELECT * FROM customer";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String ngaySinh = rs.getString("ngay_sinh");
                String phone = rs.getString("phone");
                double point = rs.getDouble("point");
                customersList.add(new Customer(name, ngaySinh, phone, point));
            }
        }
          return customersList;
    }
    
    public boolean addCustomer(Customer cus) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO customer(name,ngay_sinh, phone, point) VALUES(?,?,?,?)"; // SQL injection
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, cus.getName());
            stm.setString(2, cus.getNgaySinh());
            stm.setString(3, cus.getPhone());
            stm.setInt(4, 0);

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
    
    public Customer findCustomerByPhoneNumber(List<Customer> customerList, String phone) throws SQLException {
        for (Customer customer : customerList) {
            if (customer.getPhone().equals(phone)) {
                return customer;
            }
        }
        MessageBox.getBox("Tìm kiếm khách hàng", "Không tồn tại khách hàng có số điện thoại này", Alert.AlertType.ERROR).show();
        return null;
    }
}
