package chatClient.ListOnlineUI;

import chatClient.ClientChatFactory;
import chatClient.messageUI.MessageController;
import dependencies.lib.UserBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class ListOnlineView extends JFrame {
    private JList<UserBean> list;
    private DefaultListModel<UserBean> listModel = new DefaultListModel<>();
    private JPanel messageBox = new JPanel(new CardLayout());

    public ListOnlineView() {
        JScrollPane scrollPane = getLeftPane();
        scrollPane.setSize(150, 480);
        JPanel panel = (JPanel) getContentPane();
        setLayout(new BorderLayout());

        messageBox.add(new JLabel("Send Message") {{
            setHorizontalAlignment(JLabel.CENTER);
            setForeground(Color.GRAY);
        }}, "empty");


        panel.add(scrollPane, BorderLayout.WEST);
        panel.add(messageBox, BorderLayout.CENTER);
//        JDesktopPane desktopPane = new JDesktopPane();
//        desktopPane.add(scrollPane);
//        setContentPane(desktopPane);
//        getContentPane().add(scrollPane);
        setVisible(true);
        setSize(640, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

    }

    public void setMessageBox(String userHandle) {
        CardLayout layout = (CardLayout) messageBox.getLayout();
        MessageController clientChat = ClientChatFactory.getClientChat(userHandle);
        boolean componentAlreadyExists = false;
        for (Component component : messageBox.getComponents()) {
            if (component.equals(clientChat.getView()))
                componentAlreadyExists = true;
        }
        if (componentAlreadyExists) {
//            System.out.println("Component already exists");
            layout.show(messageBox, userHandle);
        } else {
            System.out.println("ListOnlineView : Creating new component");
            messageBox.add(clientChat.getView(), userHandle);
            layout.show(messageBox, userHandle);
        }
        setTitle(userHandle);
    }

    private JScrollPane getLeftPane() {
        list = new JList<>(listModel);
        list.setCellRenderer(new ListOnlineRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        return new JScrollPane(list);
    }

    public DefaultListModel<UserBean> getListModel() {
        return listModel;
    }

    public JList<UserBean> getList() {
        return list;
    }

    public void addMouseListener(MouseListener listener) {
        list.addMouseListener(listener);
    }

}
