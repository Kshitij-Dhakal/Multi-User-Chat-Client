package chatClient.videoUI;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class VideoCallServer {

    private VideoThread thread;

    public VideoCallServer(InetAddress ip, int port) throws IOException, InterruptedException {
        System.out.println("Starting video server " + port);
        Webcam webcam = Webcam.getDefault();
        if (webcam.isOpen()) {
            webcam.close();
        }
        webcam.setViewSize(webcam.getViewSizes()[0]);
        webcam.open();
        DatagramSocket ds = new DatagramSocket();

        thread = new VideoThread() {
            @Override
            public void run() {
                while (!canceled) {
                    try {
                        BufferedImage img = webcam.getImage();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(img, "jpg", baos);
                        byte[] buffer = baos.toByteArray();
                        DatagramPacket dp = new DatagramPacket(buffer, buffer.length, ip, port);
//            System.out.println("Sent " + dp.getLength());
                        ds.send(dp);
                        Thread.sleep(20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                webcam.close();
                System.out.println("Terminating Video Server at : " + port);
            }
        };
        thread.start();
    }

    public void stopSending() {
        this.thread.cancelThread();
    }

    private static class VideoThread extends Thread {
        public volatile boolean canceled = false;

        public void cancelThread() {
            this.canceled = true;
        }
    }

}
