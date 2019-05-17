package chatClient.messageUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MessageListRenderer extends JPanel implements ListCellRenderer<Messages> {

    JLabel messageText = new JLabel();
    private JPanel messagePanel;

    public MessageListRenderer() {
        setBorder(new EmptyBorder(5, 5, 0, 5));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        messagePanel = this;
        messageText.setOpaque(true);
        messageText.setBorder(new EmptyBorder(10, 10, 10, 10));
//        add(messagePanel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Messages> list, Messages value, int index, boolean isSelected, boolean cellHasFocus) {
        messageText.setText(value.getMessageText());
        if (value.isSent) {
            messageText.setBackground(new Color(79, 170, 226));
            messageText.setForeground(Color.WHITE);
            messagePanel.add(messageText, BorderLayout.EAST);
        } else {
            messageText.setBackground(new Color(206, 219, 226));
            messageText.setForeground(Color.BLACK);
            messagePanel.add(messageText, BorderLayout.WEST);
        }
        return this;
    }
}