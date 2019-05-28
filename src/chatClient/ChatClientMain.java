package chatClient;

import chatClient.ListOnlineUI.ListOnlineController;
import chatClient.messageUI.MessageView;
import dependencies.Listeners.LoginListener;
import des.Des;
import userHandleDesktop.UI.UserHandleController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatClientMain implements LoginListener {

    private static ChatClient localhost;
    ChatClientUser currentLogin;
    private UserHandleController userHandleController;
    private MessageHandler messageHandler;

    public ChatClientMain() {
        messageHandler = new MessageHandler();
        userHandleController = new UserHandleController();
        userHandleController.setListener(this);
        localhost = new ChatClient("localhost", 8818, userHandleController);
        localhost.addMessageListener(messageHandler);
        localhost.setLoginListener(this);
    }

    public static void main(String[] args) {
        new ChatClientMain();
    }

    @Override
    public void onDatabaseLogin() {

        try {
            //TODO getmodel from userHandleController and convert into ChatClientUser and pass it to localhost.login
            currentLogin = new ChatClientUser(userHandleController.getModel());
            localhost.login(currentLogin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if login is successful ListOnline is created and userHandle is disposed
    }

    @Override
    public void onChatServerLogin() {
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
                localhost.send("send " + to + " " + Des.encrypt(localhost.getKeys(to), message));
                view.send(message);
                messageText.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
