import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
fun AddWaterMark(
    waterMarkState: WaterMarkState,
    onImageSelected: (String) -> Unit,
    onVideoSelected: (String) -> Unit,
    onTextSelected: (String) -> Unit,
    cancel: () -> Unit,
    modifier: Modifier
    ){
    var text by remember{ mutableStateOf("")}

    when(waterMarkState){
        WaterMarkState.Image -> {
            val result = openLogFile(FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"))
            result?.let {
               onImageSelected(it.path)
            } ?: run{
               cancel()
            }
        }
        WaterMarkState.Video -> {
            val result = openLogFile(FileNameExtensionFilter("Videos", "avi", "mp4"))
            result?.let {
               onVideoSelected(it.path)
            }?: run{
                cancel()
            }
        }
        WaterMarkState.Text ->{
            Row(
                modifier = modifier,

                ) {
                CustomTextField(text,"Enter WaterMark"){ text =it }

                IconButton(onClick = {
                   if(text.isNotEmpty()) {
                       onTextSelected(text)
                    }
                }
                ) {
                    Icon(Icons.Default.Done,null,tint = MaterialTheme.colors.secondary)
                }
            }
        }
        else -> {}
    }
}