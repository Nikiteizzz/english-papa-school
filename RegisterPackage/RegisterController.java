package RegisterPackage;

import DataManagers.NetworkManager;
import Interfaces.Controller;
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

public class RegisterController implements Controller {
    public TextField nameTextField;
    public TextField surnameTextField;
    public TextField statusTextField;
    NetworkManager networkManager;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField loginTextField;
    @FXML
    TextField inviteCodeTextField;

    public RegisterController(NetworkManager networkManager) {
        this.networkManager = networkManager;
        networkManager.setController(this);
    }
    @FXML
    public void cancelRegistration(ActionEvent event) throws IOException {
        Stage window = (Stage) passwordTextField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoginingPackage/LoginViewDescription.fxml"));
        loader.setController(new LoginController(networkManager));
        Scene scene = new Scene(loader.load());
        window.setTitle("Авторизация");
        window.setScene(scene);
    }
    @FXML
    public void tryToRegister(ActionEvent event) throws IOException {
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
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainMenuPackage/MainMenuViewDescription.fxml"));
                loader.setController(new MainMenuController(networkManager, user));
                Scene scene = new Scene(loader.load());
                window.setTitle("Главное меню");
                window.setScene(scene);
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
