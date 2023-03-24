package com.tdkhoa.oumarket;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Product;
import services.ProductService;

public class PrimaryController {
    static ProductService s = new ProductService();
    @FXML TableView<Product> tbProducts;
//    @FXML private TextField txtSearch;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTableColumns();
//        this.loadTableData(this.txtSearch.getText());
    }
    
    
    private void loadTableColumns() {
        TableColumn colId = new TableColumn("Ma San Pham");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(250);
        
        TableColumn colName = new TableColumn("Ten San Pham");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colCate = new TableColumn("Danh muc");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryId"));
        
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
        
        this.tbProducts.getColumns().addAll(colId, colName, colCate);
    }
    

    private void loadTableData(String kw) throws SQLException {
        
        List<Product> pros = s.getProducts(kw);
        
        this.tbProducts.getItems().clear();
        this.tbProducts.setItems(FXCollections.observableList(pros));
    }
    
    
}
