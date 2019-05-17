package chatClient.messageUI;

import userHandleDesktop.UI.UserHandleModel;

import java.awt.event.ActionListener;

public class MessageController {

    private MessageModel model;
    private MessageView view;
    private UserHandleModel user = new UserHandleModel();

    public MessageController() {
        this.view = new MessageView();
        this.model = new MessageModel(this.view);
    }

    public static void main(String[] args) {
        new MessageController() {{
            getView().send("Just Doing Fine");
            getView().receive("Tell me, how's your life going?");
        }};
    }

    public MessageView getView() {
        return view;
    }

    public void setUser(UserHandleModel user) {
        this.user = user;
        this.getView().setTitle(this.user.getUserHandle());
    }

    public void addActionListener(ActionListener listener) {
        this.view.addActionListener(listener);
    }
}
