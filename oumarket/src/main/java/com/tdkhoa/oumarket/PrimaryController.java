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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojo.Category;
import pojo.Employee;
import pojo.Product;
import services.CategoryService;
import services.ProductService;
import utils.MessageBox;

public class PrimaryController implements Initializable {

    static ProductService pS = new ProductService();
    static CategoryService cS  = new CategoryService();
    static Product pRow = new Product ();
    @FXML TableView<Product> tbProducts;
    @FXML TableView<Category> tbCategories;
    @FXML TableView<Employee> tbEmployees;
    @FXML ComboBox<Category> cbCategories;
    @FXML private Button btnAddSP;
    @FXML private Button btnAddCate;
//    @FXML private TextField txtSearch;
    @FXML private VBox sceneVBox;
    Stage stageOut;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadTableProductsColumns();
            this.loadTableCategoriesColumns();
            this.loadTableEmployeesColumns();
            this.loadProductsData(null);
            this.loadCategoriesData(null);
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewAddProduct(ActionEvent evt) {
        this.btnAddSP.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/setProducts.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Tạo sản phẩm");
                stage.setOnHidden(e -> {                       //xử lý khi sự kiện stage đóng lại
                    try {
                        loadProductsData(null);
                    } catch (SQLException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                  });
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void viewAddCategory(ActionEvent evt) throws SQLException {
        this.btnAddCate.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/setCategory.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Tạo danh mục");
                
                stage.setOnHidden(e -> {                       //xử lý khi sự kiện stage đóng lại
                    try {
                        loadCategoriesData(null);
                    } catch (SQLException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                  });
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void loadTableProductsColumns() {
        TableColumn colId = new TableColumn("Mã SP");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Tên SP");
        colName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn colCate = new TableColumn("Danh mục");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryName"));

        TableColumn colPrice = new TableColumn("Giá tiền");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        TableColumn colUnit = new TableColumn("Đơn vị");
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));

        TableColumn colQuantity = new TableColumn("Số lượng");
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));

        TableColumn colDel = new TableColumn("Delete");
        colDel.setCellFactory(r -> {
            Button btn = new Button("Delete");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Product",
                        "Are you sure to delete this product?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Product p = (Product) cell.getTableRow().getItem();
                        try {
                            if (pS.deleteProduct(p.getId())) {
                                MessageBox.getBox("Product", "Delete successful", Alert.AlertType.INFORMATION).show();
                                this.loadProductsData(null);
                            } else {
                                MessageBox.getBox("Product", "Delete failed", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Product", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });
        
        TableColumn colEdit = new TableColumn("Edit");
        colEdit.setCellFactory(r -> {
            Button btnEdit = new Button("Edit");

            btnEdit.setOnAction(event -> {
            Button b = (Button) event.getSource();
            TableCell cell = (TableCell) b.getParent();
            pRow = (Product) cell.getTableRow().getItem();
            try {
                pS.editProduct(pRow);
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/fixProducts.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Chỉnh sửa sản phẩm");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }   catch (SQLException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
            
            TableCell c = new TableCell();
            c.setGraphic(btnEdit);
            return c;
         });
        
    this.tbProducts.getColumns ().addAll(colId, colName, colCate, colPrice, colQuantity, colUnit, colDel, colEdit);
   }
    
    private void loadProductsData(String kw) throws SQLException {
        
        List<Product> pros = pS.getProducts(kw);
        
        this.tbProducts.getItems().clear();
        this.tbProducts.setItems(FXCollections.observableList(pros));
    }
    
    private void loadCategoriesData(String kw) throws SQLException {
        List<Category> cates = cS.getCategories(kw);
        
        this.tbCategories.getItems().clear();
        this.tbCategories.setItems(FXCollections.observableList(cates));
    }

    private void loadTableEmployeesColumns() {
        TableColumn colId = new TableColumn("Mã nhân viên");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn colName = new TableColumn("Họ tên");
        colId.setCellValueFactory(new PropertyValueFactory("name"));
    }

    private void loadTableCategoriesColumns() {
        TableColumn colId = new TableColumn("Mã danh mục");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        TableColumn colName = new TableColumn("Tên danh mục");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colDel = new TableColumn("Delete");
        colDel.setCellFactory(r -> {
            Button btn = new Button("Delete");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Category",
                        "Are you sure to delete this category?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Category c = (Category) cell.getTableRow().getItem();
                        try {
                            if (cS.deleteCategory(c.getId())) {
                                MessageBox.getBox("Category", "Delete successful", Alert.AlertType.INFORMATION).show();
                                this.loadCategoriesData(null);
                            } else {
                                MessageBox.getBox("Category", "Delete failed", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Category", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });
        
        TableColumn colEdit = new TableColumn("Edit");
        colEdit.setCellFactory(r -> {
            Button btnEdit = new Button("Edit");

            btnEdit.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/fixProducts.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Chỉnh sửa sản phẩm");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
            
            TableCell c = new TableCell();
            c.setGraphic(btnEdit);
            return c;
         });
        
         this.tbCategories.getColumns ().addAll(colId, colName, colDel, colEdit);
    }
    
    public void closeView(ActionEvent evt) {
        stageOut = (Stage) sceneVBox.getScene().getWindow();
        stageOut.close();
    }
}
