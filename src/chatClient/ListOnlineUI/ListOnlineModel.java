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

    /**
     * When Alice receives reply Kb from Bob she saves her key as K=Kb^Xa mod P
     *
     * @param userHandle
     * @param Ka
     */
    public void onKeyReceive(String userHandle, String Ka) {

    }

    /**
     * When Bob recieves Ka from Alice he saves his key as K=Ka^Xb mod P and sends Alice Kb=g^Xb mod P
     *
     * @param userHandle
     * @param Ka
     */
    public void onKeyReply(String userHandle, String Ka) {

    }

    @Override
    public void online(UserBean login) {
        view.getListModel().addElement(login);
    }

    @Override
    public void offline(UserBean login) {
        //FIXME implementation of handle offline
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
