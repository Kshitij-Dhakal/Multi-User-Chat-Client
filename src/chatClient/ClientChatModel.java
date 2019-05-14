package chatClient;

public class ClientChatModel {
    ClientChatView chatView;

    public ClientChatModel() {
    }

    public ClientChatView getChatView() {
        return chatView;
    }

    public void setChatView(ClientChatView chatView) {
        this.chatView = chatView;
    }

    public void sent(String message) {

    }

    public void received(String message) {

    }
}
