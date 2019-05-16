package chatClient;

import chatClient.controllers.ClientChatController;
import dependencies.Listeners.MessageListener;

public class MessageHandler implements MessageListener {
    ClientChatController controller;

    @Override
    public void onMessage(String fromLogin, String messageText) {
        System.out.println("MessageHandler : onMessage");
        controller = ClientChatFactory.getClientChat(fromLogin);
        controller.getView().receive(messageText);
        //TODO add message handler
    }

    @Override
    public void online(String login) {
//        controller = ClientChatFactory.getClientChat(login);
//        controller.getView().enableSend();
    }

    @Override
    public void offline(String login) {
        //TODO do something fancy when user goes offline instead of hiding window
        controller = ClientChatFactory.getClientChat(login);
//        controller.getView().setVisible(false);
        controller.getView().disableSend();
    }
}
