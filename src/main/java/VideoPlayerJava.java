
import androidx.compose.runtime.snapshots.SnapshotStateList;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import javax.swing.*;



public class VideoPlayerJava {

    VideoCapture vCapture;
    SnapshotStateList<Mat> matList;

    public VideoPlayerJava(String path) {
        this.vCapture = new VideoCapture();
        vCapture.open(path);
        matList = new SnapshotStateList();
    }

    public VideoPlayerJava(VideoCapture vCapture) {
        this.vCapture = vCapture;
    }

    public SnapshotStateList<Mat> play() {

        if (!vCapture.isOpened()) {
            System.out.println("media failed to open");
            return null;
        } else {
            while (vCapture.grab()) {
                matList.add(new Mat());
                vCapture.retrieve(matList.get(matList.size() - 1));
//                System.out.println(matList.size());
//                showInFrame(mat);
            }
            System.out.println("Done");
            vCapture.release();
            return matList;
        }
    }


    private static void showInFrame(Mat mat) {
        // TODO Auto-generated method stub
        JFrame mediaFrame = new JFrame("Media");
        mediaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mediaFrame.setVisible(true);
        mediaFrame.setSize(300, 300);

        Imgcodecs.imwrite("src/main/resources/img.jpg", mat);
        ImageIcon image = new ImageIcon("src/main/resources/img.jpg");
        JLabel label = new JLabel("", image, JLabel.CENTER);

        mediaFrame.add(label);
        mediaFrame.repaint();
        mediaFrame.validate();
        mediaFrame.setVisible(true);
    }
}