package chatClient;

public interface MessageListener {
    void onMessage(String fromLogin, String messageText);
}
