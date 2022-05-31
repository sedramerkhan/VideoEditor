
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.putText;


public class VideoPlayerJava {

    VideoCapture vCapture;
    Mat mat;
    VideoWriter videoWriter;

    public VideoPlayerJava() {
        this.vCapture = new VideoCapture();
    }

    public VideoPlayerJava(String path) {
        this.vCapture = new VideoCapture();
        vCapture.open(path);
    }

    public VideoPlayerJava(VideoCapture vCapture) {
        this.vCapture = vCapture;
    }

    public boolean openVideo(String path) {
        try {
            vCapture.open(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Mat getFrame() {
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

    public List<Mat> getFrames(String path) {
        vCapture.open(path);
        List<Mat> list = new ArrayList();
        if (!vCapture.isOpened()) {
            System.out.println("media failed to open");
            return null;
        } else {

            while (vCapture.grab()) {
                list.add(new Mat());
                vCapture.retrieve(list.get(list.size() - 1));
            }
            System.out.println("Done");
            vCapture.release();
            return list;
        }
    }

    public void write(List<Mat> matList, int fps) {

        videoWriter = new VideoWriter("C:\\test5.avi", VideoWriter.fourcc('M', 'J', 'P', 'G'),
                fps, new Size(matList.get(0).width(), matList.get(0).height()), true);
        for (Mat mat : matList) {
            System.out.println(mat.size());
            videoWriter.write(mat);
        }
        videoWriter.release();

    }

    public void addTextWaterMark(List<Mat> matList,String text) {
        for (Mat source : matList) {
            putText(source, text,
                    new Point(source.rows() / 2, source.cols() / 5),
                    Imgproc.FONT_HERSHEY_PLAIN, 1.0,
                    new Scalar(255, 150, 200, 30), 1);
        }
        System.out.println("Text Water Mark is Added Successfully");
    }

    public void addImageWaterMark(List<Mat> matList, String path) {
        Mat waterMark = Imgcodecs.imread(path);
        waterMark = this.resize(matList.get(0).size(), waterMark);
        System.out.println("water mark image: " + waterMark.size());
        for (Mat source : matList) {
            Rect ROI = new Rect(0, 0, waterMark.cols(), waterMark.rows());
            Core.addWeighted(source.submat(ROI), 0.8, waterMark, 0.2, 1, source.submat(ROI));
        }
        System.out.println("Image Water Mark is Added Successfully");

    }

    public void addVideoWaterMark(List<Mat> matList,String path) {
        List<Mat> waterMark = this.getFrames(path);
        for (int i = 0; i < matList.size(); i++) {
            if (i >= waterMark.size())
                break;
            Mat source = matList.get(i);
            Mat waterMarkImage = this.resize(source.size(), waterMark.get(i));
            Rect ROI = new Rect(0, 0, waterMarkImage.cols(), waterMarkImage.rows());
            Core.addWeighted(source.submat(ROI), 0.8, waterMarkImage, 0.2, 1, source.submat(ROI));

        }
        System.out.println("Video Water Mark is Added Successfully");

    }

    public void merge2Videos(List<Mat> matList,String path){
        List<Mat> matList2 = this.getFrames(path);
        matList.addAll(matList2);
        System.out.println("Video is Added Successfully");

    }

    public Mat resize(Size size, Mat waterMark) {
        Mat resizedImage = new Mat();
        Imgproc.resize(waterMark, resizedImage, size);
        return resizedImage;
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

        videoWriter = new VideoWriter("C:\\waterMarkedImage1.avi", VideoWriter.fourcc('M', 'J', 'P', 'G'),
                fps, new Size(matList.get(0).width(), matList.get(0).height()), true);
        Mat waterMark = Imgcodecs.imread("src/main/resources/tutorialspoint.jpg");
        waterMark = this.resize(matList.get(0).size(), waterMark);
        System.out.println("water mark image: " + waterMark.size());
        for (Mat mat : matList) {
            Mat source = mat.clone();
            System.out.println(mat.size());
            Rect ROI = new Rect(0, 0, waterMark.cols(), waterMark.rows());
            Core.addWeighted(source.submat(ROI), 0.8, waterMark, 0.2, 1, source.submat(ROI));
            videoWriter.write(source);
        }
        videoWriter.release();

    }

    public void addWaterMarkVideo(List<Mat> matList, List<Mat> waterMark, int fps) {

        videoWriter = new VideoWriter("C:\\waterMarkedVideo1.avi", VideoWriter.fourcc('M', 'J', 'P', 'G'),
                fps, new Size(matList.get(0).width(), matList.get(0).height()), true);

        System.out.println("water mark image: " + waterMark.size());
        for (int i = 0; i < matList.size(); i++) {
            if (i >= waterMark.size())
                break;
            Mat source = matList.get(i).clone();
            Mat waterMarkImage = this.resize(source.size(), waterMark.get(i));
            Rect ROI = new Rect(0, 0, waterMarkImage.cols(), waterMarkImage.rows());
            Core.addWeighted(source.submat(ROI), 0.8, waterMarkImage, 0.2, 1, source.submat(ROI));
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