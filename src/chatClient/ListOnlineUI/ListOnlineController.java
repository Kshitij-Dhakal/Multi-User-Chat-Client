package chatClient.ListOnlineUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ListOnlineController {
    ListOnlineModel model;
    ListOnlineView view;
    private String userHandle;

    public ListOnlineController(String userHandle) {
        this.userHandle = userHandle;
        view = new ListOnlineView();
        model = new ListOnlineModel(view, this.userHandle);
        view.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.getClientChatController(view.getList().getSelectedValue());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    public ListOnlineModel getModel() {
        return model;
    }
}
