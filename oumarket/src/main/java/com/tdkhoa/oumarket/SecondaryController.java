package com.tdkhoa.oumarket;

import static com.tdkhoa.oumarket.PrimaryController.s;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import pojo.Product;
import utils.MessageBox;

public class SecondaryController {

    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtUnit;
    
//    public void addProductHandler(ActionEvent evt) {
//        Double price = Double.parseDouble(txtPrice.getText());
//        Integer quantity = Integer.parseInt(this.txtQuantity.getText());
//        Product p = new Product(this.txtName.getText(),
//                this.cbCategories.getSelectionModel().getSelectedItem().getId(), price, quantity,this.txtUnit.getText());
//        try {
//            if (s.addProduct(p)) {
//                this.loadTableData(null);
//                MessageBox.getBox("Product", "Add product successful", Alert.AlertType.INFORMATION).show();
//            }
//        } catch (SQLException ex) {
//            MessageBox.getBox("Product", "Add product failed", Alert.AlertType.ERROR).show();
//            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}