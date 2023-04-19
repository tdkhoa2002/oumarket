
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

        @Test
    public void testNotNull() {
         pS = new ProductService();
        try {
            List<Product> pros =  pS.getProducts(null);
            
            long t = pros.stream().filter(c -> c.getName() == null).count();
            Assertions.assertTrue(t == 0);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testUniqueName() {
        try {
            pS = new ProductService();
            List<Product> pros =  pS.getProducts(null);
            
            List<String> names = pros.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
            Set<String> testNames = new HashSet<>(names);
            Assertions.assertEquals(names.size(), testNames.size());
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    @Test
//    public void searchNameProduct() {
//        try {
//            pS = new ProductService();
//            List<Product> pros = pS.getProducts("Cá");
//            Assertions.assertEquals(1, pros.size());
//
//            for(Product x: pros) {
//                    Assertions.assertTrue(x.getName().contains("Cá"));
//            }
//        } catch (SQLException ex) {
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
    
//    @Test
//    public void testEditProduct() throws SQLException { //Test chinh sua thanh cong
//        String id = "2a904e57-dd90-44fc-a2f6-4c332b4285a3";
//        
//        pS = new ProductService();
//        Product p = new Product(id, "Bò", 41, 15000,100, "Kg", 5);
//        boolean actual = pS.editProduct(p);
//        Assertions.assertTrue(actual);
//        if (actual == true) {
//            Assertions.assertEquals(p.getName(), "Bò");
//        }
//    }
    
//    @Test
//    public void testEditProduct() throws SQLException { //Test chinh sua ten cua san pham trung voi ten cua san pham khac
//        String id = "2a904e57-dd90-44fc-a2f6-4c332b4285a3";
//        
//        pS = new ProductService();
//        Product p = new Product(id, "Gạo", 41, 15000,100, "Kg", 5);
//        boolean actual = pS.editProduct(p);
//        Assertions.assertTrue(actual);
//        if (actual == true) {
//            Assertions.assertEquals(p.getName(), "Gạo");
//        }
//    }
    
//    @Test
//    public void testEditCategory() throws SQLException {
//        String nameUpdate = "khoa ma khuyen mai";
//        int id = 4;
//        Date start = new Date(2023, 04, 15);
//        Date end = new Date(2023, 04, 20);
//        
//        proService = new PromotionService();
//        Promotion pro = new Promotion(id,nameUpdate, 10, start, end);
//        boolean actual = proService.editPromotion(pro);
//        Assertions.assertTrue(actual);
//        if (actual == true) {
//            Assertions.assertEquals(pro.getName(), nameUpdate);
//        }
//    }
    
//    @Test
//    public void testDelete() {
//        String id = "28765db2-8326-4bdb-a8be-61bc489e84c1";
//        boolean actual;
//        try {
//            pS = new ProductService();
//            conn = JdbcUtils.getConn();
//            actual = pS.deleteProduct(id);
//            Assertions.assertTrue(actual);
//            
//            String sql = "SELECT * FROM products WHERE id=?";   //Lay ra san pham co ID do, neu khong co thi tra ve false. Mong muon false
//            PreparedStatement stm = conn.prepareCall(sql);
//            stm.setString(1, id);
//            ResultSet rs = stm.executeQuery();
//            Assertions.assertFalse(rs.next());
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    @Test
//    public void testDelete() {
//        String id = "28765db2-8326-4bdb-a8be-61bc489e84c1"; //ID nay khong ton tai
//        boolean actual;
//        try {
//            pS = new ProductService();
//            conn = JdbcUtils.getConn();
//            actual = pS.deleteProduct(id);
//            Assertions.assertTrue(actual); //Mong muon xoa dc se tra ve TRUE
//            
//            String sql = "SELECT * FROM products WHERE id=?";   //Lay ra san pham co ID do, neu khong co thi tra ve false. Mong muon false
//            PreparedStatement stm = conn.prepareCall(sql);
//            stm.setString(1, id);
//            ResultSet rs = stm.executeQuery();
//            Assertions.assertFalse(rs.next());
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
