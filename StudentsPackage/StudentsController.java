package StudentsPackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.ViewController;
import MainMenuPackage.MainMenuController;
import Models.Student;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentsController implements Initializable, ViewController {
    NetworkManager networkManager;
    User user;
    MainCoordinator coordinator;
    public TableView<Student> studentsTableView;
    public TableColumn<Student, String> surnameColumn;
    public TableColumn<Student, String> nameColumn;
    public TableColumn<Student, String> groupColumn;
    ObservableList<Student> students = FXCollections.observableArrayList();

    public StudentsController(NetworkManager networkManager, User user, MainCoordinator coordinator) {
        this.networkManager = networkManager;
        this.user = user;
        this.coordinator = coordinator;
        networkManager.setController(this);
    }

    public void goToMain(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) studentsTableView.getScene().getWindow();
        coordinator.setUser(user);
        coordinator.goToMainPage(window);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
//        new Thread(() -> {
//            try {
//                students = networkManager.requestStudents();
//                Platform.runLater(() -> {
//                    studentsTableView.setItems(students);
//                });
//            } catch (Exception e) {
//                Platform.runLater(() -> {
//                    showFailAlert(e.getLocalizedMessage());
//                });
//            }
//        }).start();
    }
}
