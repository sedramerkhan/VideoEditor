
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import javax.swing.*;
import java.util.List;

import static org.opencv.imgproc.Imgproc.putText;


public class VideoPlayerJava {

    VideoCapture vCapture;
    Mat mat;
    VideoWriter videoWriter;

    public VideoPlayerJava(String path) {
        this.vCapture = new VideoCapture();
        vCapture.open(path);
    }

    public VideoPlayerJava(VideoCapture vCapture) {
        this.vCapture = vCapture;
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
            } else {
                System.out.println("Done");
                vCapture.release();
                return null;
            }


        }
    }

    public void write(List<Mat> matList, int fps) {

        videoWriter = new VideoWriter("C:\\test2.avi", VideoWriter.fourcc('M', 'J', 'P', 'G'),
                fps, new Size(matList.get(0).width(), matList.get(0).height()), true);
        for (Mat mat : matList) {
            System.out.println(mat.size());
            videoWriter.write(mat);
        }
        videoWriter.release();

//        String path =   "C:\\Users\\Sedra\\Desktop\\legend.mp4";
//        VideoCapture videoCapture = new VideoCapture();
//        videoCapture.open(path);
//        Size frameSize = new Size(426,240);
//        VideoWriter videoWriter = new VideoWriter("C:\\test2.avi",VideoWriter.fourcc('M','J','P','G'),
//                30, frameSize, true);
//        mat = new Mat();
//        while (videoCapture.read(mat)) {
////            mat = new Mat();
////            videoCapture.retrieve(mat);
//            System.out.println(mat.size());
//            videoWriter.write(mat);
//        }
//        videoCapture.release();
//        videoWriter.release();

    }

    public void addWaterMarkText(List<Mat> matList, int fps) {

        videoWriter = new VideoWriter("C:\\waterMarkedText1.avi", VideoWriter.fourcc('M', 'J', 'P', 'G'),
                fps, new Size(matList.get(0).width(), matList.get(0).height()), true);
        for (Mat mat : matList) {
            Mat source = mat.clone();
            System.out.println(mat.size());


            putText(source, "Tutorialspoint.com",
                    new Point(source.rows() / 2, source.cols() / 5),
                    Imgproc.FONT_HERSHEY_PLAIN, 1.0,
                    new Scalar(255, 150, 200, 30), 1);

            videoWriter.write(source);
        }
        videoWriter.release();

    }

    public void addWaterMarkImage(List<Mat> matList, int fps) {

        videoWriter = new VideoWriter("C:\\waterMarked1.avi", VideoWriter.fourcc('M', 'J', 'P', 'G'),
                fps, new Size(matList.get(0).width(), matList.get(0).height()), true);
        Mat waterMark = Imgcodecs.imread("src/main/resources/tutorialspoint.jpg");
        System.out.println("water mark image: " + waterMark.size());
        for (Mat mat : matList) {
            Mat source = mat.clone();
            System.out.println(mat.size());
            Rect ROI = new Rect(source.rows() / 5, source.cols() / 5, waterMark.cols(), waterMark.rows());

            Core.addWeighted(source.submat(ROI), 0.8, waterMark, 0.2, 1, source.submat(ROI));

//            Imgcodecs.putText(
//                    source,
//                    "Tutorialspoint.com",
//                    Point((source.rows() / 3).toDouble(), (source.cols() / 5).toDouble()),
//                    Core.FONT_ITALIC,
//                    2.0,
//                    Scalar(255.0, 150.0, 200.0,30.0)
//            )
//            Core.putText(source, "Tutorialspoint.com", new Point  (source.rows()/2,source.cols()/2), Core.FONT_ITALIC,new Double(1),new  Scalar(255));

            videoWriter.write(source);
        }
        videoWriter.release();

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