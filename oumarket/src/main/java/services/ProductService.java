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
import pojo.Product;



/**
 *
 * @author Khoa Tran
 */
public class ProductService {
    public List<Product> getProducts(String kw) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        List<Product> results = new ArrayList<>();
        String sql = "Select * From Product";
        if(kw != null && !kw.isEmpty()){
            sql += "Where name like concat (''%', ?, '%')";
        }
        PreparedStatement stm = conn.prepareCall(sql);
        if (kw != null && !kw.isEmpty())
                stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString("id"), rs.getString("name"), rs.getInt("category_id"), rs.getDouble("price"), rs.getInt("quantity"));
                results.add(p);
            }
          return results;
    }
}
