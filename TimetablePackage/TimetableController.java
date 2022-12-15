package TimetablePackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.ViewController;
import MainMenuPackage.MainMenuController;
import Models.Lesson;
import Models.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    public Button printButton;
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

    public void toPDFFile(ActionEvent actionEvent) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("/Users/nikitakhorosko/Documents/TimeTable.pdf"));
        document.open();
        BaseFont fontTitle = BaseFont.createFont("/WorkFiles/Comic Sans MS.ttf", "cp1251",BaseFont.EMBEDDED);
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        Paragraph title = new Paragraph("Ваше расписание на " + dayOfWeekPicker.getText(), new Font(fontTitle, 25));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(32);
        document.add(title);

        Paragraph paragraphName = new Paragraph(user.getSurname() + " " +user.getName(), new Font(fontTitle, 20));
        paragraphName.setAlignment(Element.ALIGN_CENTER);
        paragraphName.setSpacingAfter(32);
        document.add(paragraphName);

        PdfPTable timetable = new PdfPTable(4);
        PdfPCell lessonNumber = new PdfPCell(new Phrase("Номер урока", new Font(fontTitle, 15)));
        lessonNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
        timetable.addCell(lessonNumber);

        PdfPCell lessonName = new PdfPCell(new Phrase("Название", new Font(fontTitle, 15)));
        lessonName.setHorizontalAlignment(Element.ALIGN_CENTER);
        timetable.addCell(lessonName);

        PdfPCell group = new PdfPCell(new Phrase("Группа", new Font(fontTitle, 15)));
        group.setHorizontalAlignment(Element.ALIGN_CENTER);
        timetable.addCell(group);

        PdfPCell auditory = new PdfPCell(new Phrase("Аудитория", new Font(fontTitle, 15)));
        auditory.setHorizontalAlignment(Element.ALIGN_CENTER);
        timetable.addCell(auditory);

        timetable.setHeaderRows(1);

        for (Lesson element : lessonsList) {
            timetable.addCell(new Paragraph(String.valueOf(element.getNumber()), new Font(fontTitle, 15)));
            timetable.addCell(new Paragraph(element.getLessonName(), new Font(fontTitle, 15)));
            timetable.addCell(new Paragraph(element.getGroup(), new Font(fontTitle, 15)));
            timetable.addCell(new Paragraph(String.valueOf(element.getCabinet()), new Font(fontTitle, 15)));
        }

        document.add(timetable);
        document.close();

        showSuccessAlert("Успешно сохранено в PDF!");
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
        printButton.setDisable(false);
        timeTable.setItems(lessonsList);
        dayOfWeekPicker.setText("Понедельник");
        numberColumn.setSortType(TableColumn.SortType.ASCENDING);
        timeTable.getSortOrder().setAll(numberColumn);
        timeTable.refresh();
    }

    public void requestTuesday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "TUESDAY");
        printButton.setDisable(false);
        timeTable.setItems(lessonsList);
        dayOfWeekPicker.setText("Вторник");
        numberColumn.setSortType(TableColumn.SortType.ASCENDING);
        timeTable.getSortOrder().setAll(numberColumn);
        timeTable.refresh();
    }

    public void requestWednesday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "WEDNESDAY");
        printButton.setDisable(false);
        timeTable.setItems(lessonsList);
        dayOfWeekPicker.setText("Среда");
        numberColumn.setSortType(TableColumn.SortType.ASCENDING);
        timeTable.getSortOrder().setAll(numberColumn);
        timeTable.refresh();
    }

    public void requestThursday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "THURSDAY");
        printButton.setDisable(false);
        timeTable.setItems(lessonsList);
        dayOfWeekPicker.setText("Четверг");
        numberColumn.setSortType(TableColumn.SortType.ASCENDING);
        timeTable.getSortOrder().setAll(numberColumn);
        timeTable.refresh();
    }

    public void requestFriday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "FRIDAY");
        printButton.setDisable(false);
        timeTable.setItems(lessonsList);
        dayOfWeekPicker.setText("Пятница");
        numberColumn.setSortType(TableColumn.SortType.ASCENDING);
        timeTable.getSortOrder().setAll(numberColumn);
        timeTable.refresh();
    }

    public void requestSaturday(ActionEvent actionEvent) throws IOException, ParseException, ClassNotFoundException {
        lessonsList = networkManager.getLessonsForDay(user.getId(), "SATURDAY");
        printButton.setDisable(false);
        dayOfWeekPicker.setText("Суббота");
        timeTable.setItems(lessonsList);
        numberColumn.setSortType(TableColumn.SortType.ASCENDING);
        timeTable.getSortOrder().setAll(numberColumn);
        timeTable.refresh();
    }
}
