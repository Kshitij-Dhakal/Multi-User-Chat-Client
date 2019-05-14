package chatClient;

import java.io.IOException;

public class ListOnlineModel implements UserStatusListener {
    ListOnlineView view;
    private String userHandle;

    public ListOnlineModel(ListOnlineView view, String userHandle) {
        this.view = view;
        this.userHandle = userHandle;
        ChatClient localhost = new ChatClient("localhost", 8818);
        if (localhost.connect()) {
            Thread serverListener = new Thread() {
                @Override
                public void run() {
                    try {
                        localhost.listenServer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            serverListener.start();
        } else {
            System.err.println("Connection Failed!");
        }
        try {
            localhost.send("login " + this.userHandle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        localhost.addUserStatusListener(this);
    }

    @Override
    public void online(String login) {
        view.getListModel().addElement(login);
    }

    @Override
    public void offline(String login) {
        view.getListModel().removeElement(login);
    }
}
