package ru.gb.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ru.gb.server.dto.AuthRequest;
import ru.gb.client.network.NettyClient;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class WorkController implements Initializable {


    @FXML
    public TextField addressServer;
    @FXML
    public TableView<FileInfo> serDir;
    @FXML
    public TableView<FileInfo> clientDir;
    @FXML
    public TextField addressClient;


    public void test(ActionEvent actionEvent) {
        AuthRequest authRequest = new AuthRequest("a", "b");
        NettyClient.getChannel().writeAndFlush(authRequest);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ControllerRegistry.register(this);
        new GetFileInfo(serDir, Paths.get("./files/srfiles"), addressServer);
        new GetFileInfo(clientDir, Paths.get("./files/clfiles"), addressClient);

    }
}
