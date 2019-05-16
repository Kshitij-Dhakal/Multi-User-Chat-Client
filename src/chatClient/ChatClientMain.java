package chatClient;

import chatClient.controllers.ListOnlineController;
import dependencies.Listeners.LoginListener;
import userHandleDesktop.UserHandleController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatClientMain implements LoginListener {

    private static ChatClient localhost;
    private UserHandleController userHandleController;
    private MessageHandler messageHandler;

    public ChatClientMain() {
        messageHandler = new MessageHandler();
        userHandleController = new UserHandleController();
        userHandleController.setListener(this);
        localhost = new ChatClient("localhost", 8818, userHandleController);
        localhost.addMessageListener(messageHandler);
        localhost.addUserStatusListener(messageHandler);
        localhost.setLoginListener(this);
    }

    public static void main(String[] args) {
        new ChatClientMain();
    }

    @Override
    public void onDatabaseLogin() {
        try {
            String userHandle = userHandleController.getUserHandle();
            localhost.login(userHandle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if login is successful ListOnline is created and userHandle is disposed
    }

    @Override
    public void onChatServerLogin() {
        new ListOnlineController(userHandleController.getUserHandle()) {{
            localhost.addUserStatusListener(this.getModel());
        }};
        userHandleController.getView().dispose();
    }

    static class SendAction implements ActionListener {
        String to;
        JTextField messageText;

        SendAction(String to, JTextField messageText) {
            this.to = to;
            this.messageText = messageText;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                localhost.send("send " + to + " " + messageText.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
