package chatClient;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient {
    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();
    private String serverName;
    private int serverPort;
    private Socket serverSocket;
    private InputStream serverIn;
    private OutputStream serverOut;

    public ChatClient(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public void listenServer() throws IOException {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverIn));
        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split(" ");
            if (tokens[0].equalsIgnoreCase("online")) {
                handleOnlineCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("offline")) {
                handleOfflineCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("msg")) {
                handleMessageCommand(line);
            }
        }
    }

    private void handleMessageCommand(String line) {
        String[] tokens = line.split(" ", 3);
        if (tokens.length == 3) {
            String from = tokens[1];
            String message = tokens[2];
            for (MessageListener messageListener : messageListeners) {
                messageListener.onMessage(from, message);
            }
        }
    }

    private void handleOnlineCommand(String[] tokens) {
        if (tokens.length == 2) {
            String userHandle = tokens[1];
            for (UserStatusListener userStatusListener : userStatusListeners) {
                userStatusListener.online(userHandle);
            }
        }
    }

    private void handleOfflineCommand(String[] tokens) {
        if (tokens.length == 2) {
            String userHandle = tokens[1];
            for (UserStatusListener userStatusListener : userStatusListeners) {
                userStatusListener.offline(userHandle);
            }
        }
    }

    public void send(String message) throws IOException {
        serverOut.write((message + "\n").getBytes());
    }

    public boolean connect() {
        try {
            this.serverSocket = new Socket(this.serverName, this.serverPort);
            this.serverIn = serverSocket.getInputStream();
            this.serverOut = serverSocket.getOutputStream();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUserStatusListener(UserStatusListener listener) {
        userStatusListeners.add(listener);
    }

    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }
}
