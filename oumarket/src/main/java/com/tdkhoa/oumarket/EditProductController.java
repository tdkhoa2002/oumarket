/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import static com.tdkhoa.oumarket.PrimaryController.pRow;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pojo.Category;
import pojo.Product;
import services.CategoryService;
import services.ProductService;

/**
 *
 * @author Khoa Tran
 */
public class EditProductController implements Initializable {
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtUnit;
    
    @FXML private ComboBox<Category> cbCategories;
    static ProductService pS = new ProductService();
    
    
    @Override
    public void initialize (URL url, ResourceBundle rb) {
        CategoryService s = new CategoryService();
        txtName.setText(pRow.getName());
        System.out.println(pRow.getName());
        try {
//            txtName.setText(product.getName());
            List<Category> cates = s.getCategories(null);
            this.cbCategories.setItems(FXCollections.observableList(cates));
        } catch (SQLException ex) {
            Logger.getLogger(EditProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleUpdateProduct(ActionEvent event) throws SQLException {
        pRow.setName(this.txtName.getText());
        pRow.setCategoryId(this.cbCategories.getSelectionModel().getSelectedItem().getId());
        pRow.setQuantity(Integer.parseInt(this.txtQuantity.getText()));
        pRow.setPrice(Double.parseDouble(this.txtPrice.getText()));
        pRow.setUnit(this.txtUnit.getText());
        pS.editProduct(pRow);
    }
}
