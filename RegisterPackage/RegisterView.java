package RegisterPackage;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterView extends Application implements Initializable {
    Stage stage = new Stage();
    RegisterController registerController;
    @FXML
    TextField loginTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField inviteCodeTextField;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("RegisterViewDescription.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("English Papa");
        stage.show();
    }

    public void showScene() throws Exception {
        start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    void tryToRegister() {
        if (loginTextField.getText().equals("") || passwordTextField.getText().equals("") || inviteCodeTextField.getText().equals("")) {
            showErrorAlert("Введены не все поля!");
        } else {
            UserRegistrationModel userRegistrationModel = new UserRegistrationModel(loginTextField.getText(), passwordTextField.getText(), inviteCodeTextField.getText());
            registerController.userRegistrationModel = userRegistrationModel;
            registerController.tryToRegister();
        }
    }
    @FXML
    void cancelRegistration() throws Exception {
        registerController.goBack();
    }

    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешно!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerController = new RegisterController();
        registerController.registerView = this;
    }
}
