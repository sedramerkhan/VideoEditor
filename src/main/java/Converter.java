import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Converter {

    Mat toMat(BufferedImage input) {
        if (input == null)
            System.out.println("nnn");
        Mat mat = new Mat(input.getHeight(), input.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) input.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    BufferedImage toBufferedImage(Mat input) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", input, matOfByte);
        //Storing the encoded Mat in a byte array
        byte[] byteArray = matOfByte.toArray();
        //Preparing the Buffered Image
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = ImageIO.read(in);
        return bufImage;
    }
}
