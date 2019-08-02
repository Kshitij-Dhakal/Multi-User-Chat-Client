package chatClient.videoUI;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class VideoCallReceiver extends JFrame {
    int posX = 0, posY = 0;

    VideoCallReceiver(int port) throws IOException {
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

        (new Thread(() -> {
            while (true) {
                DatagramPacket dp = new DatagramPacket(new byte[64000], 64000);
                try {
                    ds.receive(dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Received " + dp.getLength());
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
        })).start();
    }

    public static void main(String[] args) throws IOException {
        new VideoCallReceiver(3000);
    }
}
