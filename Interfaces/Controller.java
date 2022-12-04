package Interfaces;

import javafx.scene.control.Alert;

public interface Controller {
    default void showFailAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }
    default void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешно!");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
