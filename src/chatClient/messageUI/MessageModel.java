package chatClient.messageUI;

public class MessageModel {

    private MessageView view;

    public MessageModel(MessageView view) {
        this.view = view;
    }

    public void send() {
        //TODO add send method
        view.send();
    }
}
