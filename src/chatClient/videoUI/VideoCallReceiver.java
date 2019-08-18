package chatClient.videoUI;

import chatClient.ChatClient;
import dependencies.lib.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class VideoCallReceiver extends JFrame {
    VideoCallServer server;
    String to;
    int posX = 0, posY = 0;
    JButton acceptCall = new JButton("Accept call") {{
        setBackground(new Color(78, 245, 66));
    }};
    JButton endCall = new JButton() {{
        setBackground(new Color(245, 99, 66));
    }}; //either reject call or end call
    private VideoThread thread;

    public void stopReceiving() {
        this.thread.cancelThread();
    }

    public void setServer(VideoCallServer server) {
        this.server = server;
    }

    /**
     * @param port
     * @throws IOException
     */
    public VideoCallReceiver(int port, String to) throws IOException {
        this.to = to;
        System.out.println("Starting video receiver " + port);

        DatagramSocket ds = new DatagramSocket(port);
        setUndecorated(true);
        setLocationRelativeTo(null);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - posX, e.getYOnScreen() - posY);
            }
        });
        thread = new VideoThread() {
            @Override
            public void run() {
                while (!canceled) {
                    DatagramPacket dp = new DatagramPacket(new byte[64000], 64000);
                    try {
                        ds.receive(dp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                System.out.println("Received " + dp.getLength());
                    byte[] buff = dp.getData();
                    ByteArrayInputStream in = new ByteArrayInputStream(buff);
                    BufferedImage bi;
                    try {
                        bi = ImageIO.read(in);
                        getContentPane().add(new JPanel() {
                            @Override
                            public Dimension getPreferredSize() {
                                return new Dimension(bi.getWidth(), bi.getHeight());
                            }

                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2 = (Graphics2D) g;
                                g2.translate(bi.getWidth(), 0);
                                g2.scale(-1, 1);
                                g2.drawImage(bi, 0, 0, null);
                            }
                        });
                        pack();
                        setVisible(true);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Terminating Video Receiver at : " + port);
                dispose();
            }
        };
        thread.start();
        if (port == 42070) {
            endCall.setText("Reject Call");
            add(new JPanel(new GridLayout(1, 2)) {{
                add(endCall);
                add(acceptCall);
            }}, BorderLayout.SOUTH);
        } else if (port == Config.VIDEO_RECEIVER_PORT) {
            endCall.setText("End Call");
            add(new JPanel(new GridLayout(1, 1)) {{
                add(endCall);
            }}, BorderLayout.SOUTH);
        }
    }

    public void addAcceptListener(ChatClient localhost) {
        acceptCall.addActionListener(new AcceptListener(to, localhost));
    }

    public void addRejectListener(ChatClient localhost) {
        endCall.addActionListener(new RejectListener(to, localhost, this, server));
    }

    private static class AcceptListener implements ActionListener {
        String to;
        ChatClient localhost;

        public AcceptListener(String to, ChatClient localhost) {
            this.to = to;
            this.localhost = localhost;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                localhost.send("video accept " + to);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class RejectListener implements ActionListener {
        private VideoCallServer server;
        String to;
        ChatClient localhost;
        VideoCallReceiver receiver;

        RejectListener(String to, ChatClient localhost, VideoCallReceiver receiver, VideoCallServer server) {
            this.to = to;
            this.localhost = localhost;
            this.receiver = receiver;
            this.server = server;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                localhost.send("video end " + this.to);
                this.receiver.thread.cancelThread();
                this.receiver.dispose();
                //wait for receiver to close receiver
                Thread.sleep(1000);
                if (server != null) {
                    server.stopSending();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class VideoThread extends Thread {
        public volatile boolean canceled = false;

        public void cancelThread() {
            this.canceled = true;
        }
    }
}
