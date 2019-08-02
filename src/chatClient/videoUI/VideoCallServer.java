package chatClient.videoUI;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class VideoCallServer {

    VideoCallServer(int port) throws IOException, InterruptedException {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(320,240));
        webcam.open();
        DatagramSocket ds = new DatagramSocket();

        while (true) {
            BufferedImage img = webcam.getImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(img, "jpg", baos);

            byte[] buffer = baos.toByteArray();
            InetAddress ip = InetAddress.getByName("localhost");
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length, ip, port);
            System.out.println("Sent " + dp.getLength());
            ds.send(dp);
            Thread.sleep(20);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new VideoCallServer(3000);
    }

}
