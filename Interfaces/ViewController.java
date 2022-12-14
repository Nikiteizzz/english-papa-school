package Interfaces;

import Coordinator.MainCoordinator;
import javafx.scene.control.Alert;

public interface ViewController {
    default void showFailAlert(String message) {
        MainCoordinator coordinator;
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
