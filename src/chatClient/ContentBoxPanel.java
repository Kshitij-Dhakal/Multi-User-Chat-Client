package chatClient;

import javax.swing.*;
import java.awt.*;

public class ContentBoxPanel extends JPanel {
    JPanel panel = new JPanel();

    ContentBoxPanel() {
//        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
//        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
    }

    public void addSend(String message) {
        panel.add(new JPanel(new BorderLayout()){{
//            setBackground(new Color(250,250,250));
            setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
            setBackground(Color.WHITE);
            add(new SentChatBox(message), BorderLayout.EAST);
        }});
    }

    public void addReceived(String message) {
        panel.add(new JPanel(new BorderLayout()){{
            setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
            setBackground(Color.WHITE);
            add(new ReceivedChatBox(message), BorderLayout.WEST);
        }});
    }
}
