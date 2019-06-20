package chatClient;

import chatClient.ListOnlineUI.ListOnlineController;
import chatClient.messageUI.MessageView;
import dependencies.Listeners.LoginListener;
import dependencies.UI.ProgressWindow;
import des.Des;
import des.RSA;
import userHandleDesktop.UI.UserHandleController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatClientMain implements LoginListener {
    ProgressWindow progressWindow = new ProgressWindow(6);
    private static ChatClient localhost;
    ChatClientUser currentLogin;
    private UserHandleController userHandleController;
    private MessageHandler messageHandler;

    public ChatClientMain() {
        //TODO handle progress window from ChatClientMain to show dbconnection, chatserverconnection and key exchange progress
        messageHandler = new MessageHandler();
        userHandleController = new UserHandleController();
        userHandleController.setListener(this);
        localhost = new ChatClient("localhost", 8818);
        localhost.addMessageListener(messageHandler);
        localhost.setLoginListener(this);
    }

    public static void main(String[] args) {
        new ChatClientMain();
    }

    @Override
    public void onDatabaseLogin() {
        progressWindow.setVisible(true);
        progressWindow.addProgress("Connecting to Chat Server");
        try {
            currentLogin = new ChatClientUser(userHandleController.getModel());
            localhost.login(currentLogin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if login is successful ListOnline is created and userHandle is disposed
    }

    @Override
    public void onChatServerLogin() {
        localhost.setRsa(new RSA(progressWindow));
        progressWindow.dispose();
        new ListOnlineController(currentLogin.getUserHandle()) {{
            localhost.addMessageListener(this.getModel());
        }};
        userHandleController.getView().dispose();
    }

    static class SendAction implements ActionListener {
        String to;
        JTextField messageText;
        MessageView view;

        SendAction(String to, JTextField messageText, MessageView view) {
            this.to = to;
            this.messageText = messageText;
            this.view = view;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String message = messageText.getText().trim();
                localhost.send("send " + to + " " + Des.encrypt(ChatClient.getKeys(to), message));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
