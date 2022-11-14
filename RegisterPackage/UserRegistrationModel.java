package RegisterPackage;

public class UserRegistrationModel {
    String login;
    String password;
    String inviteCode;
    UserRegistrationModel(String login, String password, String inviteCode) {
        this.login = login;
        this.password = password;
        this.inviteCode = inviteCode;
    }
}
