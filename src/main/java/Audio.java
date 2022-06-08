import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avutil;
import org.bytedeco.javacv.*;

public class Audio {

    private FFmpegFrameGrabber videoGrabber,audioGrabber;
    private FFmpegFrameRecorder recorder;

    String audioPath,videoPath,videoName,videoGenPath;
    public Audio(String audioPath,String viddeoPath,String videoName){
        this.audioPath = audioPath;
        this.videoPath = viddeoPath;
        this.videoName = videoName;
        //create video grabber
        videoGrabber = new FFmpegFrameGrabber(videoPath + videoName);
        //create audio grabber
        audioGrabber = new FFmpegFrameGrabber(audioPath);
    }
    private void initRecorder() throws FrameRecorder.Exception {
        String newVideoName = "withSound.mp4";
        String videoFormat = "mp4";
        //new video file path
        videoGenPath = videoPath +"\\"+ newVideoName;
        recorder = new FFmpegFrameRecorder(videoGenPath, videoGrabber.getImageWidth(),
                videoGrabber.getImageHeight(), audioGrabber.getAudioChannels());
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);

        recorder.setFormat(videoFormat);
        //set video frame rate
        recorder.setFrameRate(videoGrabber.getFrameRate());
        //set audio sample rate
        recorder.setSampleRate(audioGrabber.getSampleRate());
        //set pixel format
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        //record stard
        recorder.start();
    }
    public void createVideoWithAudioAndPhoto() {
        try {

            videoGrabber.start();
            audioGrabber.start();
            initRecorder();
            Frame frame;
            int counter = 0;
            long videoDuration = videoGrabber.getFormatContext().duration() / 1000000;
            long audioDuration = audioGrabber.getFormatContext().duration() / 1000000;
            //record video frame
            while ((frame = videoGrabber.grabFrame()) != null) {
                recorder.record(frame);
                counter++;
            }
            System.out.println("counter " + counter);

            counter = 0;
            while ((audioGrabber.grabFrame()) != null) {
                counter++;
            }
            int endOfAudio = getEndFrameNumber(videoDuration, audioDuration, counter);
            audioGrabber.close();
            audioGrabber = new FFmpegFrameGrabber(audioPath);
            audioGrabber.start();
            System.out.println("end of audio" + endOfAudio);
            for (int i = 0; i <= endOfAudio+1; i++) {
                frame = audioGrabber.grabFrame();
                if (frame != null)
                    recorder.record(frame);
                    //if the auido is smaller than video then repeat it
                else {
                    audioGrabber.close();
                    audioGrabber = new FFmpegFrameGrabber(audioPath);
                    audioGrabber.start();
                    i--;//for not remove frame when starting again
                }
            }
            //complete
            recorder.stop();
            //output path
            System.out.println("The Audio is Saved in " + videoGenPath);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                videoGrabber.stop();
                audioGrabber.stop();
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }
    private static int getEndFrameNumber(long videoDuration, long durationInSecond, int totalFrame) {
        if (videoDuration >= durationInSecond)
            return (int) videoDuration;
        double percent = videoDuration / (double) durationInSecond;
        return (int) Math.ceil(totalFrame * percent);
    }
}
