/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import static com.tdkhoa.oumarket.AddCategoryController.cS;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void addPromotion (ActionEvent evt) throws SQLException, ParseException {
        Date startTime = Date.valueOf(this.startTime.getValue());
        Date endTime = Date.valueOf(this.endTime.getValue());
        Promotion p = new Promotion(this.name.getText(), Double.parseDouble(value.getText()), startTime, endTime);
        Alert a = MessageBox.getBox("Mã khuyến mãi", "Bạn có chắc muốn thêm mã khuyến mãi này không? ", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    try {
                        proService.addPromotion(p);
                    } catch (SQLException ex) {
                        Logger.getLogger(AddPromotionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
