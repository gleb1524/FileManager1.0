package ru.gb.client;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.gb.client.network.NettyClient;
import ru.gb.server.dto.RegRequest;

public class RegController implements Initializable {

    @FXML
    public TextField login;
    @FXML
    public PasswordField password;
    @FXML
    public static TextField name;
    @FXML
    public TextField surname;
    private static String isRegistration;
    @FXML
    public Button reg;
    public static TextField test;


    public static void setIsRegistration(String isRegistration) {
        RegController.isRegistration = isRegistration;
    }


    public void pressToRegistration(ActionEvent actionEvent) throws IOException {
        if(login.getText().isEmpty()||password.getText().isEmpty()||name.getText().isEmpty()||surname.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fill in all the fields");
            alert.showAndWait();
            return;
        }else{
            RegRequest request = new RegRequest(login.getText(), password.getText(), name.getText(), surname.getText());
            NettyClient.getChannel().writeAndFlush(request);
        }

    }

    public void isReg(boolean reg){
        if(reg){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration ok");
            alert.showAndWait();
        }else{
            login.clear();
            password.clear();
            name.clear();
            surname.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration failed.Try again!");
            alert.showAndWait();
        }

    }

    public void pressToBack(ActionEvent actionEvent) throws IOException, InterruptedException {
        ClientApp.setRoot("client");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerRegistry.register(this);
    }
}