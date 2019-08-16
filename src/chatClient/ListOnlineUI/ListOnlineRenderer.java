package chatClient.ListOnlineUI;

import dependencies.lib.UserBean;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ListOnlineRenderer extends JPanel implements ListCellRenderer<UserBean> {
    JLabel userName = new JLabel();
    JLabel userHandle = new JLabel();
    JLabel messageBadge = new JLabel();
    JPanel cellPanel;

    public ListOnlineRenderer() {
        cellPanel = this;
//        cellPanel.setBorder(new EmptyBorder(2, 0, 2, 0));
        cellPanel.setLayout(new BorderLayout());
        userHandle.setForeground(Color.GRAY);
        messageBadge.setOpaque(true);
        messageBadge.setBackground(new Color(79, 170, 226));
        messageBadge.setBorder(new EmptyBorder(5, 8, 5, 8));
        cellPanel.add(new JPanel(new FlowLayout(FlowLayout.LEFT)) {{
            add(userName);
            add(userHandle);
            setOpaque(false);
        }}, BorderLayout.CENTER);
        cellPanel.add(messageBadge, BorderLayout.WEST);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends UserBean> list, UserBean value, int index, boolean isSelected, boolean cellHasFocus) {
        userName.setText(value.getName());
        userHandle.setText("(" + value.getUserHandle() + ")");
        if (value.getMessage() > 0) {
            messageBadge.setVisible(true);
            messageBadge.setText(value.getMessage() + "");
        } else {
            messageBadge.setVisible(false);
        }
        if (isSelected) {
            cellPanel.setBackground(list.getSelectionBackground());
            cellPanel.setForeground(list.getSelectionForeground());
        } else {
            cellPanel.setBackground(list.getBackground());
            cellPanel.setForeground(list.getForeground());
        }
        return this;
    }
}
