package chatClient;

public class ClientChatModel {

    private ClientChatView view;

    public ClientChatModel(ClientChatView view) {
        this.view = view;
    }

    public void send(String message, String to) {
        //TODO add send method
        if (message.isEmpty()) {

        } else {
            view.setMessageText("");
            view.send(message);
        }
    }
}
