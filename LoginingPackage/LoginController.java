package LoginingPackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.ViewController;
import MainMenuPackage.MainMenuController;
import Models.User;
import RegisterPackage.RegisterController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class LoginController implements ViewController {
    User user;
    NetworkManager networkManager;
    MainCoordinator coordinator;
    @FXML
    ProgressIndicator progressIndicator;
    @FXML
    ImageView logoImage;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button continueButton;
    public LoginController(NetworkManager networkManager, MainCoordinator coordinator) {
        this.coordinator = coordinator;
        this.networkManager = networkManager;
        networkManager.setController(this);
    }

    @FXML
    void continueButtonTapped(ActionEvent event) throws IOException, ClassNotFoundException {
        if (loginTextField.getText().equals("") || passwordTextField.getText().equals("")) {
            showFailAlert("Введены не все поля!");
        } else {
            logoImage.setVisible(false);
            progressIndicator.setVisible(true);
            new Thread(() -> {
                try {
                    user = networkManager.requestUser(loginTextField.getText(), passwordTextField.getText());
                    if (user != null) {
                        user.setPassword(passwordTextField.getText());
                        coordinator.setUser(user);
                        Stage window = (Stage) continueButton.getScene().getWindow();
                        Platform.runLater(() -> {
                            try {
                                coordinator.goToMainPage(window);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                } catch (Exception e) {
                    showFailAlert(e.getLocalizedMessage());
                }
            }).start();
        }
    }
    @FXML
    void registerButtonTapped(ActionEvent event) throws IOException {
        Stage window = (Stage) continueButton.getScene().getWindow();
        coordinator.goToRegisterPage(window);
    }

    @Override
    public void showFailAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("");
        alert.setContentText(message);
        logoImage.setVisible(true);
        progressIndicator.setVisible(false);
        alert.showAndWait();
    }
}