package ru.gb.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.gb.client.network.NettyClient;

import java.io.IOException;

/**
 * JavaFX App
 */
public class ClientApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("client"), 960, 480);
        stage.setScene(scene);

        stage.setTitle("FileManager1.0");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                new NettyClient();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        launch();
    }

}