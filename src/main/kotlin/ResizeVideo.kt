import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.opencv.core.Size

@Composable
fun ResizeVideo(
    modifier: Modifier,
    resize: (Size) -> Unit,
){
    var width by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    Row(modifier) {
        CustomTextField(width, "Width", Modifier.width(80.dp)) {width = it}
        CustomTextField(height, "Height", Modifier.width(80.dp)) { height = it }
        IconButton(onClick = {
            if (width.isNotEmpty() && width.all { it.isDigit() } && height.isNotEmpty() && height.all { it.isDigit() }) {
                val size = Size(width.toDouble(), height.toDouble())
                resize(size)
            }
        }
        ) { Icon(Icons.Default.Done, null, tint = MaterialTheme.colors.secondary) }

    }
}