
import androidx.compose.runtime.snapshots.SnapshotStateList;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import javax.swing.*;



public class VideoPlayerJava {

    VideoCapture vCapture;
    Mat mat;
    boolean end;

    public VideoPlayerJava(String path) {
        this.vCapture = new VideoCapture();
        vCapture.open(path);
        end = false;
    }

    public VideoPlayerJava(VideoCapture vCapture) {
        this.vCapture = vCapture;
        end = false;
    }

    public Mat play() {
        mat = new Mat();
        if (!vCapture.isOpened()) {
            System.out.println("media failed to open");
            return null;
        } else {
           if (vCapture.grab()) {
                vCapture.retrieve(mat);
//                System.out.println(matList.size());
//                showInFrame(mat);
               return mat;
            }
           else{
               System.out.println("Done");
               vCapture.release();
               return null;
           }


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