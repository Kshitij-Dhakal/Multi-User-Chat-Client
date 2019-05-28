package chatClient;

import chatClient.messageUI.MessageController;
import dependencies.Listeners.MessageListener;
import dependencies.lib.User;
import des.Des;

public class MessageHandler implements MessageListener {
    MessageController controller;

    @Override
    public void onMessage(String fromLogin, String messageText) {
        System.out.println("MessageHandler : onMessage");
        controller = ClientChatFactory.getClientChat(fromLogin);
        controller.getView().receive(Des.decrypt(ChatClient.getKeys(fromLogin), messageText));
        //TODO add message handler
    }

    @Override
    public void online(User login) {
//        controller = ClientChatFactory.getClientChat(login);
//        controller.getView().enableSend();
        controller = ClientChatFactory.getClientChat(login.getUserHandle());
        controller.getView().enableSend();
    }

    @Override
    public void offline(User login) {
        //TODO do something fancy when user goes offline instead of hiding window
        controller = ClientChatFactory.getClientChat(login.getUserHandle());
//        controller.getView().setVisible(false);
        controller.getView().disableSend();
    }
}
