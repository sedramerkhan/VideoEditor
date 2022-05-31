import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.opencv.core.Size

@Composable
fun ResizeVideo(
    modifier: Modifier,
    width: String,
    height: String,
    resize: () -> Unit,
    onWidthChanged: (String) -> Unit,
    onHeightChanged: (String) -> Unit,
){
    Row(modifier) {
        CustomTextField(width, "Width", Modifier.width(80.dp)) {onWidthChanged(it)}
        CustomTextField(height, "Height", Modifier.width(80.dp)) { onHeightChanged(it) }
        IconButton(onClick = {
            if (width.isNotEmpty() && width.all { it.isDigit() } && height.isNotEmpty() && height.all { it.isDigit() }) {
              resize()
            }
        }
        ) { Icon(Icons.Default.Done, null, tint = MaterialTheme.colors.secondary) }

    }
}