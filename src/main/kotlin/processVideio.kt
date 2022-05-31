//package src.main.kotlin
//
//import org.jetbrains.skia.Bitmap
//import java.awt.Frame
//import java.io.File
//
//private fun processVideo(videoFile: File) {
//    val grabber = FFmpegFrameGrabber(videoFile.absolutePath)
//
//    val bitmapConverter = AndroidFrameConverter()
//    grabber.setPixelFormat(AV_PIX_FMT_RGBA)
//
//    try {
//        grabber.start()
//    } catch (e: FrameGrabber.Exception) {
//        e.printStackTrace()
//    }
//    var grabbedFrame: Frame? = null
//
//    grabber.setFrameRate(grabber.getFrameRate())
//    grabber.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//    val recorder = FFmpegFrameRecorder(
//        File(context?.filesDir, "result.mp4"),
//        grabber.getImageWidth(),
//        grabber.getImageHeight()
//    )
//
//    recorder.setVideoCodec(grabber.getVideoCodec())
//    recorder.setFormat(grabber.getFormat())
//    recorder.setAudioChannels(grabber.getAudioChannels())
//    recorder.setAudioCodec(grabber.getAudioCodec())
//    recorder.setSampleRate(grabber.getSampleRate())
//    recorder.setAudioBitrate(grabber.getAudioBitrate())
//    recorder.setFrameRate(grabber.getFrameRate())
//    recorder.setVideoBitrate(grabber.getVideoBitrate())
//    recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264)
//
//    try {
//        recorder.start()
//    } catch (e: FrameRecorder.Exception) {
//        e.printStackTrace()
//    }
//
//    println("starting processing video")
//
//
//    while (grabber.grabFrame().also { grabbedFrame = it } != null) {
//        try {
//            val bitmap = bitmapConverter.convert(grabbedFrame)
//            if (grabbedFrame == null) {
//                println("grabbed frame null")
//            }
//            if (bitmap == null) {
//                println("bitmap null")
//                continue
//            } else {
//                val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
//                val processedBitmap = processImage(mutableBitmap)
//                val processedFrame = bitmapConverter.convert(processedBitmap)
//                grabbedFrame?.image = processedFrame?.image
//
//                recorder.record(grabbedFrame, AV_PIX_FMT_RGBA)
//            }
//
//        } catch (e: FrameRecorder.Exception) {
//            println("${e.message!!}")
//            e.printStackTrace()
//        }
//    }
//}