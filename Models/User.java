package Models;

import Interfaces.Model;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class User implements Model {
    long id;
    String login;
    String name;
    String surname;
    String password;
    String role;
    boolean isAdmin;
    public User() {}
    public User(String login, String password, String name, String surname, long id, String role, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.role = role;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }
    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
    public boolean getAdmin() {
        return isAdmin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    @Override
    public void fromJsonString(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(jsonString);
            this.password = (String) object.get("password");
            this.name = (String) object.get("name");
            this.surname = (String) object.get("surname");
            this.login = (String) object.get("login");
            this.id = (long) object.get("id");
            this.role = (String) object.get("role");
            this.isAdmin = (boolean) object.get("isAdmin");
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("login", this.login);
        object.put("name", this.name);
        object.put("surname", this.surname);
        object.put("id", this.id);
        object.put("password", getCodedPassword(this.password));
        object.put("role", this.role);
        object.put("isAdmin", this.isAdmin);
        return object;
    }

    private String getCodedPassword(String password) {
        return String.valueOf(password.hashCode());
    }
}
