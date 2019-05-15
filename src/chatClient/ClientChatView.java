package chatClient;

import javax.swing.*;
import java.awt.*;

public class ClientChatView extends JFrame {

    public ClientChatView() throws HeadlessException {
        JPanel panel = (JPanel) getContentPane();
        DefaultListModel<Messages> messageList = new DefaultListModel<>();
        messageList.addElement(new Messages() {{
            sent("Hello");
        }});
        messageList.addElement(new Messages() {{
            sent("How are you?");
        }});
        messageList.addElement(new Messages() {{
            recieved("Hi");
        }});
        messageList.addElement(new Messages() {{
            recieved("I am fine.");
        }});


        panel.add(new JScrollPane(new JList<Messages>(messageList) {{
            setCellRenderer(new ContentBoxListRenderer());
        }}));
        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
