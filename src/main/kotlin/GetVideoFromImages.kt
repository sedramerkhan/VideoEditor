import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
fun GetVideoFromImages(
    numberOfImages: String,
    onNumberChanged: (String) -> Unit,
    modifier: Modifier,
    addImages: (List<Mat>) -> Unit,
){
    Row(modifier) {
        CustomTextField(numberOfImages, "Enter Images Number") { onNumberChanged(it) }
        IconButton(onClick = {
            if(numberOfImages.isNotEmpty() && numberOfImages.all { it.isDigit() }) {
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
        ) {
            Icon(Icons.Default.Done, null, tint = MaterialTheme.colors.secondary)
        }
    }
}