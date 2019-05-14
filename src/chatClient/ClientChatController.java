package chatClient;

public class ClientChatController {
    ClientChatModel model;
    ClientChatView view;

    public ClientChatController() {
        view = new ClientChatView();
        model = new ClientChatModel();
        model.setChatView(this.view);

    }

    public static void main(String[] args) {
        new ClientChatController();
    }

    public ClientChatModel getModel() {
        return model;
    }

    public void setModel(ClientChatModel model) {
        this.model = model;
    }

    public ClientChatView getView() {
        return view;
    }

    public void setView(ClientChatView view) {
        this.view = view;
    }
}
