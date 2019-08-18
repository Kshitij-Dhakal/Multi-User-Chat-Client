package chatClient;

import chatClient.ListOnlineUI.ListOnlineController;
import chatClient.messageUI.MessageController;
import dependencies.Listeners.MessageListener;
import dependencies.lib.UserBean;
import des.Des;

public class MessageHandler implements MessageListener {
    MessageController controller;
    ListOnlineController listOnlineController;

    @Override
    public void onMessage(String fromLogin, String messageText) {
        System.out.println("MessageHandler : onMessage");
        controller = ClientChatFactory.getClientChat(fromLogin);
        controller.getView().receive(Des.decrypt(ChatClient.getKeys(fromLogin), messageText));
    }

    @Override
    public void onSend() {
        //when you receive send success message
//        System.out.println("Executing onsend check");
        controller.getModel().send();
    }

    @Override
    public void online(UserBean login) {
//        controller = ClientChatFactory.getClientChat(login);
//        controller.getView().enableSend();
        controller = ClientChatFactory.getClientChat(login.getUserHandle());
        controller.getView().enableSend();
    }

    @Override
    public void offline(UserBean login) {
        controller = ClientChatFactory.getClientChat(login.getUserHandle());
//        controller.getView().setVisible(false);
        controller.getView().disableSend();
    }
}
