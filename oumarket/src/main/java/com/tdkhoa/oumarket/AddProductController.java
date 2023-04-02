/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import pojo.Product;
import utils.MessageBox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojo.Category;
import services.CategoryService;
import services.ProductService;
/**
 *
 * @author Khoa Tran
 */
public class AddProductController implements Initializable {
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtUnit;
    @FXML private ComboBox<Category> cbCategories;
    @FXML private VBox sceneVBox;
     Stage stageOut;
    
    static ProductService pS = new ProductService();
    
    @Override
    public void initialize (URL url, ResourceBundle rb) {
        CategoryService s = new CategoryService();
        try {
            List<Category> cates = s.getCategories(null);
            this.cbCategories.setItems(FXCollections.observableList(cates));
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addProductHandler (ActionEvent evt) {
        Product p = new Product(this.txtName.getText(),
                this.cbCategories.getSelectionModel().getSelectedItem().getId(), Double.parseDouble(this.txtPrice.getText()),
                Integer.parseInt(this.txtQuantity.getText()), this.txtUnit.getText());
        try {
            if (pS.addProduct(p)) {
                Alert a = MessageBox.getBox("Sản phẩm", "Thêm sản phẩm thành công ", Alert.AlertType.INFORMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        stageOut = (Stage) sceneVBox.getScene().getWindow();
                        stageOut.close();
                    }
                });
            }
        } catch (SQLException ex) {
            MessageBox.getBox("Product", "Add product failed", Alert.AlertType.ERROR).show();
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
