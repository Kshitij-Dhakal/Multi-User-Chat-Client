package chatClient;

import chatServer.ServerWorker;
import dependencies.Listeners.LoginListener;
import dependencies.Listeners.MessageListener;
import dependencies.Listeners.UserStatusListener;
import userHandleDesktop.UserHandleController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient {
    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();
    private LoginListener listener;
    private String serverName;
    private int serverPort;
    private Socket serverSocket;
    private InputStream serverIn;
    private OutputStream serverOut;

    public ChatClient(String serverName, int serverPort, UserHandleController userHandleController) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        if (connect()) {
            Thread serverListener = new Thread() {
                @Override
                public void run() {
                    try {
                        listenServer();
                    } catch (IOException e) {
                        System.err.println("ChatClient : Server Disconnected!");
                        e.printStackTrace();
                    }
                }
            };
            serverListener.start();
        } else {
            System.err.println("ChatClient : Connection Failed!");
        }
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    public void login(String userHandle) throws IOException {
        send("login " + userHandle);
    }

    private void listenServer() throws IOException {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverIn));
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            String[] tokens = line.split(" ");
            if (tokens[0].equalsIgnoreCase("online")) {
                handleOnlineCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("offline")) {
                handleOfflineCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("message")) {
                handleMessageCommand(line);
            } else if (tokens[0].equalsIgnoreCase("login")) {
                handleLoginCommand(line);
            }
        }
    }

    private void handleLoginCommand(String line) {
        if (line.equalsIgnoreCase(ServerWorker.LOGIN_SUCCESS)) {
            listener.onChatServerLogin();
        }
    }

    private void handleMessageCommand(String line) {
        String[] tokens = line.split(" ", 3);
        if (tokens.length == 3) {
            String from = tokens[1];
            String message = tokens[2];
            for (MessageListener messageListener : messageListeners) {
                System.out.println("Chat Client : Notifying Observers");
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
            System.err.println("Server Down!");
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
