package RegisterPackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.ViewController;
import LoginingPackage.LoginController;
import MainMenuPackage.MainMenuController;
import Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController implements ViewController {
    NetworkManager networkManager;
    MainCoordinator coordinator;
    public TextField nameTextField;
    public TextField surnameTextField;
    public TextField statusTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField loginTextField;
    @FXML
    TextField inviteCodeTextField;

    public RegisterController(NetworkManager networkManager, MainCoordinator coordinator) {
        this.networkManager = networkManager;
        this.coordinator = coordinator;
        networkManager.setController(this);
    }
    @FXML
    public void cancelRegistration(ActionEvent event) throws IOException {
        Stage window = (Stage) passwordTextField.getScene().getWindow();
        coordinator.goToLoginPage(window);
    }
    @FXML
    public void tryToRegister(ActionEvent event) throws IOException, ClassNotFoundException {
        if (isAllFieldsFilled()) {
            User user = new User();
            user.setLogin(loginTextField.getText());
            user.setPassword(passwordTextField.getText());
            user.setName(nameTextField.getText());
            user.setSurname(surnameTextField.getText());
            user.setRole(statusTextField.getText());
            User newUser = networkManager.requestRegistration(user, inviteCodeTextField.getText());
            if (newUser != null) {
                showSuccessAlert("Регистрация прошла успешно!");
                Stage window = (Stage) loginTextField.getScene().getWindow();
                coordinator.setUser(newUser);
                coordinator.goToMainPage(window);
            } else {
                showFailAlert("Во время регистрации произошла ошибка!");
            }
        }
    }

    private boolean isAllFieldsFilled() {
        return !(nameTextField.getText().equals("") || surnameTextField.getText().equals("") || inviteCodeTextField.getText().equals("") ||
                loginTextField.getText().equals("") || passwordTextField.getText().equals("") || statusTextField.getText().equals(""));
    }
}
