package chatClient.ListOnlineUI;

import chatClient.ChatClientUser;
import chatClient.ClientChatFactory;
import chatClient.messageUI.MessageController;
import dependencies.lib.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class ListOnlineView extends JFrame {
    private JList<ChatClientUser> list;
    private DefaultListModel<ChatClientUser> listModel = new DefaultListModel<>();
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
        new ListOnlineView() {{
            ChatClientUser kshitij = new ChatClientUser(new User()) {{
                setUserHandle("@Kshitij_Dhakal");
                setFirstName("Kshitij");
                setLastName("Dhakal");
            }};
            ChatClientUser subin = new ChatClientUser(new User()) {{
                setUserHandle("@Subin_Shrestha");
                setFirstName("Subin");
                setLastName("Shrestha");
                addNewMessage();
                addNewMessage();
            }};
            ChatClientUser sudesh = new ChatClientUser(new User()) {{
                setUserHandle("@Sudesh_Khatiwada");
                setFirstName("Sudesh");
                setLastName("Khatiwada");
                addNewMessage();
            }};
            getListModel().addElement(kshitij);
            getListModel().addElement(subin);
            getListModel().addElement(sudesh);
//            System.out.println(kshitij);
//            System.out.println(subin);
//            System.out.println(sudesh);
        }};


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

    public DefaultListModel<ChatClientUser> getListModel() {
        return listModel;
    }

    public JList<ChatClientUser> getList() {
        return list;
    }

    public void addMouseListener(MouseListener listener) {
        list.addMouseListener(listener);
    }

}
