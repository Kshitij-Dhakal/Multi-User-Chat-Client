package chatClient;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

public class ClientChatView extends JFrame {
    JPanel contentBox = new JPanel();
    JTextField messageBox = new JTextField();
    JButton sendButton = new JButton("Send");
    JPanel chatViewPanel = new JPanel();


    public ClientChatView() {
        JScrollPane contentBoxScrollPane = new JScrollPane(contentBox);
        contentBox.setBackground(Color.WHITE);
        contentBox.add(new SentChatBox("Hello"));
        contentBox.add(new ReceivedChatBox("Hello Back"));

        //Experimental for adding html/css

/*
        contentBox.setEditable(false);
        HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        contentBox.setEditorKit(htmlEditorKit);
        JScrollPane contentBoxScrollPane = new JScrollPane(contentBox);
        StyleSheet styleSheet = htmlEditorKit.getStyleSheet();
        //TODO add stylesheed for sender and receiver
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
        chatViewPanel.add(contentBoxScrollPane,BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(messageBox, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        chatViewPanel.add(bottomPanel, BorderLayout.SOUTH);


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        add(chatViewPanel);
    }

    public static void main(String[] args) {
        new ClientChatView();
    }
}
