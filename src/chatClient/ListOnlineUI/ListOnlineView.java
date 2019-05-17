package chatClient.ListOnlineUI;

import chatClient.ChatClientUser;
import dependencies.User.User;

import javax.swing.*;
import java.awt.event.MouseListener;

public class ListOnlineView extends JFrame {
    private JList<ChatClientUser> list;
    private DefaultListModel<ChatClientUser> listModel = new DefaultListModel<>() {
    };

    public ListOnlineView() {
        list = new JList<>(listModel);
        list.setCellRenderer(new ListOnlineRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane scrollPane = new JScrollPane(list);

        getContentPane().add(scrollPane);
        setVisible(true);
        setSize(400, 600);
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

    public DefaultListModel<ChatClientUser> getListModel() {
        return listModel;
    }

    public void setListModel(DefaultListModel<ChatClientUser> listModel) {
        this.listModel = listModel;
    }

    public JList<ChatClientUser> getList() {
        return list;
    }

    public void addMouseListener(MouseListener listener) {
        list.addMouseListener(listener);
    }

}
