import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
fun AddWaterMark(
    modifier: Modifier,
    onImageSelected: (String) -> Unit,
    onVideoSelected: (String) -> Unit,
    onTextSelected: (String) -> Unit,
    onFinish: () -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var waterMarkState by remember { mutableStateOf(WaterMarkState.NoWaterMark) }

    SimpleRadioButtonComponent(waterMarkState, modifier) { waterMarkState = it }

    when (waterMarkState) {
        WaterMarkState.Image -> {
            val result = openLogFile(FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"))
            if(result.isNotEmpty()) {
                result[0]?.let {
                    onImageSelected(it.path)
                }
                onFinish()
            }
        }
        WaterMarkState.Video -> {
            val result = openLogFile(FileNameExtensionFilter("Videos", "avi", "mp4"))
            if(result.isNotEmpty()) {
                result[0]?.let {
                    onVideoSelected(it.path)
                }
            }
            onFinish()
        }
        WaterMarkState.Text -> {
            Row(modifier = modifier,) {
                CustomTextField(text, "Enter WaterMark", Modifier.width(200.dp)) { text = it }

                CustomIconButton {
                    if (text.isNotEmpty()) {
                        onTextSelected(text)
                        onFinish()
                    }
                }
            }
        }
        else -> {}
    }
}