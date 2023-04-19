/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.Order;
import pojo.OrderDetails;
import pojo.Product;
import services.JdbcUtils;
import services.OrderDetailsService;
import services.OrderService;

/**
 *
 * @author Khoa Tran
 */
public class OrderDetailsTester {

    private static Connection conn;
    private static OrderDetailsService ordDetailsService;
    private OrderService ordService;
    private ObservableList<OrderDetails> orderDetailsList;

    @BeforeAll
    public static void beforeAll() {
    }

    @AfterAll
    public static void afterAll() {
    }

    @Test
    public void test() {
        boolean actual = false;
        Assertions.assertTrue(actual);
    }

    @Test
    public void saveOrderDetailsTest() {
        try {
            ordDetailsService = new OrderDetailsService();
            OrderService ordService = new OrderService();
            ObservableList<OrderDetails> orderDetailsList = null;
            boolean actual = false;

            String idOrder = "d8b958d8-cdae-456b-88d3-fd02261ae9fe";
            Order o = ordService.findOrder(idOrder);
            Assertions.assertEquals(o.getId(), idOrder);
            Product p1 = new Product("Sp test 1", 1, 21000, 100, "Cái");
            Product p2 = new Product("Sp test 2", 1, 35000, 100, "Lon");
            Product p3 = new Product("Sp test 3", 1, 23000, 100, "bịch");
            Product p4 = new Product("Sp test 4", 1, 8000, 100, "Cái");
            Product p5 = new Product("Sp test 5", 1, 18000, 100, "Lon");

            OrderDetails oD1 = new OrderDetails(p1, 5);
            OrderDetails oD2 = new OrderDetails(p2, 6);
            OrderDetails oD3 = new OrderDetails(p3, 7);
            OrderDetails oD4 = new OrderDetails(p4, 8);
            OrderDetails oD5 = new OrderDetails(p5, 9);

            orderDetailsList.add(oD1);
            orderDetailsList.add(oD2);
            orderDetailsList.add(oD3);
            orderDetailsList.add(oD4);
            orderDetailsList.add(oD5);

            for (OrderDetails x : orderDetailsList) {
                actual = ordDetailsService.saveOderDetails(x, o);
                Assertions.assertTrue(actual);
                String sql = "SELECT * FROM orderdetails WHERE id = ?";
                PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
                stm.setString(1, o.getId());

                ResultSet rs = stm.executeQuery();
                Assertions.assertNotNull(rs.next());
                Assertions.assertEquals(o.getId(), rs.getString("order_id")); //kiem tra da them vao hay chua
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailsTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void updateQuantityInStockTest() {
        ordDetailsService = new OrderDetailsService();
        ordService = new OrderService();
        orderDetailsList = null;
        boolean actual = false;
        Assertions.assertTrue(actual);
        try {

            String idOrder = "d8b958d8-cdae-456b-88d3-fd02261ae9fe";
            Order o = ordService.findOrder(idOrder);
            Assertions.assertEquals(o.getId(), idOrder);
            Product p1 = new Product("Sp test 1", 1, 21000, 100, "Cái");
            Product p2 = new Product("Sp test 2", 1, 35000, 100, "Lon");
            Product p3 = new Product("Sp test 3", 1, 23000, 100, "bịch");
            Product p4 = new Product("Sp test 4", 1, 8000, 100, "Cái");
            Product p5 = new Product("Sp test 5", 1, 18000, 100, "Lon");

            OrderDetails oD1 = new OrderDetails(p1, 5);
            OrderDetails oD2 = new OrderDetails(p2, 6);
            OrderDetails oD3 = new OrderDetails(p3, 7);
            OrderDetails oD4 = new OrderDetails(p4, 8);
            OrderDetails oD5 = new OrderDetails(p5, 9);

            orderDetailsList.add(oD1);
            orderDetailsList.add(oD2);
            orderDetailsList.add(oD3);
            orderDetailsList.add(oD4);
            orderDetailsList.add(oD5);

            for (OrderDetails x : orderDetailsList) {
                ordDetailsService.saveOderDetails(x, o);
            }
            Assertions.assertTrue(ordDetailsService.updateQuantityInStock(orderDetailsList));
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailsTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void viewDetailTest() {
        try {
            String idOrder = "d8b958d8-cdae-456b-88d3-fd02261ae9fe";
            List<Product> listProducts = ordDetailsService.viewDetail(idOrder);
            Assertions.assertEquals(10, listProducts.size());
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailsTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
