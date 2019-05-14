package chatClient;

import userHandleDesktop.LoginListener;
import userHandleDesktop.UserHandleController;

import java.io.IOException;

public class ChatClientMain implements LoginListener {

    private ChatClient localhost;
    private UserHandleController userHandleController;

    public ChatClientMain() {
        userHandleController = new UserHandleController();
        userHandleController.addLoginListener(this);
        localhost = new ChatClient("localhost", 8818, userHandleController);
    }

    public static void main(String[] args) {
        new ChatClientMain();
    }

    @Override
    public void onLogin() {
        try {
            localhost.login(userHandleController.getUserHandle());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if login is successful ListOnline is created and userHandle is disposed
    }
}
