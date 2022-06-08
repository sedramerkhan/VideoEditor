
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.putText;


public class VideoEditor {

    VideoCapture vCapture;

    VideoWriter videoWriter;

    public VideoEditor() {
        this.vCapture = new VideoCapture();
    }

    public VideoEditor(String path) {
        this.vCapture = new VideoCapture();
        vCapture.open(path);
    }

    public VideoEditor(VideoCapture vCapture) {
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
        Mat mat = new Mat();
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
                System.out.println("Video Frames are Captured");
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
            System.out.println("Getting Frames is Done Successfully");
            System.out.println("We Have "+list.size()+" Frames With Size "+list.get(0).size());
            vCapture.release();
            return list;
        }
    }

    public void write(List<Mat> matList,String videoPath, int fps) {

        videoWriter = new VideoWriter(videoPath, VideoWriter.fourcc('X', '2', '6', '4'),
                fps, new Size(matList.get(0).width(), matList.get(0).height()), true);
        for (Mat mat : matList) {
            videoWriter.write(mat);
        }
        videoWriter.release();
        System.out.println("Saved To Disk Successfully");
    }

//    public void addTextWaterMark(List<Mat> matList, String text,double alpha) {
//        for (Mat source : matList) {
//            putText(source, text,
//                    new Point(source.rows() / 2, source.cols() / 5),
//                    Imgproc.FONT_HERSHEY_PLAIN, 1.0,
//                    new Scalar(255, 150, 200, 30), 1);
//        }
//        System.out.println("Text Water Mark is Added Successfully");
//    }
    private static Mat addTextWatermark(String text, Mat source, float alpha) throws IOException, IOException {
        BufferedImage image = Converter.toBufferedImage(source);

        // determine image type and handle correct transparency
        // initializes necessary graphic properties
        Graphics2D w = (Graphics2D) image.getGraphics();
        //       w.drawImage(image, 0, 0, null);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        w.setComposite(alphaChannel);
        w.setColor(Color.GRAY);
        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        FontMetrics fontMetrics = w.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, w);

        // calculate center of the image
        int centerX = (image.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = image.getHeight() / 2;

        // add text overlay to the image
        w.drawString(text, centerX, centerY);
        //  Imgcodecs.imwrite("D:\\test.jpg",Converter.toMat(image));

        w.dispose();
        return Converter.toMat(image);
    }
    public void addTextWaterMark(List<Mat> matList, String text, float alpha) throws IOException {
        for (int i = 0  ;i < matList.size();i++) {
            Mat source= matList.get(i);
            matList.set(i,addTextWatermark(text,source,alpha));
            /*
            putText(source, text,
                    new Point(source.rows() / 2, source.cols() / 5),
                    Imgproc.FONT_HERSHEY_PLAIN, 1.0,
                    new Scalar(255, 150, 200, 30), 1);
*/        }
        System.out.println("Text Water Mark is Added Successfully");
    }
    public void addImageWaterMark(List<Mat> matList, String path,double alpha) {
        Mat waterMark = Imgcodecs.imread(path);
        waterMark = this.resize(matList.get(0).size(), waterMark);
        System.out.println("water mark image: " + waterMark.size());
        for (int i =0; i< matList.size();i++) {
            Mat source= matList.get(i).clone();
            Rect ROI = new Rect(0, 0, waterMark.cols(), waterMark.rows());
            Core.addWeighted(source.submat(ROI), alpha, waterMark, 1-alpha, 1, source.submat(ROI));
            matList.set(i,source);
        }
        System.out.println("Image Water Mark is Added Successfully");

    }

    public void addVideoWaterMark(List<Mat> matList, String path,double alpha) {
        List<Mat> waterMark = this.getFrames(path);
        if(waterMark != null) {
            for (int i = 0; i <matList.size(); i++) {
                Mat source = matList.get(i).clone();
                Mat waterMarkImage = this.resize(source.size(), waterMark.get(i%waterMark.size()));
                Rect ROI = new Rect(0, 0, waterMarkImage.cols(), waterMarkImage.rows());
                Core.addWeighted(source.submat(ROI), alpha, waterMarkImage, 1-alpha, 1, source.submat(ROI));
                matList.set(i,source);
            }
            System.out.println("Video Water Mark is Added Successfully");
        }
        else
            System.out.println("Something Went Wrong");
    }

    public void addSticker(List<Mat> matList, Mat emoji) {
        emoji = this.resize(new Size(50,50), emoji);
        for (int i =0; i< matList.size();i++) {
            Mat source= matList.get(i).clone();
            Rect ROI = new Rect(matList.get(0).rows()/3, matList.get(0).cols()/3, emoji.cols(), emoji.rows());
            Core.addWeighted(source.submat(ROI), 0, emoji, 1, 1, source.submat(ROI));
            matList.set(i,source);

        }
        System.out.println("Image Water Mark is Added Successfully");

    }

    public void merge2Videos(List<Mat> matList, String path) {
        List<Mat> matList2 = this.getFrames(path);
        if(matList2 != null) {
            if (matList.get(0).size() != matList2.get(0).size()) {
                matList2.replaceAll(it -> resize(matList.get(0).size(), it));
            }
            matList.addAll(matList2);
            System.out.println("Video is Added Successfully");
        }
        else
            System.out.println("Something Went Wrong");
    }

    public void moveVideoFrames(List<Mat> matList, int start, int end, int position) {
        if (start < matList.size() && end < matList.size() && position <= matList.size()) {
            if(position<start) {
                move(matList,position,start,end+1);
                System.out.println("Frames Are Moved Successfully");

            }
            else if (position>end){
                move(matList,start,end+1,position+end-start+1);
                System.out.println("Frames Are Moved Successfully");

            }else
                System.out.println("Something Went Wrong");
        } else
            System.out.println("Something Went Wrong");
    }

    private void move(List<Mat> matList,int index1,int index2,int index3){
        ArrayList<Mat> temp = new ArrayList(matList);;
        List<Mat> temp1 = temp.subList(0, index1);
        List<Mat> temp2 = temp.subList(index1, index2);
        List<Mat> temp3 = temp.subList(index2, index3);
        List<Mat> temp4 = temp.subList(index3, matList.size());
        matList.clear();
        matList.addAll(temp1);
        matList.addAll(temp3);
        matList.addAll(temp2);
        matList.addAll(temp4);
    }

    public void deleteVideoFrames(List<Mat> matList, int start, int end) {
        if (start < matList.size() && end < matList.size()) {
            matList.subList(start, end + 1).clear();
            System.out.println("Frames Are Deleted Successfully");
        } else
            System.out.println("Something Went Wrong");
    }

    public Mat resize(Size size, Mat waterMark) {
        Mat resizedImage = new Mat();
        Imgproc.resize(waterMark, resizedImage, size);
        return resizedImage;
    }

}