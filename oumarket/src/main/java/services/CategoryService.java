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
import java.util.ArrayList;
import java.util.List;
import pojo.Category;
/**
 *
 * @author Khoa Tran
 */
public class CategoryService {
    public List<Category> getCategories(String kw) throws SQLException {
        List<Category> cates = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("SELECT * FROM categories");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                cates.add(new Category(id, name));
            }
        }
        return cates;
    }
    
    public boolean deleteCategory(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM categories WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            
            return stm.executeUpdate() > 0;
        }
    }
    
    public boolean addCategory(Category c) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO categories(name) VALUES(?)"; // SQL injection
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, c.getName());

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
    
    public void saveDb(Category c) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "UPDATE categories SET name = ? ";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, c.getName());
            stm.executeUpdate();
        }
    }
}
