/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import static com.tdkhoa.oumarket.EditProductController.itemsUnit;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class AddCustomerController implements Initializable {

    static CustomerService cusService = new CustomerService();

    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private DatePicker ngaySinh;
    @FXML
    private VBox sceneVBox;
    Stage stageOut;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.phone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10) {
                this.phone.setText(oldValue);
            }
        });
    }

    public void addCustomer(ActionEvent evt) throws SQLException {
        if (this.name.getText().isEmpty() || this.phone.getText().isEmpty() || this.ngaySinh.getValue() == null) {
            MessageBox.getBox("Khách hàng", "Dữ liệu không được để trống", Alert.AlertType.WARNING).show();
        } else {
            try {
                Long.parseLong(this.phone.getText());
                if (this.phone.getText().length() == 10) {
                    Customer cus = new Customer();
                    cus.setName(name.getText());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDateTime = ngaySinh.getValue().format(formatter);

                    cus.setNgaySinh(formattedDateTime);
                    cus.setPhone(phone.getText());
                    Alert a = MessageBox.getBox("Thêm khách hàng", "Bạn có chắc muốn thêm khách hàng không? ", Alert.AlertType.CONFIRMATION);
                    a.showAndWait().ifPresent(res -> {
                        if (res == ButtonType.OK) {
                            try {
                                cusService.addCustomer(cus);
                            } catch (SQLException ex) {
                                Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            stageOut = (Stage) sceneVBox.getScene().getWindow();
                            stageOut.close();
                        }
                    });
                }
                else {
                    MessageBox.getBox("Khách hàng", "Số điện thoại phải đủ 10 ký tự", Alert.AlertType.WARNING).show();
                }
            } catch (NumberFormatException ex) {
                MessageBox.getBox("Khách hàng", "Số điện thoại nhập không hợp lệ", Alert.AlertType.WARNING).show();
            }
        }
    }

    public void closeView(ActionEvent evt) {
        stageOut = (Stage) sceneVBox.getScene().getWindow();
        stageOut.close();
    }
}
