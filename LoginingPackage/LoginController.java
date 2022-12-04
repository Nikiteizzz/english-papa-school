package LoginingPackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.Controller;
import MainMenuPackage.MainMenuController;
import Models.User;
import RegisterPackage.RegisterController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Controller {
    User user;
    NetworkManager networkManager;
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
    public LoginController(NetworkManager networkManager) {
        this.networkManager = networkManager;
        networkManager.setController(this);
    }

    @FXML
    void continueButtonTapped(ActionEvent event) throws IOException {
        logoImage.setVisible(false);
        progressIndicator.setVisible(true);
        user = networkManager.requestUser(loginTextField.getText(), passwordTextField.getText());
        if (user != null) {
            user.setPassword(passwordTextField.getText());
            Stage window = (Stage) continueButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainMenuPackage/MainMenuViewDescription.fxml"));
            loader.setController(new MainMenuController(networkManager, user));
            Scene scene = new Scene(loader.load());
            window.setTitle("Главное меню");
            window.setScene(scene);
        }
    }
    @FXML
    void registerButtonTapped(ActionEvent event) throws IOException {
        Stage window = (Stage) continueButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("RegisterPackage/RegisterViewDescription.fxml"));
        loader.setController(new RegisterController(networkManager));
        Scene scene = new Scene(loader.load());
        window.setTitle("Регистрация");
        window.setScene(scene);
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