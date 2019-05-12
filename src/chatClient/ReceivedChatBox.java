package chatClient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReceivedChatBox extends JLabel {
    public ReceivedChatBox(String text) {
        super(text);
//        setBackground(new Color( 239, 239, 239));
        setBackground(Color.LIGHT_GRAY);
        setOpaque(true);
        setBorder(new EmptyBorder(10, 10, 10, 10));
//        setForeground(Color.BLACK);
    }
}
