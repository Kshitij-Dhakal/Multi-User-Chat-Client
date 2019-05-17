package chatClient;

import dependencies.User.User;

public class ChatClientUser extends User {
    int messages;

    public ChatClientUser(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userHandle = user.getUserHandle();
    }
}
