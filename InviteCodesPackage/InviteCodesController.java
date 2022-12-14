package InviteCodesPackage;

import Coordinator.MainCoordinator;
import DataManagers.NetworkManager;
import Interfaces.ViewController;
import Models.InviteCode;
import Models.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class InviteCodesController implements ViewController, Initializable {
    NetworkManager networkManager;
    User user;
    MainCoordinator coordinator;
    public TableView<InviteCode> codesTableView;
    public TableColumn<InviteCode, String> codeColumn;
    public TableColumn<InviteCode, Boolean> statusColumn;
    public TextField codeTextField;
    public CheckBox adminCheckBox;
    ObservableList<InviteCode> inviteCodes = FXCollections.observableArrayList();

    public InviteCodesController(NetworkManager networkManager, User user, MainCoordinator coordinator) {
        this.networkManager = networkManager;
        this.user = user;
        this.coordinator = coordinator;
        networkManager.setController(this);
    }

    public void goToMain(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) codeTextField.getScene().getWindow();
        coordinator.setUser(user);
        coordinator.goToMainPage(window);
    }

    public void addNewCode(ActionEvent actionEvent) {

    }

    public void generateCode(ActionEvent actionEvent) {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 14; i++) {
            if (i == 4 || i == 9) {
                code+= '-';
            } else {
                code += (char)(random.nextInt(26) + 'A');
            }
        }
        codeTextField.setText(code);
    }

    public void deleteCode(ActionEvent actionEvent) {

    }

    public void changeCode(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("inviteCode"));
        statusColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getHighStatus()));
        new Thread(() -> {
            try {
                inviteCodes = networkManager.requestInviteCodes();
                Platform.runLater(() -> {
                    codesTableView.setItems(inviteCodes);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showFailAlert(e.getLocalizedMessage());
                });
            }
        }).start();
    }
}
