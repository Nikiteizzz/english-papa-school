package EditLessonsPackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.ViewController;
import Models.Lesson;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditLessonsController implements ViewController, Initializable {
    NetworkManager networkManager;
    User user;
    User selectedUser;
    MainCoordinator coordinator;
    public MenuButton teacherSelector;
    public MenuButton daySelector;
    public TableView<Lesson> teachersTable;
    public TableColumn<Lesson, Long> numberColumn;
    public TableColumn<Lesson, String> nameColumn;
    public TableColumn<Lesson, String> groupColumn;
    public TableColumn<Lesson, Long> cabinetColumn;
    public TextField numberTextField;
    public TextField nameTextField;
    public TextField groupTextField;
    public TextField auditoryTextField;
    public Button addButton;
    ObservableList<Lesson> lessons = FXCollections.observableArrayList();
    ObservableList<User> teachers = FXCollections.observableArrayList();

    public EditLessonsController(NetworkManager networkManager, User user, MainCoordinator coordinator) {
        this.networkManager = networkManager;
        this.user = user;
        this.coordinator = coordinator;
        networkManager.setController(this);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) groupTextField.getScene().getWindow();
        coordinator.setUser(user);
        coordinator.goToMainPage(window);
    }

    public void chooseTeacher(ActionEvent actionEvent) {

    }

    public void addLesson(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (isAllFieldsFilled()) {
            Lesson lesson = new Lesson();
            lesson.setNumber(Long.parseLong(numberTextField.getText()));
            lesson.setLessonName(nameTextField.getText());
            lesson.setTeacherId(selectedUser.getId());
            lesson.setGroup(groupTextField.getText());
            lesson.setCabinet(Long.parseLong(auditoryTextField.getText()));
            lesson.setDayOfWeek(daySelector.getText().toLowerCase());
            boolean response = networkManager.addLesson(lesson);
            if (response) {
                new Thread(() -> {
                    String dayOfWeek = switch (daySelector.getText()) {
                        case "Понедельник" -> "MONDAY";
                        case "Вторник" -> "TUESDAY";
                        case "Среда" -> "WEDNESDAY";
                        case "Четверг" -> "THURSDAY";
                        case "Пятница" -> "FRIDAY";
                        case "Суббота" -> "SATURDAY";
                        default -> "ERROR";
                    };
                    try {
                        lessons = networkManager.getLessonsForDay(selectedUser.getId(), dayOfWeek);
                        Platform.runLater(() -> {
                            teachersTable.setItems(lessons);
                        });
                    } catch (Exception e) {
                        showFailAlert(e.getLocalizedMessage());
                    }

                }).start();
            }
        }
    }

    private boolean isAllFieldsFilled() {
        return !(auditoryTextField.getText().equals("") && groupTextField.getText().equals("") &&
                numberTextField.getText().equals("") && nameTextField.getText().equals(""));
    }


    public void deleteLesson(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (teachersTable.getSelectionModel().getSelectedItem() != null) {
            boolean response = networkManager.deleteLesson(teachersTable.getSelectionModel().getSelectedItem().getId());
            if (response) {
                new Thread(() -> {
                    String dayOfWeek = switch (daySelector.getText()) {
                        case "Понедельник" -> "MONDAY";
                        case "Вторник" -> "TUESDAY";
                        case "Среда" -> "WEDNESDAY";
                        case "Четверг" -> "THURSDAY";
                        case "Пятница" -> "FRIDAY";
                        case "Суббота" -> "SATURDAY";
                        default -> "ERROR";
                    };
                    try {
                        lessons = networkManager.getLessonsForDay(selectedUser.getId(), dayOfWeek);
                        Platform.runLater(() -> {
                            teachersTable.setItems(lessons);
                        });
                    } catch (Exception e) {
                        showFailAlert(e.getLocalizedMessage());
                    }

                }).start();
            }
        }
    }

    public void editLesson(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("lessonName"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        cabinetColumn.setCellValueFactory(new PropertyValueFactory<>("cabinet"));
        new Thread(() -> {
            try {
                teachers = networkManager.requestUsers();
                MenuItem menuItem;
                for (User teacher: teachers) {
                    menuItem = new MenuItem(teacher.getSurname() + " " + teacher.getName());
                    MenuItem finalMenuItem = menuItem;
                    menuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            teacherSelector.setText(finalMenuItem.getText());
                            selectTeacher(finalMenuItem.getText().split(" "));
                        }
                    });
                    teacherSelector.getItems().add(menuItem);
                }
            } catch (Exception e) {
                showFailAlert(e.getLocalizedMessage());
            }
        }).start();
    }

    public void chooseMonday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        daySelector.setText("Понедельник");
        lessons = networkManager.getLessonsForDay(selectedUser.getId(), "MONDAY");
        teachersTable.setItems(lessons);
        addButton.setDisable(false);
    }

    public void chooseTuesday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        daySelector.setText("Вторник");
        lessons = networkManager.getLessonsForDay(selectedUser.getId(), "TUESDAY");
        teachersTable.setItems(lessons);
        addButton.setDisable(false);
    }

    public void chooseWednesday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        daySelector.setText("Среда");
        lessons = networkManager.getLessonsForDay(selectedUser.getId(), "WEDNESDAY");
        teachersTable.setItems(lessons);
        addButton.setDisable(false);
    }

    public void chooseThursday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        daySelector.setText("Четверг");
        lessons = networkManager.getLessonsForDay(selectedUser.getId(), "THURSDAY");
        teachersTable.setItems(lessons);
        addButton.setDisable(false);
    }

    public void chooseFriday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        daySelector.setText("Пятница");
        lessons = networkManager.getLessonsForDay(selectedUser.getId(), "FRIDAY");
        teachersTable.setItems(lessons);
        addButton.setDisable(false);
    }

    public void chooseSaturday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        daySelector.setText("Суббота");
        lessons = networkManager.getLessonsForDay(selectedUser.getId(), "SATURDAY");
        teachersTable.setItems(lessons);
        addButton.setDisable(false);
    }

    public void selectTeacher(String[] data) {
        for (User element : teachers) {
            if (data[0].equals(element.getSurname()) && data[1].equals(element.getName())) {
                selectedUser = element;
                System.out.println(selectedUser.getId());
                daySelector.setDisable(false);
            }
        }
    }
}
