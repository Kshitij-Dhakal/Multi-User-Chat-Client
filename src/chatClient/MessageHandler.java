package chatClient;

import chatClient.controllers.ClientChatController;

public class MessageHandler implements MessageListener {
    ClientChatController controller;

    @Override
    public void onMessage(String fromLogin, String messageText) {
        System.out.println("MessageHandler : onMessage");
        controller = ClientChatFactory.getClientChat(fromLogin);
        controller.getView().receive(messageText);
        //TODO add message handler
    }
}