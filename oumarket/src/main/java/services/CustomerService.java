/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<Customer> getCustomers() throws SQLException {
        List<Customer> customersList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
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
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM customer WHERE phone = ?";
            PreparedStatement stmCheckUnique = conn.prepareCall(sql);
            stmCheckUnique.setString(1, cus.getPhone());
            ResultSet resultSet = stmCheckUnique.executeQuery();
            if (resultSet.next()) {
                MessageBox.getBox("Khách hàng", "Khách hàng này đã tồn tại", Alert.AlertType.ERROR).show();
                return false;
            }
            if (cus.getNgaySinh().length() != 10) {
                MessageBox.getBox("Khách hàng", "Số điện thoại phải 10 ký tự", Alert.AlertType.ERROR).show();
                return false;
            }
            sql = "INSERT INTO customer(name,ngay_sinh, phone, point) VALUES(?,?,?,?)"; // SQL injection
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, cus.getName());
            stm.setString(2, cus.getNgaySinh());
            stm.setString(3, cus.getPhone());
            stm.setInt(4, 0);

            LocalDate now = LocalDate.now();
            int yearOfNow = now.getYear();
            
            int yearOfBirthDay = Integer.parseInt(cus.getNgaySinh().substring(6, 10));
            
            stm.executeUpdate();
            if (yearOfNow - yearOfBirthDay < 16) {
                MessageBox.getBox("Khách hàng", "Khách hàng phải trên 16 tuổi", Alert.AlertType.ERROR).show();
                return false;
            }
            else {
                try {
                    conn.commit();
                    return true;
                } catch (SQLException ex) {
                    return false;
                }
            }
        }
    }

    public Customer findCustomerByPhoneNumber(List<Customer> customerList, String phone) throws SQLException {
        for (Customer customer : customerList) {
            if (customer.getPhone().equals(phone)) {
                return customer;
            }
        }
        return null;
    }
}
