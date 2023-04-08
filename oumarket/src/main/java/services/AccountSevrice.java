/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author VanAn
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import pojo.Account;

public class AccountSevrice {

//    public class LoginApp extends Application {

      
//        @Override
//        public void start(Stage primaryStage) {
//
//            // Tao GridPane de dat cac thanh phan giao dien
//            GridPane grid = new GridPane();
//            grid.setAlignment(Pos.CENTER);
//            grid.setHgap(10);
//            grid.setVgap(10);
//            grid.setPadding(new Insets(25, 25, 25, 25));
//
//            // Them Text "Welcome" o hang dau tien
//            Text scenetitle = new Text("Welcome");
//            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//            grid.add(scenetitle, 0, 0, 2, 1);
//
//            // Them Label "Username" o hang thu hai
//            Label userName = new Label("Username:");
//            grid.add(userName, 0, 1);
//
//            // Them TextField de nhap ten dang nhap
//            TextField userTextField = new TextField();
//            grid.add(userTextField, 1, 1);
//
//            // Them Label "Password" o hang thu ba
//            Label pw = new Label("Password:");
//            grid.add(pw, 0, 2);
//
//            // Them PasswordField de nhap mat khau
//            PasswordField pwBox = new PasswordField();
//            grid.add(pwBox, 1, 2);
//
//            // Them nut Login o hang thu tu
//            Button btn = new Button("Login");
//            HBox hbBtn = new HBox(10);
//            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//            hbBtn.getChildren().add(btn);
//            grid.add(hbBtn, 1, 4);
//
//            // Them Text cho thong bao loi dang nhap o hang cuoi cung
//            final Text actiontarget = new Text();
//            grid.add(actiontarget, 1, 6);
//
//            btn.setOnAction(event -> {
//                String username = userTextField.getText();
//                String password = pwBox.getText();
//
//                // authenticate user
//                if (authenticateUser(username, password)) {
//                  
//                    List<String> userRoles = getUserRoles(username);
//
//                    // kiểm tra vài trò của người dùng
//                    if (userRoles.contains("admin")) {
//                        // chuyển hướng đén 
//                        redirectToMainScreen();
//                    } else {
//                        // hiển thị thông báo lỗi
//                        actiontarget.setFill(Color.RED);
//                        actiontarget.setText("User does not have required role.");
//                    }
//                } else {
//                    // hiển thị thông báo lỗi
//                    actiontarget.setFill(Color.RED);
//                    actiontarget.setText("Incorrect username or password.");
//                }
//            });
//
//            // display UI
//            Scene scene = new Scene(grid, 300, 275);
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        }

// lấy danh sách các vai trò của người dùng từ database
        public List<String> getUserRoles(String username) {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            List<String> roles = new ArrayList<>();

            try {
                // Tạo kết nối đến database
                conn = JdbcUtils.getConn();

                // Tạo câu lệnh truy vấn
                String sql = "SELECT role FROM user_roles WHERE username = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);

                // Thực thi truy vấn và lấy kết quả trả về
                rs = stmt.executeQuery();

                // Lặp qua tất cả các bản ghi và thêm vai trò vào danh sách
                while (rs.next()) {
                    roles.add(rs.getString("role"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // Đóng tất cả các đối tượng ResultSet, PreparedStatement và Connection
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return roles;
        }

        public boolean authenticateUser(String username, String password) {
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;

            try {
                // Lấy kết nối cơ sở dữ liệu
                connection = JdbcUtils.getConn();

                // Tạo câu lệnh truy vấn để kiểm tra thông tin đăng nhập
                String query = "SELECT * FROM account WHERE username = ? AND password = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);

                // Thực thi truy vấn
                resultSet = statement.executeQuery();

                // Xác thực thông tin đăng nhập
                return resultSet.next();

            } catch (SQLException ex) {
                Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {

                    if (resultSet
                            != null) {
                        resultSet.close();
                    }

                    if (statement
                            != null) {
                        statement.close();
                    }

                    if (connection
                            != null) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return false;
        }
// chuyển hướng đến màn hình chính

        public void redirectToMainScreen() {
            // redirect to main application screen
        }

    }

//}
