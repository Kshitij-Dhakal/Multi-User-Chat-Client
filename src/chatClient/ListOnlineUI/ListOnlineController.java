package chatClient.ListOnlineUI;

import dependencies.lib.UserBean;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListOnlineController {
    ListOnlineModel model;
    ListOnlineView view;
    private UserBean user;

    public ListOnlineController(UserBean user) {
        this.user = user;
        view = new ListOnlineView();
        model = new ListOnlineModel(view, this.user.getUserHandle());
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int index = view.getList().getSelectedIndex();
                    UserBean selectedValue = view.getList().getSelectedValue();
                    selectedValue.resetMessage();
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
