
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    }

    @AfterAll
    public static void afterAll() {
    }

//        @Test
//    public void testNotNull() {
//         pS = new ProductService();
//        try {
//            List<Product> pros =  pS.getProducts(null);
//            
//            long t = pros.stream().filter(c -> c.getName() == null).count();
//            Assertions.assertTrue(t == 0);
//        } catch (SQLException ex) {
//            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    @Test
//    public void testUniqueName() {
//        try {
//            pS = new ProductService();
//            List<Product> pros =  pS.getProducts(null);
//            
//            List<String> names = pros.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
//            Set<String> testNames = new HashSet<>(names);
//            Assertions.assertEquals(names.size(), testNames.size());
//        } catch (SQLException ex) {
//            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    @Test
//    public void searchNameProduct() {
//        String kw = "khoa1";
//        try {
//            pS = new ProductService();
//            List<Product> pros =  pS.getProducts(null);
//            List<String> names = pros.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
//            Set<String> testNames = new HashSet<>(names);
//            Assertions.assertEquals(names.size(), testNames.size());
//        }
//        catch (SQLException ex) {
//            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    @Test
//    public void testAddProduct() throws SQLException {
//        try {
//            pS = new ProductService();
//            Product p = new Product("Sp test", 1, 80000, 100, "bịch");
//            boolean actual = pS.addProduct(p);
//            Assertions.assertTrue(actual);
//
//            String sql = "SELECT * FROM categories where name = ?";
//            PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
//            stm.setString(1, p.getName());
//
//            ResultSet rs = stm.executeQuery();
//            Assertions.assertNotNull(rs.next());
//            Assertions.assertEquals(p.getName(), rs.getString("name")); //kiem tra da them vao hay chua
//        }
//        catch (SQLException ex) {
//            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
