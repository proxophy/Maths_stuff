module my.project {
    requires javafx.fxml;
    requires javafx.controls;
    opens my.project to javafx.graphics;
    exports my.project;
}