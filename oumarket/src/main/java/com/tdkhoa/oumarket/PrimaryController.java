package com.tdkhoa.oumarket;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pojo.Category;
import pojo.Customer;
import pojo.Employee;
import pojo.Order;
import pojo.OrderDetails;
import pojo.Product;
import pojo.Promotion;
import services.PromotionService;
import services.CategoryService;
import services.CustomerService;
import services.OrderDetailsService;
import services.OrderService;
import services.ProductService;
import utils.MessageBox;

public class PrimaryController implements Initializable {

    static ProductService pS = new ProductService();
    static CategoryService cS  = new CategoryService();
    static Product pRow = new Product ();
    static OrderDetailsService oDS = new OrderDetailsService();
    static OrderService oS = new OrderService();
    static CustomerService cusS = new CustomerService();
    static PromotionService promoService = new PromotionService();
    
    @FXML TableView<Product> tbProducts;
    @FXML TableView<Product> tbShowProducts;
    
    @FXML TableView<OrderDetails> tbShowOrdersDetail;
    ObservableList<OrderDetails> cartItems = FXCollections.observableArrayList();
    double total = cartItems.stream().mapToDouble(OrderDetails::getTotal).sum();
    
    @FXML TableView<Category> tbCategories;
    @FXML TableView<Employee> tbEmployees;
    @FXML TableView<Order> tbOrders;
    @FXML TableView<Customer> tbCustomers;
    @FXML ComboBox<Category> cbCategories;
    @FXML TextField txtTotal;
    @FXML TextField txtPhone;
    @FXML private Button btnAddSP;
    @FXML private Button btnAddCate;
    @FXML private Button btnAddCustomer;
//    @FXML private TextField txtSearch;
    @FXML private VBox sceneVBox;
    Stage stageOut;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadTableProductsColumns();
            this.loadTableCategoriesColumns();
            this.loadTableEmployeesColumns();
            this.loadTableShowProductsColumns();
            this.loadTableShowOrdersDetailColumns();
            this.loadTableOrdersColumns();
            this.loadTableCustomersColumns();
            
            this.loadProductsData(null);
            this.loadCategoriesData(null);
            this.loadCustomerData(null);
            this.loadOrdersData();
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
    
    public void viewAddCustomer(ActionEvent evt) throws SQLException {
        this.btnAddCustomer.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/setCustomer.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Thêm khách hàng");
                
                stage.setOnHidden(e -> {                       //xử lý khi sự kiện stage đóng lại
                    try {
                        loadCustomerData(null);
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
    
    
    //Hien thi cot trong bang
    private void loadTableShowOrdersDetailColumns() {
        TableColumn<OrderDetails, String> colName = new TableColumn<>("Tên SP");
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        colName.setPrefWidth(170);

        TableColumn<OrderDetails, Integer> colQuantity = new TableColumn<>("Số lượng");
        colQuantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        
        TableColumn<OrderDetails, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        
        TableColumn<OrderDetails, Double> colPriceDiscount = new TableColumn<>("Price Discounted");
        colPriceDiscount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduct().getPriceDiscount()).asObject());

        TableColumn<OrderDetails, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal()).asObject());
        
        TableColumn colDel = new TableColumn("Xóa");
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xóa");

            btn.setOnAction(evt -> {
                Button b = (Button) evt.getSource();
                TableCell cell = (TableCell) b.getParent();
                OrderDetails orderDetails = (OrderDetails) cell.getTableRow().getItem();
                
                this.tbShowOrdersDetail.getItems().remove(orderDetails);
                total = cartItems.stream().mapToDouble(OrderDetails::getTotal).sum();
                txtTotal.setText(Double.toString(total));
                this.tbShowOrdersDetail.refresh();
//                System.out.println(this.tbShowOrdersDetail.getItems().remove(p));
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });
        
        this.tbShowOrdersDetail.getColumns().addAll(colName, colQuantity, priceCol,colPriceDiscount, totalCol,colDel);
    }
    
    private void loadTableOrdersColumns() {
        TableColumn colId = new TableColumn("Mã HĐ");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(50);
        
        TableColumn colOrderDate = new TableColumn("Ngày tạo");
        colOrderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));
        
        TableColumn colTotal = new TableColumn("Tổng");
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));
        
        TableColumn colSelect = new TableColumn("Xem");
        colSelect.setCellFactory(r -> {
                Button btnView = new Button("View");

                btnView.setOnAction(event -> {
                    try {
                        Stage stage = new Stage();
                        // Tạo Scene mới
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/viewOrderDetails.fxml"));
                        Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                        stage.setScene(scene);
                        stage.setTitle("Xem chi tiết hóa đơn");
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            TableCell c = new TableCell();
            c.setGraphic(btnView);
            return c;
        });
        
        this.tbOrders.getColumns().addAll(colId, colOrderDate, colTotal, colSelect);
    }
    
    private void loadTableShowProductsColumns() {
        
        TableColumn colId = new TableColumn("Mã SP");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(50);
        
        TableColumn colName = new TableColumn("Tên SP");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(170);

        TableColumn colCate = new TableColumn("Danh mục");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryName"));

        TableColumn colPrice = new TableColumn("Giá tiền");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        TableColumn colUnit = new TableColumn("Đơn vị");
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));

        TableColumn colQuantity = new TableColumn("Số lượng");
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        
        TableColumn colAdd = new TableColumn("Thêm");
        colAdd.setCellFactory(r -> {
            Button btn = new Button("Thêm");
            btn.setOnAction(evt -> {
                Button b = (Button) evt.getSource();
                TableCell cell = (TableCell) b.getParent();
                Product p = (Product) cell.getTableRow().getItem();
                
                Popup popup = new Popup();
                VBox popupContent = new VBox();
                Label quantityLabel = new Label("Số lượng: ");
                TextField quantityTextField = new TextField();
                quantityTextField.setText("0");
                Button saveButton = new Button("Save");
                saveButton.setOnAction(event -> {
                    int quantity = Integer.parseInt(quantityTextField.getText());
                    boolean check = true;
                    //Kiem tra xem san pham do da co trong gio hang hay chua
                    if(!cartItems.isEmpty()) { //Nếu mảng đó co sản phẩm
                        for (OrderDetails cartItem: cartItems) { //Chạy vòng lặp từng sản phẩm
                            if (cartItem.getProduct().getId().equals(p.getId())) { //Nếu sản phẩm vừa thêm vào trùng với sản phẩm đã có
                                cartItem.setQuantity(cartItem.getQuantity()+quantity); //Set lại số lượng tăng lên
                                check = false;
                            }
                        }
                        if(check)
                            cartItems.add(new OrderDetails(p, quantity));
                    }
                    else {
                            // Lưu số lượng sản phẩm vào một biến hoặc gọi một phương thức khác
                            OrderDetails cartItem = new OrderDetails(p, quantity);
                            cartItems.add(cartItem);
                        }
                    total = cartItems.stream().mapToDouble(OrderDetails::getTotal).sum();
                    txtTotal.setText(Double.toString(total));
                    
                    this.tbShowOrdersDetail.setItems(cartItems);
                    this.tbShowOrdersDetail.refresh();
                    // Đóng Popup
                    popup.hide();
                 });
                // Thêm các thành phần vào VBox
                popupContent.getChildren().addAll(quantityLabel, quantityTextField, saveButton);

                // Thiết lập nội dung cho Popup
                popup.getContent().addAll(popupContent);

                // Thiết lập sự kiện cho button
                btn.setOnAction(event -> {
                    // Hiển thị Popup tại vị trí của button
                    popup.show(btn.getScene().getWindow(), 600, 500);
                });
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });
        this.tbShowProducts.getColumns ().addAll(colId, colName, colCate, colPrice, colQuantity, colUnit, colAdd);
    } //Page chọn sản phẩm để thanh toán

    private void loadTableProductsColumns() {
        TableColumn colId = new TableColumn("Mã SP");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(50);

        TableColumn colName = new TableColumn("Tên SP");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(170);

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
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/fixProducts.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Chỉnh sửa sản phẩm");
                
                stage.setOnHidden(e -> {//xử lý khi sự kiện stage đóng lại
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
            
            TableCell c = new TableCell();
            c.setGraphic(btnEdit);
            return c;
         });
        
    this.tbProducts.getColumns ().addAll(colId, colName, colCate, colPrice, colQuantity, colUnit, colEdit, colDel);
   } //Page load tất cả các sản phẩm
    
    private void loadTableEmployeesColumns() {
        TableColumn colId = new TableColumn("Mã nhân viên");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn colName = new TableColumn("Họ tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
    } //Page load tất cả nhân viên

    private void loadTableCategoriesColumns() {
        TableColumn colId = new TableColumn("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(50);
        
        TableColumn colName = new TableColumn("Tên danh mục");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(170);
        
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
    } //Page load tất cả danh mục
    
    private void loadTableCustomersColumns() {
        TableColumn colId = new TableColumn("Mã KH");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn colName = new TableColumn("Họ tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colBirth = new TableColumn("Ngày Sinh");
        colBirth.setCellValueFactory(new PropertyValueFactory("ngaySinh"));
        
        TableColumn colPhone = new TableColumn("SĐT");
        colPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        
        TableColumn colPoint = new TableColumn("Điểm");
        colPoint.setCellValueFactory(new PropertyValueFactory("point"));
        
        this.tbCustomers.getColumns().addAll(colId, colName, colBirth, colPhone, colPoint);
    } //Page load tất cả thông tin khách hàng
    
    
    //Load du lieu
    
    private void loadProductsData(String kw) throws SQLException {
        
        List<Product> pros = pS.getProducts(kw);
        
        this.tbProducts.getItems().clear();
        this.tbProducts.setItems(FXCollections.observableList(pros));
        
        this.tbShowProducts.getItems().clear();
        this.tbShowProducts.setItems(FXCollections.observableList(pros));
    }
    
    private void loadCategoriesData(String kw) throws SQLException {
        List<Category> cates = cS.getCategories(kw);
        
        this.tbCategories.getItems().clear();
        this.tbCategories.setItems(FXCollections.observableList(cates));
    }
    
    private void loadCustomerData(String kw) throws SQLException {
        List<Customer> customers = cusS.getCustomers(kw);
        
        this.tbCustomers.getItems().clear();
        this.tbCustomers.setItems(FXCollections.observableList(customers));
    }
    
    private void loadOrdersData() throws SQLException {
        List<Order> orders = oS.getOrders();
        
        this.tbOrders.getItems().clear();
        this.tbOrders.setItems(FXCollections.observableList(orders));
    }
    
    public void closeView(ActionEvent evt) {
        stageOut = (Stage) sceneVBox.getScene().getWindow();
        stageOut.close();
    }
    
    public void savePay() throws SQLException {
        Order o = new Order();
        List<Customer> customers = cusS.getCustomers(null);
        String phone = txtPhone.getText();
        double tongTien = Double.parseDouble(txtTotal.getText());
        if(!phone.isEmpty()) { //Nếu txtPhone không rỗng
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime purchaseDate = LocalDateTime.now();
            String formattedDateTime = purchaseDate.format(formatter);
            String purchaseDateSS = formattedDateTime.substring(0,5);
            System.out.println(purchaseDateSS);
            Customer customer = cusS.findCustomerByPhoneNumber(customers, phone);
            if(customer != null) {
                String customerBirthDay = customer.getNgaySinh().substring(0,5);
                if(customerBirthDay.equals(purchaseDateSS) && tongTien >= 1000000) {
                    double discount = tongTien * 0.1;
                    o.setTotal(tongTien - discount);
                }
                else {
                    o.setTotal(Double.parseDouble(txtTotal.getText()));
                }
            }
            else {
                return;
            }
        }
        else  //Nếu txtPhone rỗng
            o.setTotal(Double.parseDouble(txtTotal.getText()));
        
        oS.addOrder(o);
        ObservableList<OrderDetails> orderDetailsList = this.tbShowOrdersDetail.getItems();
        boolean tmp = false;
        for (OrderDetails oD : orderDetailsList) {
            if(oDS.saveOderDetails(oD, o)) {
                tmp = true;
            }
            else {
                tmp = false;
            }
        }
        if(tmp == true)
            MessageBox.getBox("Hóa đơn", "Thêm hóa đơn thành công ", Alert.AlertType.CONFIRMATION).show();
        else 
            MessageBox.getBox("Hóa đơn", "Thêm hóa đơn thất bại", Alert.AlertType.ERROR).show();
        this.tbShowOrdersDetail.getItems().clear();
    }
}
