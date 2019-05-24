package chatClient.ListOnlineUI;

import chatClient.ChatClientUser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListOnlineController {
    ListOnlineModel model;
    ListOnlineView view;
    private String userHandle;

    public ListOnlineController(String userHandle) {
        this.userHandle = userHandle;
        view = new ListOnlineView();
        model = new ListOnlineModel(view, this.userHandle);
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int index = view.getList().getSelectedIndex();
                    ChatClientUser selectedValue = view.getList().getSelectedValue();
                    selectedValue.resetMessageCount();
                    view.getListModel().setElementAt(selectedValue, index);
                    view.setMessageBox(selectedValue.getUserHandle());
                } catch (Exception e1) {
                    System.err.println("List Empty");
                }
            }
        });

    }

    public ListOnlineModel getModel() {
        return model;
    }

}
