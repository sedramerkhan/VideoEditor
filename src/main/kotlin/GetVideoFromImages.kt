import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
fun GetVideoFromImages(
    modifier: Modifier,
    addImages: (List<Mat>) -> Unit,
) {
    var numberOfImages by remember { mutableStateOf("") }

    Row(modifier) {
        CustomTextField(numberOfImages, "Enter Images Number", Modifier.width(200.dp)) { numberOfImages = it }
        CustomIconButton {
            if (numberOfImages.isDigit()) {
                val list = mutableListOf<Mat>()
                for (i in 1..numberOfImages.toInt()) {
                    val result = openLogFile(FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"))
                    result?.let {
                        val image = Imgcodecs.imread(it.path)
                        list.add(image)
                    }
                    addImages(list)
                }
            }
        }
    }
}