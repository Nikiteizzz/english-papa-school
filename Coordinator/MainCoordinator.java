package Coordinator;

import DataManagers.NetworkManager;
import InviteCodesPackage.InviteCodesController;
import LoginingPackage.LoginController;
import MainMenuPackage.MainMenuController;
import Models.User;
import RegisterPackage.RegisterController;
import SettingsMenuPackage.SettingsMenuController;
import StudentsPackage.StudentsController;
import TimetablePackage.TimetableController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainCoordinator extends Application {
    User user;
    NetworkManager networkManager;

    public User getUser() {
        return user;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNetworkManager(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        user = new User();
        networkManager = new NetworkManager();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("LoginingPackage/LoginViewDescription.fxml"));
        fxmlLoader.setController(new LoginController(networkManager, this));
        Scene scene = new Scene(fxmlLoader.load());
        networkManager.startServer();
        stage.setTitle("Авторизация");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void goToMainPage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MainMenuPackage/MainMenuViewDescription.fxml"));
        fxmlLoader.setController(new MainMenuController(networkManager, user, this));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Главное меню");
        stage.setScene(scene);
    }

    public void goToRegisterPage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("RegisterPackage/RegisterViewDescription.fxml"));
        fxmlLoader.setController(new RegisterController(networkManager, this));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Регистрация");
        stage.setScene(scene);
    }

    public void goToLoginPage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoginingPackage/LoginViewDescription.fxml"));
        loader.setController(new LoginController(networkManager, this));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Авторизация");
        stage.setScene(scene);
    }

    public void goToSettingsPage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("SettingsMenuPackage/SettingsMenuViewDescription.fxml"));
        loader.setController(new SettingsMenuController(user, networkManager, this));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Настройки");
        stage.setScene(scene);
    }

    public void goToTimetable(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("TimetablePackage/TimetableViewDescription.fxml"));
        loader.setController(new TimetableController(user, networkManager, this));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Расписание");
        stage.setScene(scene);
    }

    public void goToStudentsPage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("StudentsPackage/StudentsViewDescription.fxml"));
        loader.setController(new StudentsController(networkManager, user, this));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Список студентов");
        stage.setScene(scene);
    }

    public void goToInviteCodes(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("InviteCodesPackage/InviteCodesViewDescription.fxml"));
        loader.setController(new InviteCodesController(networkManager, user, this));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Инвайт коды");
        stage.setScene(scene);
    }
}