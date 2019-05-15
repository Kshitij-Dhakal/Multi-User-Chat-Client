package chatClient;

public class MessageHandler implements MessageListener {
    ClientChatController controller;

    @Override
    public void onMessage(String fromLogin, String messageText) {
        controller = ClientChatFactory.getClientChat(fromLogin);
        //TODO add message handler
    }
}
