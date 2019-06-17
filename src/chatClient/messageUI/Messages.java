package chatClient.messageUI;

public class Messages {
    boolean isSent;
    boolean isReceived;
    String messageText;

    public String getMessageText() {
        return messageText;
    }

    public void sent(String messageText) {
        this.isSent = true;
        isReceived = false;
        this.messageText = messageText;
    }

    public void recieved(String messageText) {
        this.isSent = false;
        isReceived = true;
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        String s = "Messages[isSent = " + isSent + ", " +
                "isReceived = " + isReceived + ", " +
                "messageText = " + messageText + "]";
        return s;
    }
}
