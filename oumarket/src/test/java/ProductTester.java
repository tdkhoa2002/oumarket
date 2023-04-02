
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.ProductService;
import org.junit.jupiter.api.*;
import services.JdbcUtils;
import pojo.Product;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Khoa Tran
 */
public class ProductTester {
    private static Connection conn;
    private static ProductService pS;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pS = new ProductService();
    }
    
    @AfterAll
    public static void afterAll() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void tesAddSuccessful() {
        Product p = new Product("3", "Sp test", 1, 80000, 100, "bịch");
        
        try {
            boolean actual = pS.addProduct(p);
            Assertions.assertTrue(actual);
            
            String sql = "SELECT * FROM products WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getId());
            
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Sp test", rs.getString("name"));
            Assertions.assertEquals(3, rs.getInt("category_id"));
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
