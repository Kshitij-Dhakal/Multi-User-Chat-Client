package chatClient.messageUI;

import dependencies.UI.AddPlaceHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MessageView extends JPanel {

    private DefaultListModel<Messages> messageList;
    private JTextField messageField = new JTextField("Message");
    private JButton sendButton = new JButton("Send");

    public MessageView() throws HeadlessException {
//        setBackground(Color.cyan);
        JPanel mainPanel = this;
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBackground(Color.red);
        panel.add(getMessagePanel(), BorderLayout.CENTER);
        panel.add(getBottomPanel(), BorderLayout.SOUTH);

        mainPanel.add(panel, BorderLayout.CENTER);
//        setVisible(true);
//        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addPlaceHolder();
    }

    public static void main(String[] args) {
        new JFrame() {{
//            setLayout(new GridLayout(1, 1));
            add(new MessageView());
            setVisible(true);
            setSize(640, 480);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }};
    }

    private void addPlaceHolder() {
        messageField.addFocusListener(new AddPlaceHolder("Message", messageField));
    }

    public JTextField getMessageField() {
        return messageField;
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
            setCellRenderer(new MessageListRenderer());
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

    public void setMessageText(String messageText) {
        this.messageField.setText(messageText);
    }

    public void addActionListener(ActionListener listener) {
        this.messageField.addActionListener(listener);
        this.sendButton.addActionListener(listener);
    }

    public void disableSend() {
        setMessageText("User Went Offline");
        getMessageField().setEnabled(false);
        sendButton.setEnabled(false);
    }

    public void enableSend() {
//        setVisible(true);
        setMessageText("");
        getMessageField().setEnabled(true);
        sendButton.setEnabled(true);
    }
}
