package LoginingPackage;

import Controller.MainController;
import RegisterPackage.RegisterView;
import com.sun.tools.javac.Main;

public class LoginController {
    User user;
    LoginView loginView;
    MainController mainController;
    LoginController() {
        this.mainController = new MainController();
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void tryToLogin() {
        if (user.getUsername().equals("Nikiteizzz") && user.getPassword().equals("14122313")) {
            loginView.showSuccessAlert("Вход выполнен!");
        } else {
            loginView.showErrorAlert("Неверные логин или пароль!");
        }
    }

    public void tryToRegister() throws Exception {
        mainController.goToRegisterPage();
    }
}
