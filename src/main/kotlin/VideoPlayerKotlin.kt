
import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture


fun play( vCapture: VideoCapture) : Mat?{
    val mat = Mat()
    return if (!vCapture.isOpened) {
        println("media failed to open")
        null
    } else {
        if (vCapture.grab()) {
            vCapture.retrieve(mat)
            println(mat.rows());
        }
        else
            vCapture.release()
        mat
    }

}


