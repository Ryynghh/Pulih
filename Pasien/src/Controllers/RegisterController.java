package Controllers;

import javafx.event.ActionEvent;

// import Models.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import Models.User;
import Models.UserList;

public class RegisterController implements Initializable{
    private Stage stage;
    private Scene scene;

    @FXML
    private Button pindah;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField psPassword;

    // Membuat objek xstream
    XStream xstream = new XStream(new StaxDriver());
    

    public RegisterController() {
        xstream.allowTypes(new Class[] { User.class, UserList.class });
        xstream.alias("user", User.class);
        xstream.alias("userlist", UserList.class);
    }

    @FXML
    public void handleRegister() {
        String email = tfEmail.getText();
        String password = psPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Field tidak boleh kosong!");
            return;
        }

        UserList userList = loadUsers();

        for (User user : userList.getUsers()) {
            if (user.getEmail().equals(email)) {
                showAlert("Username sudah terdaftar.");
                return;
            }
        }

        userList.getUsers().add(new User(email, password));
        saveUsers(userList);
        showAlert("Registrasi berhasil!");
    }

    public void Masuk(ActionEvent event) throws IOException{
        stage = (Stage) pindah.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private UserList loadUsers() {
        File file = new File("src/Database/Users.xml");
        if (!file.exists()) {
            return new UserList();
        }

        try (FileReader reader = new FileReader(file)) {
            return (UserList) xstream.fromXML(reader);
        } catch (Exception e) {
            e.printStackTrace();
            return new UserList();
        }
    }

    private void saveUsers(UserList userList) {
        try (FileWriter writer = new FileWriter("src/Database/Users.xml")) {
            String xml = xstream.toXML(userList);
            writer.write(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
