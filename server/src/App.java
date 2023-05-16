import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Listening on port 8080");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(bytes)) != -1) {
                String message = new String(bytes, 0, bytesRead);
                System.out.println(message);
            }
        }
    }
}