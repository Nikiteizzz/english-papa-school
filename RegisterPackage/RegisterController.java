package RegisterPackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.Controller;
import LoginingPackage.LoginController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController implements Controller {
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
    public void tryToRegister(ActionEvent event) {

    }
}
