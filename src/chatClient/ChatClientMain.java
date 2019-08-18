package chatClient;

import chatClient.ListOnlineUI.ListOnlineController;
import chatClient.messageUI.MessageView;
import dependencies.Listeners.LoginListener;
import dependencies.UI.ProgressWindow;
import dependencies.lib.Config;
import dependencies.lib.UserBean;
import des.Des;
import des.RSA;
import userHandleDesktop.UI.UserHandleController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatClientMain implements LoginListener {
    static ChatClient localhost;
    ProgressWindow progressWindow = new ProgressWindow(6);
    private UserHandleController userHandleController;
    private MessageHandler messageHandler;

    public ChatClientMain() {
        messageHandler = new MessageHandler();
        userHandleController = new UserHandleController();
        userHandleController.setListener(this);
        localhost = new ChatClient(Config.CHAT_SERVER_URL, Config.PORT);
        localhost.addMessageListener(messageHandler);
        localhost.setLoginListener(this);
    }

    public static void main(String[] args) {
        new ChatClientMain();
    }

    @Override
    public void onLoginButtonEvent(UserBean bean) {
        progressWindow.setVisible(true);
        progressWindow.addProgress("Connecting to Chat Server");
        try {
            localhost.login(bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if login is successful ListOnline is created and userHandle is disposed
    }

    @Override
    public void onChatServerLogin(UserBean bean) {
        localhost.setRsa(new RSA(progressWindow));
        progressWindow.dispose();
        new ListOnlineController(bean) {{
            localhost.addMessageListener(this.getModel());
        }};
        userHandleController.getView().dispose();
    }

    @Override
    public void onRegisterButtonEvent(UserBean bean) {
        progressWindow.setVisible(true);
        progressWindow.addProgress("Connecting to Chat Server");
        try {
            localhost.register(bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if login is successful ListOnline is created and userHandle is disposed
    }

    static class VideoCallAction implements ActionListener {
        String to;

        VideoCallAction(String to) {
            this.to = to;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                localhost.send("video start " + this.to);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
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
                if (!message.equalsIgnoreCase("")) {
                    localhost.send("send " + to + " " + Des.encrypt(ChatClient.getKeys(to), message));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
