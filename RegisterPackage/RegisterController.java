package RegisterPackage;

import Controller.MainController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController {
    UserRegistrationModel userRegistrationModel;
    MainController mainController;
    RegisterView registerView;

    RegisterController() {
        this.mainController = new MainController();
    }

    public UserRegistrationModel getUserRegistrationModel() {
        return userRegistrationModel;
    }
    public void setUserRegistrationModel(UserRegistrationModel userRegistrationModel) {
        this.userRegistrationModel = userRegistrationModel;
    }

    public void goBack() throws Exception {
        mainController.goToLoginPage();
    }

    public void tryToRegister() {
        if (userRegistrationModel.login.equals("Nikiteizzz") && userRegistrationModel.password.equals("14122313") && userRegistrationModel.inviteCode.equals("1111")) {
            registerView.showSuccessAlert("Регистрация прошла успешно!");
        } else {
            registerView.showErrorAlert("Перепроверьте инвайт код!");
        }
    }
}
