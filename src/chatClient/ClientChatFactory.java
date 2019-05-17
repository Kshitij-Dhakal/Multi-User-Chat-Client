package chatClient;

import chatClient.messageUI.MessageController;
import userHandleDesktop.UI.UserHandleModel;

import java.util.ArrayList;

public class ClientChatFactory {
    static ArrayList<ClientChatContainer> clientChatList = new ArrayList<>();

    public static MessageController getClientChat(String key) {
        for (ClientChatContainer clientChatContainer : clientChatList) {
            if (clientChatContainer.key.equalsIgnoreCase(key)) {
//                System.out.println("Returning existing controller");
                return clientChatContainer.value;
            }
        }
        MessageController value = new MessageController() {{
            setUser(new UserHandleModel() {{

            }});
            addActionListener(new ChatClientMain.SendAction(key, getView().getMessageField(), getView()));
        }};

        clientChatList.add(new ClientChatContainer(key, value));
        System.out.println("Creating new controller");
        return value;
    }

    private static class ClientChatContainer {
        String key;
        MessageController value;

        public ClientChatContainer(String key, MessageController value) {
            this.key = key;
            this.value = value;
        }
    }
}
