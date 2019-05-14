package chatClient;

import javax.swing.*;
import java.awt.*;

public class ContentBoxPanel extends JPanel {
    private JPanel panel = new JPanel();
    private JPanel textPanel = new JPanel(new BorderLayout());

    ContentBoxPanel() {
//        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
//        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
    }

    public void addSend(String message) {
        panel.add(new JPanel(new BorderLayout()) {{
//            setBackground(new Color(250,250,250));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
            setBackground(Color.WHITE);
            add(new ClientChatView.SentChatBox(message), BorderLayout.EAST);
        }});
//        textPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
//        textPanel.setBackground(Color.WHITE);
//        textPanel.add(new ClientChatView.SentChatBox(message), BorderLayout.EAST);
//        panel.add(textPanel);
    }

    public void addReceived(String message) {
        panel.add(new JPanel(new BorderLayout()) {{
            setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
            setBackground(Color.WHITE);
            add(new ClientChatView.ReceivedChatBox(message), BorderLayout.WEST);
        }});
    }
}
