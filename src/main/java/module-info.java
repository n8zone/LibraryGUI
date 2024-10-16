module com.example.bookgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.xml.dom;


    opens com.example.bookgui to javafx.fxml;
    exports com.example.bookgui;
}