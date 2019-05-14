package chatClient;

import userHandleDesktop.LoginListener;
import userHandleDesktop.UserHandleController;

import java.awt.event.WindowEvent;

public class ChatClientMain implements LoginListener {

    private UserHandleController userHandleController;

    public ChatClientMain() {
        userHandleController = new UserHandleController();
        userHandleController.addLoginListener(this);
    }

    public static void main(String[] args) {
        new ChatClientMain();
    }

    @Override
    public void onLogin() {
        //on login close login window
        System.out.println("On Login");
        new ListOnlineController(userHandleController.getUserHandle());
//        userHandleController.getView().dispatchEvent(new WindowEvent(userHandleController.getView(), WindowEvent.WINDOW_CLOSING));
    }
}
