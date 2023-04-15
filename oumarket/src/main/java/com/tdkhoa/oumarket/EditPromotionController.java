/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import static com.tdkhoa.oumarket.EditCategoryController.cS;
import static com.tdkhoa.oumarket.PrimaryController.cRow;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import utils.MessageBox;

/**
 *
 * @author Khoa Tran
 */
import static com.tdkhoa.oumarket.PrimaryController.proRow;
import java.sql.Date;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import services.PromotionService;

public class EditPromotionController {

    @FXML
    private VBox sceneVBox;
    @FXML
    private TextField name;
    @FXML
    private TextField value;
    @FXML
    private DatePicker startTime;
    @FXML
    private DatePicker endTime;
    PromotionService proService = new PromotionService();
    Stage stageOut;

    public void editPromotionHandler(ActionEvent evt) {
        if (this.name.getText().isEmpty() || this.value.getText().isEmpty() || this.startTime.getValue() == null || this.endTime.getValue() == null) {
            MessageBox.getBox("Mã khuyến mãi", "Dữ liệu không được để trống", Alert.AlertType.WARNING).show();
        } else {
            if (Integer.parseInt(this.value.getText()) < 0) {
                MessageBox.getBox("Mã khuyến mãi", "Giá trị của mã khuyến mãi phải lớn hơn 0", Alert.AlertType.WARNING).show();
            } else {
                Date startTime = Date.valueOf(this.startTime.getValue());
                Date endTime = Date.valueOf(this.endTime.getValue());
                proRow.setName(this.name.getText());
                proRow.setValue(Double.parseDouble(this.value.getText()));
                proRow.setTimeEnd(endTime);
                proRow.setTimeStart(startTime);
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
                    MessageBox.getBox("Mã khuyến mãi", "Thêm sản phẩm thất bại", Alert.AlertType.ERROR).show();
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
