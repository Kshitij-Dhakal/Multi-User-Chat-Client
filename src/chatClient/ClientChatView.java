package chatClient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientChatView extends JFrame {
    private ContentBoxPanel contentBox = new ContentBoxPanel();
    private JTextField messageBox = new JTextField();
    private JButton sendButton = new JButton("Send");

    public ContentBoxPanel getContentBox() {
        return contentBox;
    }

    public void setContentBox(ContentBoxPanel contentBox) {
        this.contentBox = contentBox;
    }

    public JTextField getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(JTextField messageBox) {
        this.messageBox = messageBox;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }

    public JPanel getChatViewPanel() {
        return chatViewPanel;
    }

    public void setChatViewPanel(JPanel chatViewPanel) {
        this.chatViewPanel = chatViewPanel;
    }

    private JPanel chatViewPanel = new JPanel();


    public ClientChatView() {
        JScrollPane contentBoxScrollPane = new JScrollPane(contentBox);
        contentBox.setBackground(Color.WHITE);
        contentBox.addSend("Hello");
        contentBox.addReceived("Hello Back");
        contentBox.addSend("How are you doing?");
        contentBox.addReceived("I'm Fine. How are you?");
        contentBox.addReceived("Where are you rn?");
        contentBox.addSend("I am in ngt");


        //Experimental for adding html/css
/*
        contentBox.setEditable(false);
        HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        contentBox.setEditorKit(htmlEditorKit);
        JScrollPane contentBoxScrollPane = new JScrollPane(contentBox);
        StyleSheet styleSheet = htmlEditorKit.getStyleSheet();
        styleSheet.addRule("#sent {\n" +
                "    position: relative;\n" +
                "    float: right;\n" +
                "    background-color:rgb(152, 152, 224);\n" +
                "    padding: 10px;\n" +
                "    border-radius: 25px;\n" +
                "    color: white;\n" +
                "}\n");
        styleSheet.addRule("#received {\n" +
                "    position: relative;\n" +
                "    float: left;\n" +
                "    background-color: gainsboro;\n" +
                "    padding: 10px;\n" +
                "    border-radius: 25px;\n" +
                "    color: black;\n" +
                "    border-color: black;\n" +
                "}\n");
        HTMLDocument defaultDocument = (HTMLDocument) htmlEditorKit.createDefaultDocument();
        contentBox.setDocument(defaultDocument);
        String htmlString = "<div id='sent'>This message was sent!!</div>" +
                "<div id='received'>This message was received!!</div>";
        contentBox.setText(htmlString);
        chatViewPanel.setLayout(new BorderLayout());
        chatViewPanel.add(contentBoxScrollPane, BorderLayout.CENTER);
*/
        //Experimental for adding html/css


        chatViewPanel.setLayout(new BorderLayout());
        chatViewPanel.add(contentBoxScrollPane, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(messageBox, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        chatViewPanel.add(bottomPanel, BorderLayout.SOUTH);


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        getContentPane().add(chatViewPanel);
    }

    public static class SentChatBox extends JLabel {
        public SentChatBox(String text) {
            super(text);
            setBackground(new Color(152, 152, 224));
            setOpaque(true);
            setBorder(new EmptyBorder(10, 10, 10, 10));
            setForeground(Color.WHITE);
        }
    }

    public static class ReceivedChatBox extends JLabel {
        public ReceivedChatBox(String text) {
            super(text);
//        setBackground(new Color( 239, 239, 239));
            setBackground(Color.LIGHT_GRAY);
            setOpaque(true);
            setBorder(new EmptyBorder(10, 10, 10, 10));
//        setForeground(Color.BLACK);
        }
    }


}
