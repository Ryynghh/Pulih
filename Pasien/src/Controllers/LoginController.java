package Controllers;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import Models.User;
import Models.UserList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;

public class LoginController implements Initializable{
    private Stage stage;
    private Scene scene;
    
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField psPassword;
    @FXML
    private Button pindah;


    @FXML
    public void handleLogin() {
        String email = tfEmail.getText();
        String password = psPassword.getText();

    if (email.isEmpty() || password.isEmpty()) {
        showAlert("Email atau Password tidak boleh kosong!");
        return;
    }

    UserList userList = loadUsers();

    for (User user : userList.getUsers()) {
        if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
            showAlert("Login Berhasil!");
            return;
        }
    }

    showAlert("Email atau Password salah!");
    
    }


    public void Daftar(ActionEvent event) throws IOException{
        stage = (Stage) pindah.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Register.fxml"));
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    private UserList loadUsers() {
        File file = new File("src/Database/Users.xml");
        if (!file.exists()) return new UserList();

        XStream xstream = new XStream(new StaxDriver());
        xstream.allowTypes(new Class[] { User.class, UserList.class });
        xstream.alias("user", User.class);
        xstream.alias("userlist", UserList.class);
        xstream.processAnnotations(User.class);
        xstream.processAnnotations(UserList.class);

        try (FileReader reader = new FileReader(file)) {
            return (UserList) xstream.fromXML(reader);
        } catch (Exception e) {
            e.printStackTrace();
            return new UserList();
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
