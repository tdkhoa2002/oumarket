/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojo.Category;
import services.CategoryService;
import services.ProductService;
import utils.MessageBox;

/**
 *
 * @author Khoa Tran
 */
public class AddCategoryController {
    static CategoryService cS = new CategoryService();
    
    @FXML private TextField nameCategory;
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;
    @FXML private VBox sceneVBox;
     Stage stageOut;
    public void addCategory (ActionEvent evt) throws SQLException {
        Category c = new Category();
        c.setName(nameCategory.getText());
        cS.addCategory(c);
        Alert a = MessageBox.getBox("Danh mục", "Bạn có chắc muốn thêm danh mục không? ", Alert.AlertType.CONFIRMATION);
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
