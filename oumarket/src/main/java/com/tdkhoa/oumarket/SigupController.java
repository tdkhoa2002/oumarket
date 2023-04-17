/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import services.AccountSevrice;

/**
 *
 * @author ASUS
 */
public class SigupController implements Initializable {
    static AccountSevrice aS = new AccountSevrice();
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField passwordField2;
    @FXML private Button btnCancel;
    @FXML private Button btnSigup;
    @FXML private Text actiontarget;
    @FXML private AnchorPane AnchorPane;
    @FXML private ComboBox ComboBox;
    
    ObservableList<String> list = FXCollections.observableArrayList("Nguyễn Kiệm", "Võ Văn Tần", "Mai Thị Lựu");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComboBox.setItems(list);
    }
    
    
    public void handleSigupButtonAction(ActionEvent eve) {
        this.btnSigup.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String password2 = passwordField2.getText();
            String branch = (String) ComboBox.getValue();
            if (username.isEmpty() || password.isEmpty() || password2.isEmpty() || branch == null) {
                actiontarget.setFill(Color.RED);
                actiontarget.setText("VUI LÒNG NHẬP ĐẦY ĐỦ THÔNG TIN.");
            } else {
                if (password.equals(password2)) {

                    if (aS.registerUser(username, password, branch)) {
                        btnSigup.getScene().getWindow().hide();
                    } else {
                        actiontarget.setFill(Color.RED);
                        actiontarget.setText("Tên tài khoản đã tồn tại.");
                    }

                } else {
                    // hiển thị thông báo lỗi
                    actiontarget.setFill(Color.RED);
                    actiontarget.setText("Mật khẩu xác nhận không trùng khớp.");
                }
            }

        });
    }
    public void closeView(ActionEvent evt) {
        this.btnCancel.setOnAction(event -> {
            btnCancel.getScene().getWindow().hide();
        });
    }

    
    
}
