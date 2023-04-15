/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import static com.tdkhoa.oumarket.PrimaryController.cRow;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojo.Category;
import services.CategoryService;
import utils.MessageBox;

/**
 *
 * @author Khoa Tran
 */
public class EditCategoryController {
    @FXML private TextField nameCategory;
    Stage stageOut;
    @FXML private VBox sceneVBox;
    static CategoryService cS = new CategoryService();
    
    public void editCategoryHandler (ActionEvent evt) {
        cRow.setName(this.nameCategory.getText());
        try {
            if (cS.editCategory(cRow)) {
                Alert a = MessageBox.getBox("Danh mục", "Sửa danh mục thành công", Alert.AlertType.INFORMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        stageOut = (Stage) sceneVBox.getScene().getWindow();
                        stageOut.close();
                    }
                });
            }
        } catch (SQLException ex) {
            MessageBox.getBox("Sản phẩm", "Thêm sản phẩm thất bại", Alert.AlertType.ERROR).show();
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
