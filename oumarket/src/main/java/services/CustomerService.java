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
import pojo.Customer;


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
    
    public Customer findCustomerByPhoneNumber(List<Customer> customerList, String phone) throws SQLException {
        for (Customer customer : customerList) {
            if (customer.getPhone().equals(phone)) {
                return customer;
            }
        }
    return null;
    }
}
