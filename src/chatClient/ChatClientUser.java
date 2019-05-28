package chatClient;

import dependencies.lib.User;
import userHandleDesktop.UserDao;

import java.math.BigInteger;
import java.sql.SQLException;

public class ChatClientUser extends User {
    int messageCount = 0;
    BigInteger key;

    public ChatClientUser(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userHandle = user.getUserHandle();
    }

    public static User getUserFromDatabase(String userHandle) throws SQLException, ClassNotFoundException {
        return UserDao.getUserInformation(userHandle);
    }

    public BigInteger getKey() {
        return key;
    }

    public void setKey(BigInteger key) {
        this.key = key;
    }

    public void addNewMessage() {
        this.messageCount++;
    }

    public void resetMessageCount() {
        this.messageCount = 0;
    }

    public int getMessageCount() {
        return messageCount;
    }

    @Override
    public String toString() {
        return "ChatClientUser{" + super.toString() + ", messages=" + messageCount + "}";
    }
}
