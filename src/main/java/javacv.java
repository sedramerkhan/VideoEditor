import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class javacv {
    public void extractAudioFromVedio()
    {
        //    String videoFolder="D:\\sample_folder\\";
        //video fil e
        String videoFile = "C:\\Users\\Sedra\\Desktop\\undo.mp4";
        //path
        String videoPath =   videoFile;
        //audio path
        String extractAudio="C:\\Users\\Sedra\\Desktop\\extraxAudio25.mp3";
        try{
            //check the audio file exist or not ,remove it if exist
            File extractAudioFile = new File(extractAudio);
            if (extractAudioFile.exists()) {
                extractAudioFile.delete();
            }
            //audio recorder，extractAudio:audio path，2:channels
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(extractAudio, 2);
            recorder.setAudioOption("crf", "0");
            recorder.setAudioQuality(0);
            //bit rate
            recorder.setAudioBitrate(192000);
            //sample rate
            recorder.setSampleRate(44100);
            recorder.setAudioChannels(2);
            //encoder
            recorder.setAudioCodec(avcodec.AV_CODEC_ID_MP3);
            //start
            recorder.start();
            //load video
            int i=0;
            FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(videoPath);
            grabber.start();
            Frame f=null;
            //get audio sample and record it
            while ((f = grabber.grabSamples()) != null) {
                i++;
                System.out.println("aud: "+i);
                recorder.record(f);
            }
            // stop to save
            grabber.stop();
            recorder.release();
            //output audio path
            //  LOGGER.info(extractAudio);
        } catch (Exception e) {
            //LOGGER.error("", e);
            e.printStackTrace();        }
    }

    public void createVideoWithAudioAndPhoto()
    {
        String videoPath="C:\\test5.avi";
        String audioName="sample.mp3";
        String audioPath="src/main/resources/music.mp3";
//        String videoGenFolder = videoFolder + "videogen\\";
        String videoName = "mix";
        String videoFormat = "mp4";
        //new video file path
        String videoGenPath = "C:\\Users\\Sedra\\Desktop\\newwwrtw.mp4";
//        String videoGenPath = videoGenFolder + videoName + DateFormatUtil.format(new Date()) + "." + videoFormat;
        //create video grabber
        FrameGrabber videoGrabber = new FFmpegFrameGrabber(videoPath);
        //create audio grabber
        FrameGrabber audioGrabber = new FFmpegFrameGrabber(audioPath);
        try {
            //start grabber
            videoGrabber.start();
            audioGrabber.start();
            //recorder for  ceating audio video
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(videoGenPath, videoGrabber.getImageWidth(),
                    videoGrabber.getImageHeight(), audioGrabber.getAudioChannels());
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
            //set format mp4
            recorder.setFormat(videoFormat);
            //set video frame rate
            recorder.setFrameRate(videoGrabber.getFrameRate());
            //set audio sample rate
            recorder.setSampleRate(audioGrabber.getSampleRate());
            //set pixel format
            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
            //record stard
            recorder.start();
            //output basic info
            System.out.println("Video Frame number: "+ videoGrabber.getFrameNumber() +" Length in frames: " + videoGrabber.getLengthInFrames()+ "  Length in time: "+ videoGrabber.getLengthInTime());
            System.out.println("Audio Frame number: "+ audioGrabber.getFrameNumber() +"Length in frames: " + audioGrabber.getLengthInFrames() + " Length in time:{} "+audioGrabber.getLengthInTime());
            Frame frame;
            int counter = 0;
            //record video frame
            while ((frame = videoGrabber.grabFrame()) != null) {
                counter++;
                recorder.record(frame);
            }
            System.out.println("counter in video "+ counter);
            counter*=2;
            //record audio frame
            while (counter >=0 &&(frame = audioGrabber.grabFrame()) != null) {
                counter--;
                recorder.record(frame);
            }
            System.out.println("counter in audio "+counter);
            //complete
            recorder.stop();
            //output path
            System.out.println(videoGenPath);
        } catch (Exception e) {
           System.out.println(e);
        }finally{
            try {
                videoGrabber.stop();
                audioGrabber.stop();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
