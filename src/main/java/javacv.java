import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import java.awt.*;
import java.io.File;

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
}
