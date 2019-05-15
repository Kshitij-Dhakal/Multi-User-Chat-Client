package chatClient;

import java.util.ArrayList;

public class ClientChatFactory {
    static ArrayList<ClientChatContainer> clientChatList;

    public static ClientChatController getClientChat(String key) {
        for (ClientChatContainer clientChatContainer : clientChatList) {
            if (clientChatContainer.key.equalsIgnoreCase(key)) {
                return clientChatContainer.value;
            }
        }
        ClientChatController value = new ClientChatController();
        clientChatList.add(new ClientChatContainer(key, value));
        return value;
    }

    private static class ClientChatContainer {
        String key;
        ClientChatController value;

        public ClientChatContainer(String key, ClientChatController value) {
            this.key = key;
            this.value = value;
        }
    }
}
