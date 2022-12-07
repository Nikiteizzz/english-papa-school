package DataManagers;

import Interfaces.Controller;
import Models.Lesson;
import Models.Student;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdk.jshell.execution.LoaderDelegate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class NetworkManager {
    private String ip = "127.0.0.1";
    private int port = 12345;
    private DataOutputStream serverOutputStream;
    private DataInputStream serverInputStream;
    private Controller controller;
    private boolean isConnected = false;
    Socket socket;
    public Controller getController() {
        return controller;
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }
    public NetworkManager() { }
    public void startServer() {
        try {
            this.socket = new Socket(ip, port);
            this.serverOutputStream = new DataOutputStream(this.socket.getOutputStream());
            this.serverInputStream = new DataInputStream(this.socket.getInputStream());
            isConnected = true;
        } catch (Exception e) {
            controller.showFailAlert("Ошибка: " + e.getLocalizedMessage());
        }
    }
    public User requestUser(String login, String password) throws IOException {
        String request = "", result;
        if (isConnected) {
            request += "login@" + login + "@" + password;
            serverOutputStream.writeUTF(request);
            result = serverInputStream.readUTF();
            if (!result.equals("")) {
                User user = new User();
                user.fromJsonString(result);
                return user;
            } else {
                controller.showFailAlert("Перепроверьте логин или пароль!");
                return null;
            }
        } else {
            controller.showFailAlert("Нет подключения к серверу!");
            return null;
        }
    }
    public ObservableList<Lesson> getLessons(long teacherId) throws IOException, ParseException {
        LocalDate localDate = LocalDate.now();
        ObservableList<Lesson> lessons = FXCollections.observableArrayList();
        Calendar calendar = Calendar.getInstance();
        String request = "get@lessons@" + teacherId + "@" + localDate.getDayOfWeek();
        serverOutputStream.writeUTF(request);
        String result = serverInputStream.readUTF();
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

    public ObservableList<Lesson> getLessonsForDay(long teacherId, String dayOfWeek) throws IOException, ParseException {
        ObservableList<Lesson> lessons = FXCollections.observableArrayList();
        String request = "get@lessons@" + teacherId + "@" + dayOfWeek;
        serverOutputStream.writeUTF(request);
        String result = serverInputStream.readUTF();
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


    public boolean updateUser(User user) throws IOException {
        String requestString = "update@" + user.toJsonObject().toJSONString(), response;
        serverOutputStream.writeUTF(requestString);
        response = serverInputStream.readUTF();
        if (response.equals("Success")) {
            return true;
        } else {
            controller.showFailAlert(response);
            return false;
        }
    }

    public User requestRegistration(User user, String inviteCode) throws IOException {
        String request = "registration@" + user.toJsonObject().toJSONString() + "@" + inviteCode;
        serverOutputStream.writeUTF(request);
        String response = serverInputStream.readUTF();
        if (!response.equals("Success")) {
            controller.showFailAlert(response);
            return null;
        } else {
            User newUser = requestUser(user.getLogin(), user.getPassword());
            return newUser;
        }
    }

    public ObservableList<Student> requestStudents() throws IOException, ParseException {
        String request = "get@students";
        ObservableList<Student> students = FXCollections.observableArrayList();
        serverOutputStream.writeUTF(request);
        String result = serverInputStream.readUTF();
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
}
