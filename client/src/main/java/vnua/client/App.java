package vnua.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Socket socket;
    private TextField textField;
    private Button sendButton;

    @Override
    public void start(Stage stage) throws Exception {
        textField = new TextField();
        sendButton = new Button("Send");

        sendButton.setOnAction(event -> {
            String message = textField.getText();
            sendMessage(message);
        });

        socket = new Socket("localhost", 8080);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(textField, sendButton);

        Scene scene = new Scene(vBox, 400, 300);

        stage.setScene(scene);

        stage.show();

        listenForMessages();
    }

    private void sendMessage(String message) {
        try {
            socket.getOutputStream().write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = socket.getInputStream();
                    byte[] bytes = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(bytes)) != -1) {
                        String message = new String(bytes, 0, bytesRead);
                        textField.appendText(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}