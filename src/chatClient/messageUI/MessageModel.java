package chatClient.messageUI;

public class MessageModel {

    private MessageView view;

    public MessageModel(MessageView view) {
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
