package chatClient;

import java.util.ArrayList;

public class ClientChatFactory {
    static ArrayList<ClientChatContainer> clientChatList = new ArrayList<>();

    public static ClientChatController getClientChat(String key) {
        for (ClientChatContainer clientChatContainer : clientChatList) {
            if (clientChatContainer.key.equalsIgnoreCase(key)) {
                System.out.println("Returning existing controller");
                clientChatContainer.value.getView().setVisible(true);
                return clientChatContainer.value;
            }
        }
        ClientChatController value = new ClientChatController() {{
            setUser(new Users() {{
                setUserHandle(key);
            }});
        }};
        clientChatList.add(new ClientChatContainer(key, value));
        System.out.println("Creating new controller");
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
