package chatClient.controllers;

import chatClient.models.ClientChatModel;
import chatClient.views.ClientChatView;
import chatClient.Users;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientChatController {

    private ClientChatModel model;
    private ClientChatView view;
    private Users user = new Users();

    public ClientChatController() {
        this.view = new ClientChatView();
        this.model = new ClientChatModel(this.view);
        this.view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO find a way to get userHandle
                model.send(view.getMessageText(), "userHandle");
            }
        });
    }

    public static void main(String[] args) {
        new ClientChatController() {{
            getView().send("Just Doing Fine");
            getView().receive("Tell me, how's your life going?");
        }};
    }

    public ClientChatView getView() {
        return view;
    }

    public void setUser(Users user) {
        this.user = user;
        this.getView().setTitle(this.user.getUserHandle());
    }
}
