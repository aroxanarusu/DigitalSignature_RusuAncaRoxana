module com.example.rsadss {
    requires javafx.controls;
    requires javafx.fxml;
    requires javax.xml.bind;


    opens com.example.rsadss to javafx.fxml;
    exports com.example.rsadss;
}