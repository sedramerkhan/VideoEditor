import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

fun Mat.asImageAsset(): ImageBitmap {
    val bytes = MatOfByte()
    Imgcodecs.imencode(".jpeg", this, bytes)
    val byteArray = ByteArray((this.total() * this.channels()).toInt())
    bytes.get(0, 0, byteArray)
    return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
}

fun openLogFile(fileFilter : FileNameExtensionFilter): MutableList<File?> {
    val result: MutableList<File?> = mutableListOf()
    JFileChooser("${System.getProperty("user.home")}/Desktop").apply {
        fileSelectionMode = JFileChooser.FILES_ONLY
        addChoosableFileFilter(fileFilter)
        isAcceptAllFileFilterUsed = true
        isMultiSelectionEnabled = true
        showOpenDialog(null)
        result.addAll(selectedFiles)
        println("${selectedFiles.size}files is/are selected")
    }
    return result
}

fun String.isDigit() = isNotEmpty() && all{ it.isDigit()}

fun SnapshotStateList<Mat>.clone(list: SnapshotStateList<Mat>) {
    this.clear()
    this.addAll(list)
}