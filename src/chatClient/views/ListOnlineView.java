package chatClient.views;

import javax.swing.*;
import java.awt.event.MouseListener;

public class ListOnlineView extends JFrame {
    private JPanel panel = new JPanel();
    private JList<String> list;
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    public ListOnlineView() {
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane scrollPane = new JScrollPane(list);

        getContentPane().add(scrollPane);
        setVisible(true);
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    public void setListModel(DefaultListModel<String> listModel) {
        this.listModel = listModel;
    }

    public JList<String> getList() {
        return list;
    }

    public void addMouseListener(MouseListener listener) {
        list.addMouseListener(listener);
    }

}
