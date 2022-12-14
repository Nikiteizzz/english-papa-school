package DataManagers;

import Interfaces.ViewController;
import Models.InviteCode;
import Models.Lesson;
import Models.Student;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Calendar;

public class NetworkManager {
    private String ip = "127.0.0.1";
    private int port = 12345;
    private ObjectOutputStream serverOutputStream;
    private ObjectInputStream serverInputStream;
    private ViewController viewController;
    private boolean isConnected = false;
    Socket socket;
    public ViewController getController() {
        return viewController;
    }
    public void setController(ViewController controller) {
        this.viewController = controller;
    }
    public NetworkManager() { }
    public void startServer() {
        try {
            this.socket = new Socket(ip, port);
            this.serverOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.serverInputStream = new ObjectInputStream(this.socket.getInputStream());
            isConnected = true;
        } catch (Exception e) {
            viewController.showFailAlert("Ошибка: " + e.getLocalizedMessage());
        }
    }
    public User requestUser(String login, String password) throws IOException, ClassNotFoundException {
        String request = "", result;
        if (isConnected) {
            request += "login@" + login + "@" + getCodedPassword(password);
            serverOutputStream.writeObject(request);
            result = (String)serverInputStream.readObject();
            if (!result.equals("")) {
                User user = new User();
                user.fromJsonString(result);
                return user;
            } else {
                viewController.showFailAlert("Перепроверьте логин или пароль!");
                return null;
            }
        } else {
            viewController.showFailAlert("Нет подключения к серверу!");
            return null;
        }
    }
    private String getCodedPassword(String password) {
        return String.valueOf(password.hashCode());
    }
    public ObservableList<Lesson> getLessons(long teacherId) throws IOException, ParseException, ClassNotFoundException {
        LocalDate localDate = LocalDate.now();
        ObservableList<Lesson> lessons = FXCollections.observableArrayList();
        Calendar calendar = Calendar.getInstance();
        String request = "get@lessons@" + teacherId + "@" + localDate.getDayOfWeek();
        serverOutputStream.writeObject(request);
        String result = (String)serverInputStream.readObject();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(result);
        JSONArray jsonArray = (JSONArray) jsonObject.get("lessons");
        Lesson lesson;
        for (Object jsonObj: jsonArray) {
            lesson = new Lesson();
            lesson.fromJsonString(jsonObj.toString());
            lessons.add(lesson);
        }
        return lessons;
    }

    public ObservableList<Lesson> getLessonsForDay(long teacherId, String dayOfWeek) throws IOException, ParseException, ClassNotFoundException {
        ObservableList<Lesson> lessons = FXCollections.observableArrayList();
        String request = "get@lessons@" + teacherId + "@" + dayOfWeek;
        serverOutputStream.writeObject(request);
        String result = (String)serverInputStream.readObject();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(result);
        JSONArray jsonArray = (JSONArray) jsonObject.get("lessons");
        Lesson lesson;
        for (Object jsonObj: jsonArray) {
            lesson = new Lesson();
            lesson.fromJsonString(jsonObj.toString());
            lessons.add(lesson);
        }
        return lessons;
    }


    public boolean updateUser(User user) throws IOException, ClassNotFoundException {
        String requestString = "update@" + user.toJsonObject().toJSONString();
        serverOutputStream.writeObject(requestString);
        String result = (String)serverInputStream.readObject();
        if (result.equals("Success")) {
            return true;
        } else {
            viewController.showFailAlert(result);
            return false;
        }
    }

    public User requestRegistration(User user, String inviteCode) throws IOException, ClassNotFoundException {
        String request = "registration@" + user.toJsonObject().toJSONString() + "@" + inviteCode;
        serverOutputStream.writeObject(request);
        String result = (String)serverInputStream.readObject();
        if (!result.equals("Success")) {
            viewController.showFailAlert(result);
            return null;
        } else {
            User newUser = requestUser(user.getLogin(), user.getPassword());
            return newUser;
        }
    }
    public ObservableList<Student> requestStudents() throws IOException, ParseException, ClassNotFoundException {
        String request = "get@students";
        ObservableList<Student> students = FXCollections.observableArrayList();
        serverOutputStream.writeObject(request);
        String result = (String)serverInputStream.readObject();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(result);
        JSONArray jsonArray = (JSONArray) jsonObject.get("students");
        Student student;
        for (Object jsonObj: jsonArray) {
            student = new Student();
            student.fromJsonString(jsonObj.toString());
            students.add(student);
        }
        return students;
    }

    public ObservableList<InviteCode> requestInviteCodes() throws IOException, ClassNotFoundException, ParseException {
        String request = "get@invite_codes";
        ObservableList<InviteCode> inviteCodes = FXCollections.observableArrayList();
        serverOutputStream.writeObject(request);
        String result = (String)serverInputStream.readObject();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(result);
        JSONArray jsonArray = (JSONArray) jsonObject.get("invite_codes");
        InviteCode inviteCode;
        for (Object jsonObj: jsonArray) {
            inviteCode = new InviteCode();
            inviteCode.fromJsonString(jsonObj.toString());
            inviteCodes.add(inviteCode);
        }
        return inviteCodes;
    }

    public String deleteInviteCode(long codeId) throws IOException, ClassNotFoundException {
        String request = "delete@invite_code@" + codeId;
        serverOutputStream.writeObject(request);
        String response = (String) serverInputStream.readObject();
        if (response.equals("Success")) {
            viewController.showSuccessAlert("Успешно!");
        } else {
            viewController.showFailAlert(response);
        }
        return response;
    }
}
