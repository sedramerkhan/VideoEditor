import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

fun asImageAsset(image: Mat): ImageBitmap {
    val bytes = MatOfByte()
    Imgcodecs.imencode(".jpeg", image, bytes)
    val byteArray = ByteArray((image.total() * image.channels()).toInt())
    bytes.get(0, 0, byteArray)
    return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
}

fun openLogFile(fileFilter : FileNameExtensionFilter): File? {
    var result: File?
    JFileChooser("${System.getProperty("user.home")}/Desktop").apply {
        fileSelectionMode = JFileChooser.FILES_ONLY
        var st =listOf("jpg", "png", "gif", "bmp")
        addChoosableFileFilter(fileFilter)
        isAcceptAllFileFilterUsed = true
        showOpenDialog(null)
        result = selectedFile
        println(selectedFile)
    }
    return result
}