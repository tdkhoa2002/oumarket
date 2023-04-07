/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import static com.tdkhoa.oumarket.PrimaryController.products;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.OrderDetails;
import pojo.Product;
import services.OrderDetailsService;
import services.OrderService;


/**
 *
 * @author Khoa Tran
 */
public class ViewOrderDetailsController implements Initializable {
    @FXML TextField txtName;
    @FXML TextField txtPrice;
    @FXML TextField txtTienGiam;
    @FXML TextField total;
    @FXML TextField txtTienNhan;
    @FXML TextField txtTienTra;
    @FXML DatePicker orderDate;
    @FXML TableView tbProducts;
    static OrderDetailsService oDS = new OrderDetailsService();
    OrderService oD = new OrderService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTableOrderDetailsColumn();
        try {
            this.loadData();
        } catch (SQLException ex) {
            Logger.getLogger(ViewOrderDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableOrderDetailsColumn() {
        TableColumn colName = new TableColumn("Tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        this.tbProducts.getColumns().addAll(colName);
    }
    
    public void loadData() throws SQLException {
        this.tbProducts.getItems().clear();
        this.tbProducts.setItems(FXCollections.observableList(products));
    }
}
