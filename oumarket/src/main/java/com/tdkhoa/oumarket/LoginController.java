/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import services.AccountSevrice;

/**
 *
 * @author ASUS
 */
public class LoginController {
    static AccountSevrice aS = new AccountSevrice();
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button btnSigin;
    @FXML private Button btnLogin;
    @FXML private Text actiontarget;
    
    public void handleLoginButtonAction(ActionEvent eve) {
        this.btnLogin.setOnAction(event -> {
                String username = usernameField.getText();
                String password = passwordField.getText();
 
                if (aS.authenticateUser(username, password)) {
                  
                    // chuyển hướng đến ứng dụng
                } else {
                    // hiển thị thông báo lỗi
                    actiontarget.setFill(Color.RED);
                    actiontarget.setText("Incorrect username or password.");
                }
            });
    }
     public void handleSiginButtonAction(ActionEvent eve) {
        this.btnSigin.setOnAction(event -> {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (aS.authenticateUser(username, password)) {
                  
                    List<String> userRoles = aS.getUserRoles(username);

                    // kiểm tra vài trò của người dùng
                    if (userRoles.contains("admin")) {
                        // chuyển hướng đén 
                    } else {
                        // hiển thị thông báo lỗi
                        actiontarget.setFill(Color.RED);
                        actiontarget.setText("User does not have required role.");
                    }
                } else {
                    // hiển thị thông báo lỗi
                    actiontarget.setFill(Color.RED);
                    actiontarget.setText("Incorrect username or password.");
                }
            });
    }
}


