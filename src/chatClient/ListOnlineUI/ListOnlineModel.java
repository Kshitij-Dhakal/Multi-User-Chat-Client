package chatClient.ListOnlineUI;

import chatClient.ChatClientUser;
import chatClient.ClientChatFactory;
import chatClient.messageUI.MessageController;
import dependencies.Listeners.MessageListener;
import dependencies.lib.User;

import java.util.Iterator;

public class ListOnlineModel implements MessageListener {
    ListOnlineView view;
    private String userHandle;

    public ListOnlineModel(ListOnlineView view, String userHandle) {
        this.view = view;
        this.userHandle = userHandle;
    }

    @Override
    public void online(User login) {
        view.getListModel().addElement(new ChatClientUser(login));
    }

    @Override
    public void offline(User login) {
        Iterator<ChatClientUser> iterator = view.getListModel().elements().asIterator();
        while (iterator.hasNext()) {
            ChatClientUser user = iterator.next();
            if (user.getUserHandle().equals(login.getUserHandle())) {
                view.getListModel().removeElement(user);
            }
        }
    }

    public MessageController getClientChatController(String sendTo) {
        MessageController clientChat = ClientChatFactory.getClientChat(sendTo);
//        clientChat.getView().setVisible(true);
        clientChat.getView().enableSend();
        return clientChat;
    }

    @Override
    public void onMessage(String fromLogin, String messageText) {
        int index = 0;
        Iterator<ChatClientUser> iterator = view.getListModel().elements().asIterator();
        while (iterator.hasNext()) {
            ChatClientUser user = iterator.next();
            if (user.getUserHandle().equals(fromLogin)) {
                user.addNewMessage();
                view.getListModel().setElementAt(user, index);
            }
            index++;
        }
    }
}
