package TimetablePackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.ViewController;
import MainMenuPackage.MainMenuController;
import Models.Lesson;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TimetableController implements Initializable, ViewController {
    NetworkManager networkManager;
    User user;
    MainCoordinator coordinator;
    public TableView<Lesson> timeTable;
    public TableColumn<Lesson, Long> numberColumn;
    public TableColumn<Lesson, String> nameColumn;
    public TableColumn<Lesson, String> groupColumn;
    public TableColumn<Lesson, Long> cabinetColumn;

    public MenuButton dayOfWeekPicker;
    ObservableList<Lesson> lessonsList = FXCollections.observableArrayList();
    public TimetableController(User user, NetworkManager networkManager, MainCoordinator mainCoordinator) {
        this.user = user;
        this.networkManager = networkManager;
        this.coordinator = mainCoordinator;
        networkManager.setController(this);
    }
    public void goBack(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) timeTable.getScene().getWindow();
        coordinator.setUser(user);
        coordinator.goToMainPage(window);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("lessonName"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        cabinetColumn.setCellValueFactory(new PropertyValueFactory<>("cabinet"));
        timeTable.setItems(lessonsList);
    }

    public void requestMonday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "MONDAY");
        timeTable.setItems(lessonsList);
        dayOfWeekPicker.setText("Понедельник");
        timeTable.refresh();
    }

    public void requestTuesday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "TUESDAY");
        timeTable.setItems(lessonsList);
        dayOfWeekPicker.setText("Вторник");
        timeTable.refresh();
    }

    public void requestWednesday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "WEDNESDAY");
        timeTable.setItems(lessonsList);
        dayOfWeekPicker.setText("Среда");
        timeTable.refresh();
    }

    public void requestThursday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "THURSDAY");
        timeTable.setItems(lessonsList);
        dayOfWeekPicker.setText("Четверг");
        timeTable.refresh();
    }

    public void requestFriday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "FRIDAY");
        timeTable.setItems(lessonsList);
        dayOfWeekPicker.setText("Пятница");
        timeTable.refresh();
    }

    public void requestSaturday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "SATURDAY");
        dayOfWeekPicker.setText("Суббота");
        timeTable.setItems(lessonsList);
        timeTable.refresh();
    }
}
