package Coordinator;

import DataManagers.NetworkManager;
import LoginingPackage.LoginController;
import Models.User;
import RegisterPackage.RegisterController;
import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainCoordinator extends Application {
    User user;
    NetworkManager networkManager;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        user = new User();
        networkManager = new NetworkManager();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("LoginingPackage/LoginViewDescription.fxml"));
        fxmlLoader.setController(new LoginController(networkManager));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Авторизация");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}