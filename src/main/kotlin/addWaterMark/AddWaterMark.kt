import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
fun AddWaterMark(
    modifier: Modifier,
    onImageSelected: (String, Double) -> Unit,
    onVideoSelected: (String, Double) -> Unit,
    onTextSelected: (String, Double) -> Unit,
    onFinish: () -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var waterMarkState by remember { mutableStateOf(WaterMarkState.Nothing) }
    SimpleRadioButtonComponent(waterMarkState, modifier) { waterMarkState = it }

    when (waterMarkState) {
        WaterMarkState.Image -> {
            EnterOpacity { alpha ->
                val result = openLogFile(FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"))
                if (result.isNotEmpty()) {
                    result[0]?.let {

                        onImageSelected(it.path, alpha)
                        onFinish()
                    } ?: run {
                        onFinish()
                    }
                }

            }
        }
        WaterMarkState.Video -> {
            EnterOpacity { alpha ->
                val result = openLogFile(FileNameExtensionFilter("Videos", "avi", "mp4"))
                if (result.isNotEmpty()) {
                    result[0]?.let {
                        onVideoSelected(it.path, alpha)

                        onFinish()
                    } ?: run {
                        onFinish()
                    }
                }
            }
        }
        WaterMarkState.Text -> {
            Row(modifier = modifier) {
                CustomTextField(text, "WaterMark", Modifier.width(150.dp)) { text = it }
                var alpha by remember { mutableStateOf("") }
                CustomTextField(alpha, "Alpha", modifier = Modifier.width(80.dp)) {
                    alpha = it
                }
                CustomIconButton {
                    if (text.isNotEmpty() && alpha.isDigit()) {
                        onTextSelected(text, alpha.toDouble())
                        onFinish()
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
fun EnterOpacity(
    addWaterMark: (Double) -> Unit
) {
    var alpha by remember { mutableStateOf("") }
    Row {
        CustomTextField(alpha, "Alpha", modifier = Modifier.width(80.dp)) {
            alpha = it
        }
        CustomIconButton {
            if (alpha.isDigit()) {
                val alphaNum = if (alpha.toDouble() < 0 || alpha.toDouble() > 1) 1.0 else alpha.toDouble()
                addWaterMark(alphaNum)
            }
        }
    }

}