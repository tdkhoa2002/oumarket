/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pojo.Category;
/**
 *
 * @author Khoa Tran
 */
public class CategoryService {
//    public List<Category> getCategories() throws SQLException {
//        List<Category> cates = new ArrayList<>();
//        try (Connection conn = JdbcUtils.getConn()) {
//            Statement stm = conn.createStatement();
//
//            ResultSet rs = stm.executeQuery("SELECT * FROM category");
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                cates.add(new Category(id, name));
//            }
//        }
//        
//        return cates;
//    }
}
