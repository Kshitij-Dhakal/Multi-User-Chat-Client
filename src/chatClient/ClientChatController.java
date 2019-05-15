package chatClient;

public class ClientChatController {

    private ClientChatModel model;
    private ClientChatView view;

    public ClientChatController() {
        this.view = new ClientChatView();
        this.model = new ClientChatModel(this.view);
    }

    public static void main(String[] args) {
        new ClientChatController();
    }
}
