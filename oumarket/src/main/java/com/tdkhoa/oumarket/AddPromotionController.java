/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import static com.tdkhoa.oumarket.AddCategoryController.cS;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojo.Category;
import pojo.Promotion;
import services.PromotionService;
import utils.MessageBox;

/**
 *
 * @author Khoa Tran
 */
public class AddPromotionController {
    @FXML private VBox sceneVBox;
    @FXML private TextField name;
    @FXML private TextField value;
    @FXML private DatePicker startTime;
    @FXML private DatePicker endTime;
    PromotionService proService = new PromotionService();
     Stage stageOut;
    public void addPromotion (ActionEvent evt) throws SQLException {
        Promotion p = new Promotion();
        p.setName(name.getText());
        p.setValue(Double.parseDouble(value.getText()));
        p.setTimeStart(startTime.getValue());
        p.setTimeEnd(endTime.getValue());
        proService.addPromotion(p);
        Alert a = MessageBox.getBox("Mã khuyến mãi", "Bạn có chắc muốn thêm mã khuyến mãi này không? ", Alert.AlertType.CONFIRMATION);
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
