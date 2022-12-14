package SettingsMenuPackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.ViewController;
import MainMenuPackage.MainMenuController;
import Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsMenuController implements Initializable, ViewController  {
    User user;
    NetworkManager networkManager;
    MainCoordinator coordinator;
    public PasswordField passwordField;
    public TextField nameTextField;
    public TextField surnameTextField;
    public TextField statusTextField;
    public TextField loginTextField;
    public Button saveButton;
    public SettingsMenuController(User user, NetworkManager networkManager, MainCoordinator mainCoordinator) {
        this.user = user;
        this.networkManager = networkManager;
        this.coordinator = mainCoordinator;
        networkManager.setController(this);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) passwordField.getScene().getWindow();
        coordinator.setUser(user);
        coordinator.goToMainPage(window);
    }

    public void saveChanges(ActionEvent actionEvent) {
        try {
            if (isAllFieldsFilled()) {
                User buffUser = new User();
                buffUser.setName(nameTextField.getText());
                buffUser.setRole(statusTextField.getText());
                buffUser.setLogin(loginTextField.getText());
                buffUser.setPassword(passwordField.getText());
                buffUser.setSurname(surnameTextField.getText());
                buffUser.setAdmin(this.user.getAdmin());
                buffUser.setId(this.user.getId());
                boolean result = networkManager.updateUser(buffUser);
                if (result) {
                    user = buffUser;
                    showSuccessAlert("Успешно обновлено!");
                }
            } else {
                throw new Exception("Не все поля заполнены!");
            }
        } catch (Exception e) {
            showFailAlert(e.getLocalizedMessage());
        }
    }

    private boolean isAllFieldsFilled() {
        return !(loginTextField.getText().equals("") && passwordField.getText().equals("") && statusTextField.getText().equals("") &&
                surnameTextField.getText().equals("") && nameTextField.getText().equals(""));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordField.setText(user.getPassword());
        loginTextField.setText(user.getLogin());
        nameTextField.setText(user.getName());
        surnameTextField.setText(user.getSurname());
        statusTextField.setText(user.getRole());
    }
}
