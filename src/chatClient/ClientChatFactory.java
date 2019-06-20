package chatClient;

import chatClient.messageUI.MessageController;
import userHandleDesktop.UI.UserHandleModel;

import java.util.HashMap;
import java.util.Map;

public class ClientChatFactory {
    static Map<String, MessageController> chatContainerMap = new HashMap<>();

    public static MessageController getClientChat(String key) {
        MessageController value;
        if (chatContainerMap.containsKey(key)) {
            value = chatContainerMap.get(key);
        } else {
            value = new MessageController() {{
                setUser(new UserHandleModel() {{

                }});
                addActionListener(new ChatClientMain.SendAction(key, getView().getMessageField(), getView()));
            }};
            chatContainerMap.put(key, value);
        }

        return value;
    }
}
