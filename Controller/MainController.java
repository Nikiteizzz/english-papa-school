package Controller;

import LoginingPackage.LoginController;
import LoginingPackage.LoginView;
import LoginingPackage.User;
import RegisterPackage.RegisterView;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public LoginController loginController;
    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goToRegisterPage() throws Exception {
        RegisterView registerView = new RegisterView();
        registerView.showScene();
    }

    public void goToLoginPage() throws Exception {
        LoginView loginView = new LoginView();
        loginView.showScene();
    }

}