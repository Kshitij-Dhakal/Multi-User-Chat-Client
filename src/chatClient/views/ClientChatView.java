package chatClient.views;

import chatClient.ContentBoxListRenderer;
import chatClient.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientChatView extends JFrame {

    private DefaultListModel<Messages> messageList;
    private JTextField messageField = new JTextField();
    private JButton sendButton = new JButton("Send");

    public ClientChatView() throws HeadlessException {
        JPanel mainPanel = (JPanel) getContentPane();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(getMessagePanel(), BorderLayout.CENTER);
        panel.add(getBottomPanel(), BorderLayout.SOUTH);

        mainPanel.add(panel);
        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private JPanel getBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        return bottomPanel;
    }

    private JScrollPane getMessagePanel() {
        this.messageList = new DefaultListModel<>();
        return new JScrollPane(new JList<Messages>(messageList) {{
            setCellRenderer(new ContentBoxListRenderer());
        }});
    }

    public void send(String message) {
        messageList.addElement(new Messages() {{
            sent(message);
        }});
    }

    public void receive(String message) {
        messageList.addElement(new Messages() {{
            recieved(message);
        }});
    }

    public String getMessageText() {
        return this.messageField.getText().trim();
    }

    public void setMessageText(String messageText) {
        this.messageField.setText(messageText);
    }

    public void addActionListener(ActionListener listener) {
        this.sendButton.addActionListener(listener);
    }
}
