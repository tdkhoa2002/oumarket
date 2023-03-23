module com.tdkhoa.oumarket {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.tdkhoa.oumarket to javafx.fxml;
    exports com.tdkhoa.oumarket;
}
