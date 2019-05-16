package chatClient;

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
        return ClientChatFactory.getClientChat(sendTo);
    }
}
