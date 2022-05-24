import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs

fun asImageAsset(image: Mat): ImageBitmap {
    val bytes = MatOfByte()
    Imgcodecs.imencode(".jpeg", image, bytes)
    val byteArray = ByteArray((image.total() * image.channels()).toInt())
    bytes.get(0, 0, byteArray)
    return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
}