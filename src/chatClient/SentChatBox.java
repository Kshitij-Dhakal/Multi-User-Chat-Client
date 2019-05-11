package chatClient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SentChatBox extends JLabel {
    public SentChatBox(String text) {
        super(text);
        setBackground(new Color(152, 152, 224));
        setOpaque(true);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setForeground(Color.WHITE);
    }
}
