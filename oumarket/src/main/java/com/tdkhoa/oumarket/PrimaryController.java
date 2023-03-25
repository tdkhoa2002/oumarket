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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.Category;
import pojo.Product;
import services.ProductService;
import utils.MessageBox;

public class PrimaryController implements  Initializable {
    static ProductService s = new ProductService();
    @FXML TableView<Product> tbProducts;
    @FXML ComboBox<Category> cbCategories;
    @FXML private Button btnAddSP;
//    @FXML private TextField txtSearch;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
//            List<Category> cates = s.getCategories();
//            this.cbCategories.setItems(FXCollections.observableList(cates));
            
            this.loadTableColumns();
            this.loadTableData(null);
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void viewAddProduct(ActionEvent evt) {
        this.btnAddSP.setOnAction(event -> { 
            try {
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Tạo sản phẩm");
                stage.show();
            } catch (IOException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void loadTableColumns() {
        TableColumn colId = new TableColumn("Mã SP");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn colName = new TableColumn("Tên SP");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colCate = new TableColumn("Danh mục");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryId"));
        
        TableColumn colPrice = new TableColumn("Giá tiền");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        TableColumn colUnit = new TableColumn("Đơn vị");
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));
        
        TableColumn colQuantity = new TableColumn("Số lượng");
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        
//        TableColumn colDel = new TableColumn();
//        colDel.setCellFactory(r -> {
//            Button btn = new Button("Delete");
//            
//            btn.setOnAction(evt -> {
//                Alert a = MessageBox.getBox("Question", 
//                        "Are you sure to delete this question?", 
//                        Alert.AlertType.CONFIRMATION);
//                a.showAndWait().ifPresent(res -> {
//                    if (res == ButtonType.OK) {
//                        Button b = (Button)evt.getSource();
//                        TableCell cell = (TableCell) b.getParent();
//                        Question q = (Question) cell.getTableRow().getItem();
//                        try {
//                            if (s.deleteQuestion(q.getId())) {
//                                MessageBox.getBox("Question", "Delete successful", Alert.AlertType.INFORMATION).show();
//                                this.loadTableData(null);
//                            } else
//                                MessageBox.getBox("Question", "Delete failed", Alert.AlertType.WARNING).show();
//                                
//                         } catch (SQLException ex) {
//                              MessageBox.getBox("Question", ex.getMessage(), Alert.AlertType.WARNING).show();
//                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        
//                    }
//                });
//            });
//            
//            TableCell c = new TableCell();
//            c.setGraphic(btn);
//            return c;
//        });
        
        this.tbProducts.getColumns().addAll(colId, colName, colCate, colPrice, colQuantity, colUnit);
    }
    

    private void loadTableData(String kw) throws SQLException {
        
        List<Product> pros = s.getProducts(kw);
        
        this.tbProducts.getItems().clear();
        this.tbProducts.setItems(FXCollections.observableList(pros));
    }
}
