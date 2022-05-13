package ru.gb.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.gb.server.dto.AuthRequest;
import ru.gb.client.network.NettyClient;

public class ClientController implements Initializable {

    @FXML
    private static HBox startWindow;
    @FXML
    public Button reg;
    @FXML
    public TextField exp;

    @FXML
    private Stage stage;
    @FXML
    public TextField login;
    @FXML
    public PasswordField password;

    private static boolean isReg;

    public static void setIsReg(boolean isReg) {
        ClientController.isReg = isReg;
    }

    @FXML
    public void pressToAuth(ActionEvent actionEvent) throws InterruptedException, IOException {
        AuthRequest request = new AuthRequest(login.getText(),password.getText());
        NettyClient.getChannel().writeAndFlush(request);

    }

    public  void workPlace(boolean is) throws IOException {
        if(is){
            Alert alert = new Alert(Alert.AlertType.NONE, "Wrong login or password. Try again");
            alert.show();
        }else{
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "красава");
//            alert.showAndWait();
            ClientApp.setRoot("work");
        }
    }
    @FXML
    public void pressToReg(ActionEvent actionEvent) throws IOException {
        ClientApp.setRoot("reg");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerRegistry.register(this);
        Platform.runLater(() -> {
            stage = (Stage) reg.getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                        stage.close();
                        NettyClient.getChannel().close();
                        NettyClient.getEventLoopGroup().shutdownGracefully();
                }
            });
        });
    }
}
