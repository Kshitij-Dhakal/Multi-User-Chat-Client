package chatClient.models;

import chatClient.ClientChatFactory;
import chatClient.controllers.ClientChatController;
import chatClient.views.ListOnlineView;
import dependencies.Listeners.UserStatusListener;

public class ListOnlineModel implements UserStatusListener {
    ListOnlineView view;
    private String userHandle;

    public ListOnlineModel(ListOnlineView view, String userHandle) {
        this.view = view;
        this.userHandle = userHandle;
    }

    @Override
    public void online(String login) {
        view.getListModel().addElement(login);
    }

    @Override
    public void offline(String login) {
        view.getListModel().removeElement(login);
    }

    public ClientChatController getClientChatController(String sendTo) {
        ClientChatController clientChat = ClientChatFactory.getClientChat(sendTo);
//        clientChat.getView().setVisible(true);
        clientChat.getView().enableSend();
        return clientChat;
    }
}
