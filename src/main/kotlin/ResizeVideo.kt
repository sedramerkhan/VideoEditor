import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.opencv.core.Size

@Composable
fun ResizeVideo(
    modifier: Modifier,
    textFieldModifier: Modifier,
    resize: (Size) -> Unit,
){
    var width by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    Row(modifier) {
        CustomTextField(width, "Width", textFieldModifier) {width = it}
        CustomTextField(height, "Height", textFieldModifier) { height = it }
        CustomIconButton {
            if (width.isDigit() && height.isDigit()) {
                val size = Size(width.toDouble(), height.toDouble())
                resize(size)
            }
        }
    }
}