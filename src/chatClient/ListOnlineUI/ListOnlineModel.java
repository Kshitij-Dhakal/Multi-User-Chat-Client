package chatClient.ListOnlineUI;

import chatClient.ClientChatFactory;
import chatClient.messageUI.MessageController;
import dependencies.Listeners.MessageListener;
import dependencies.lib.UserBean;

import java.util.Iterator;

public class ListOnlineModel implements MessageListener {
    ListOnlineView view;
    private String userHandle;

    public ListOnlineModel(ListOnlineView view, String userHandle) {
        this.view = view;
        this.userHandle = userHandle;
    }

    @Override
    public void online(UserBean login) {
        view.getListModel().addElement(login);
    }

    @Override
    public void offline(UserBean login) {
        //FIXME implementation of handle offline
        System.out.println("ListOnlineModel : Trying to remove " + login);
        Iterator<UserBean> iterator = view.getListModel().elements().asIterator();
        while (iterator.hasNext()) {
            UserBean user = iterator.next();
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
        Iterator<UserBean> iterator = view.getListModel().elements().asIterator();
        while (iterator.hasNext()) {
            UserBean user = iterator.next();
            if (user.getUserHandle().equals(fromLogin)) {
                user.addMessage();
                view.getListModel().setElementAt(user, index);
            }
            index++;
        }
    }

    @Override
    public void onSend() {

    }

}
