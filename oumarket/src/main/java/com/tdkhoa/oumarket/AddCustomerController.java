/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojo.Category;
import pojo.Customer;
import services.CategoryService;
import services.CustomerService;
import utils.MessageBox;

/**
 *
 * @author Khoa Tran
 */
public class AddCustomerController {
    static CustomerService cusService = new CustomerService();
    
    @FXML private TextField name;
    @FXML private TextField phone;
    @FXML private DatePicker ngaySinh;
    @FXML private VBox sceneVBox;
     Stage stageOut;
    public void addCustomer (ActionEvent evt) throws SQLException {
        Customer cus = new Customer();
        cus.setName(name.getText());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = ngaySinh.getValue().format(formatter);
        
        cus.setNgaySinh(formattedDateTime);
        cus.setPhone(phone.getText());
        cusService.addCustomer(cus);
        Alert a = MessageBox.getBox("Thêm khách hàng", "Bạn có chắc muốn thêm khách hàng không? ", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    stageOut = (Stage) sceneVBox.getScene().getWindow();
                    stageOut.close();
                }
            });
    }
    
    public void closeView(ActionEvent evt) {
        stageOut = (Stage) sceneVBox.getScene().getWindow();
        stageOut.close();
    }
}
