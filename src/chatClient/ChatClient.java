package chatClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("Localhost", 8818);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("login sandesh\n".getBytes());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                String line;
                while ((line = scanner.nextLine()) != null) {
                    line += "\n";
                    try {
                        outputStream.write(line.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split(" ");
            if (tokens[0].equalsIgnoreCase("kick")) {
                outputStream.write("exit".getBytes());
                break;
            } else {
                System.out.println(line);
            }
        }
        socket.close();
        thread.interrupt();
    }
}
