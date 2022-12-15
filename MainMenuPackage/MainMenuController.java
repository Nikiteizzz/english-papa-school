package MainMenuPackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.ViewController;
import LoginingPackage.LoginController;
import Models.Lesson;
import Models.User;
import SettingsMenuPackage.SettingsMenuController;
import StudentsPackage.StudentsController;
import TimetablePackage.TimetableController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable, ViewController {
    User user;
    NetworkManager networkManager;
    MainCoordinator coordinator;
    public Label userInfoLabel;
    public Button inviteCodesButton;
    public Button addLessonsButton;
    public TableView<Lesson> lessonsTable;
    public TableColumn<Lesson, Long> numberColumn;
    public TableColumn<Lesson, String> lessonColumn;
    public TableColumn<Lesson, Long> groupColumn;
    public TableColumn<Lesson, Long> cabinetColumn;
    ObservableList<Lesson> lessons = FXCollections.observableArrayList();
    public MainMenuController(NetworkManager networkManager, User user, MainCoordinator coordinator) {
        this.networkManager = networkManager;
        this.user = user;
        this.coordinator = coordinator;
        networkManager.setController(this);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) userInfoLabel.getScene().getWindow();
        coordinator.goToLoginPage(window);
    }
    public void goToSettings(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) userInfoLabel.getScene().getWindow();
        coordinator.setUser(user);
        coordinator.goToSettingsPage(window);
    }
    public void goToTimetable(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) userInfoLabel.getScene().getWindow();
        coordinator.setUser(user);
        coordinator.goToTimetable(window);
    }

    public void showStudents(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) userInfoLabel.getScene().getWindow();
        coordinator.setUser(user);
        coordinator.goToStudentsPage(window);
    }

    public void addLessons(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) userInfoLabel.getScene().getWindow();
        coordinator.setUser(user);
        coordinator.goToEditingLessons(window);
    }

    public void showInviteCodes(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) userInfoLabel.getScene().getWindow();
        coordinator.setUser(user);
        coordinator.goToInviteCodes(window);
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
            numberColumn.setSortType(TableColumn.SortType.ASCENDING);
            lessonsTable.getSortOrder().setAll(numberColumn);
        } catch (Exception e) {
            showFailAlert(e.getLocalizedMessage());
        }
        if (user.getAdmin()) {
            inviteCodesButton.setVisible(true);
            addLessonsButton.setVisible(true);
        }
    }
}
