package LoginingPackage;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginView extends Application implements Initializable {
    @FXML
    TextField loginTextField;
    @FXML
    PasswordField passwordTextField;
    LoginController controller;
    Stage stage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }
    public void showScene() throws Exception {
        start(stage);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginViewDescription.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("English Papa");
        stage.show();
    }
    @FXML
    void continueButtonTapped() {
        if (loginTextField.getText().equals("") || passwordTextField.getText().equals("")) {
            showErrorAlert("Не введён логин или пароль!");
        } else {
            User user = new User(loginTextField.getText(), passwordTextField.getText());
            controller.setUser(user);
            controller.tryToLogin();
        }
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

    @FXML
    void registerButtonTapped() throws Exception {
        controller.tryToRegister();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new LoginController();
        controller.setLoginView(this);
    }
}
