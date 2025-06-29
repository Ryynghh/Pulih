package Models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("user")
public class User {
    private String email, password;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //getter setter email
    public String getEmail() {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    //getter setter password
    public String getPassword() {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Email: " + email);
        stringBuilder.append("Password: " + password);
        return stringBuilder.toString();
    }
}
