package MainMenuPackage;

import DataManagers.NetworkManager;
import Interfaces.Controller;
import LoginingPackage.LoginController;
import Models.Lesson;
import Models.User;
import RegisterPackage.RegisterController;
import SettingsMenuPackage.SettingsMenuController;
import TimetablePackage.TimetableController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Controller, Initializable {
    User user;
    NetworkManager networkManager;
    public Label userInfoLabel;
    public TableView<Lesson> lessonsTable;
    public TableColumn<Lesson, Long> numberColumn;
    public TableColumn<Lesson, String> lessonColumn;
    public TableColumn<Lesson, Long> groupColumn;
    public TableColumn<Lesson, Long> cabinetColumn;
    ObservableList<Lesson> lessons = FXCollections.observableArrayList();
    public MainMenuController(NetworkManager networkManager, User user) {
        this.networkManager = networkManager;
        this.user = user;
        networkManager.setController(this);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) userInfoLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoginingPackage/LoginViewDescription.fxml"));
        loader.setController(new LoginController(networkManager));
        Scene scene = new Scene(loader.load());
        window.setTitle("Авторизация");
        window.setScene(scene);
    }
    public void goToSettings(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) userInfoLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("SettingsMenuPackage/SettingsMenuViewDescription.fxml"));
        loader.setController(new SettingsMenuController(user, networkManager));
        Scene scene = new Scene(loader.load());
        window.setTitle("Настройки");
        window.setScene(scene);
    }
    public void goToTimetable(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) userInfoLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("TimetablePackage/TimetableViewDescription.fxml"));
        loader.setController(new TimetableController(user, networkManager));
        Scene scene = new Scene(loader.load());
        window.setTitle("Расписание");
        window.setScene(scene);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        lessonColumn.setCellValueFactory(new PropertyValueFactory<>("lessonName"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        cabinetColumn.setCellValueFactory(new PropertyValueFactory<>("cabinet"));
        userInfoLabel.setAlignment(Pos.CENTER);
        userInfoLabel.setText(user.getName() + " " +user.getSurname());
        try {
            lessons = networkManager.getLessons(user.getId());
            lessonsTable.setItems(lessons);
        } catch (Exception e) {
            showFailAlert(e.getLocalizedMessage());
        }
    }
}
