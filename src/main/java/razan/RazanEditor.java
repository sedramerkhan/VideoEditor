package razan;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

public class RazanEditor {
    Mat frame = new Mat();
    VideoCapture cap = new VideoCapture();
    ArrayList<Mat> frames = new ArrayList();
    String input = "src/main/resources/test2.mp4";
    String output = "C:\\Users\\Sedra\\Desktop\\razan.mp4";
    String edit = "/Users/RAZAN/Desktop/edit";
    int video_length;
    int frames_per_second;
    int frame_number;

    public RazanEditor() {
        this.video_length = (int)this.cap.get(7);
        this.frames_per_second = (int)this.cap.get(5);
        this.frame_number = (int)this.cap.get(1);
    }

    public void capp() {
        this.cap.open(this.input);
        int video_length = (int)this.cap.get(7);
        int frames_per_second = (int)this.cap.get(5);
        int frame_number = (int)this.cap.get(1);
//        int frame_numbe = false;
        if (!this.cap.isOpened()) {
            System.out.println("Fail");
        } else {
            while (cap.grab()) {
                frames.add(new Mat());
                cap.retrieve(frames.get(frames.size() - 1));
            }
            cap.release();
//            while(true) {
//                if (!this.cap.read(this.frame)) {
//                    this.cap.release();
////                    System.out.println(">>>" + this.frames.size());
//                    break;
//                }
//
//                if(frame_number<5) {
//                    Imgcodecs.imwrite("C:/Users/Sedra/Desktop/" + frame_number + ".jpg", this.frame);
//                    System.out.println(">>>" + this.frame.size());
//                }
//
//                this.frames.add(this.frame);
//                ++frame_number;
//            }
//            Imgcodecs.imwrite("C:/Users/Sedra/Desktop/frame.jpg", this.frames.get(5));
            System.out.println(">>>" + this.frames.get(5).size());
            System.out.println(">>>" + this.frames.size());
            System.out.println("Capture Video is Done");
        }

    }

    void cut() {
        this.capp();

        for(int i = 0; i <= this.frames.size(); ++i) {
            if (i > 60 && i < 100) {
                this.frames.remove(i);
            }
        }

        System.out.println(">>>11" + this.frames.size());
    }

    void edit() {
        this.capp();
        ArrayList<Mat> buffer = new ArrayList();

        int i;
        for(i = 0; i <= this.frames.size(); ++i) {
            if (i > 60 && i < 100) {
                buffer.add((Mat)this.frames.get(i));
                this.frames.remove(i);
            }
        }

        System.out.println(buffer.size());

        for(i = 0; i <= this.frames.size(); ++i) {
            if (i == 300) {
                for(int j = 0; j < buffer.size(); ++j) {
                    this.frames.add(i, (Mat)buffer.get(j));
                }
            }
        }

        System.out.println("FF" + this.frames.size());
    }

    public void write() {
        int fps = 30;
        VideoWriter videoWriter = new VideoWriter(output, VideoWriter.fourcc('X', '2', '6', '4'),
                fps, new Size(frames.get(0).width(), frames.get(0).height()), true);
        for (Mat mat : frames) {
            videoWriter.write(mat);
        }
        videoWriter.release();
        System.out.println("Saved To Disk Successfully");
    }
}
