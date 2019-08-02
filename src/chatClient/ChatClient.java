package chatClient;

import chatServer.ServerWorker;
import dependencies.Listeners.LoginListener;
import dependencies.Listeners.MessageListener;
import des.KeyGenerator;
import des.RSA;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatClient {
    static Map<String, KeySheet> keys = new HashMap<>();
    RSA rsa;
    private KeyGenerator keyGenerator = new KeyGenerator();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();
    private LoginListener listener;
    private String serverName;
    private int serverPort;
    private Socket serverSocket;
    private InputStream serverIn;
    private OutputStream serverOut;

    public ChatClient(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        if (connect()) {
            new Thread(() -> {
                try {
                    listenServer();
                } catch (IOException e) {
                    System.err.println("ChatClient : Server Disconnected!");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            System.err.println("ChatClient : Connection Failed!");
        }
    }

    public static BigInteger getKeys(String username) {
        if (keys.containsKey(username)) {
            return keys.get(username).getDh_key();
        } else {
            System.err.println("ChatClient : Key not created yet!");
            return new BigInteger("0");
        }
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    public void setRsa(RSA rsa) {
        this.rsa = rsa;
    }

    public void login(String userhandle, String password) throws IOException {
        send("login " + userhandle + " " + password);
    }

    private void listenServer() throws IOException, SQLException, ClassNotFoundException {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverIn));
        while ((line = bufferedReader.readLine()) != null) {
//            System.out.println("Chat CLient : " + line);
            String[] tokens = line.split(" ");
            if (tokens[0].equalsIgnoreCase("online")) {
                handleOnlineCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("offline")) {
                handleOfflineCommand(tokens);
            } else if (tokens[0].equalsIgnoreCase("message")) {
                handleMessageCommand(line);
            } else if (tokens[0].equalsIgnoreCase("login")) {
                handleLoginCommand(line);
            } else if (tokens[0].equalsIgnoreCase("key")) {
                handleKeyCommand(tokens);
            }
        }
    }

    private void handleKeyCommand(String[] tokens) throws IOException {
        //key @userhandle init rsa_public_variable
        //key @userhandle exchange dh_key signature
        if (tokens.length >= 4) {
            String userHandle = tokens[1];
            if (tokens[2].equalsIgnoreCase("init")) {
                BigInteger rsa_public_variable = new BigInteger(tokens[3], 16);
                if (!keys.containsKey(userHandle)) {
                    keys.put(userHandle, new KeySheet(rsa_public_variable));
                    String Ka = keyGenerator.initializeDHKeyExchange();
                    String sign = rsa.sign(new BigInteger(Ka, 16), keys.get(userHandle).getRsa_public_variable());
//                    System.out.println("ChatClient : sign = " + sign);
                    String keyCommand = "key " + userHandle + " exchange " + sign;
//                    System.out.println("Signed key sent : " + keyCommand);
                    send(keyCommand);
                }
            } else if (tokens[2].equalsIgnoreCase("exchange")) {
                BigInteger public_variable = rsa.verify(tokens[3] + " " + tokens[4], keys.get(userHandle).getRsa_public_variable());
                //public variables sent and received are same
                //send signed key and received signed key are also same
                if (public_variable != null) {
                    keyGenerator.receive(public_variable.toString(16));
                    System.out.println(keyGenerator.getKey());
                    keys.get(userHandle).setDh_key(keyGenerator.getKey());
                } else {
                    System.err.println("ChatClient : Failed to exchange symmetric keys.");
                }
//                KeySheet keySheet = keys.get(userHandle);
//                keySheet.setDh_key(keyGenerator.getKey());
//                keys.put(userHandle, keySheet);
            }
        }
    }

    private void handleLoginCommand(String line) {
        System.out.println(line);
        String[] split = line.split(":");
        if (split[0].equalsIgnoreCase(ServerWorker.LOGIN_SUCCESS)) {
            listener.onChatServerLogin(split[1]);
        }
    }

    private void handleMessageCommand(String line) {
        String[] tokens = line.split(" ", 3);
        if (tokens.length == 3) {
            if (tokens[1].equalsIgnoreCase("sent")) {
                if (tokens[2].equalsIgnoreCase("success")) {
                    //update view only message sent success is received
                    for (MessageListener messageListener : messageListeners) {
                        messageListener.onSend();
                    }
                }
            } else {
                String from = tokens[1];
                String message = tokens[2];
                for (MessageListener messageListener : messageListeners) {
//                System.out.println("Chat Client : Notifying Observers");
                    messageListener.onMessage(from, message);
                }
            }
        }
    }

    private void handleOnlineCommand(String[] tokens) throws SQLException, ClassNotFoundException, IOException {
        if (tokens.length == 2) {
            String userHandle = tokens[1];
            String keyCommand = "key " + userHandle + " init " + rsa.getPublic_variable().toString(16);
            send(keyCommand);
//            System.out.println("Chat Client : " + keyCommand);
            for (MessageListener messageListener : messageListeners) {
                messageListener.online(ChatClientUser.getUserFromDatabase(userHandle));
            }
        }
    }

    private void handleOfflineCommand(String[] tokens) throws SQLException, ClassNotFoundException {
        if (tokens.length == 2) {
            String userHandle = tokens[1];
            keys.remove(userHandle);
            for (MessageListener messageListener : messageListeners) {
                messageListener.offline(ChatClientUser.getUserFromDatabase(userHandle));
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

    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }
}
