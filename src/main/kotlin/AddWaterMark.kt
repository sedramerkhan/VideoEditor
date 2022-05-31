import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
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
    ){
    var text by remember{ mutableStateOf("")}
    when(waterMarkState){
        WaterMarkState.Image -> {
            val result = openLogFile(FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"))
            result?.let {
               onImageSelected(it.path)
            }
        }
        WaterMarkState.Video -> {
            val result = openLogFile(FileNameExtensionFilter("Videos", "avi", "mp4"))
            result?.let {
               onVideoSelected(it.path)
            }
        }
        WaterMarkState.Text ->{
            Row(
                modifier = Modifier,

                ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.padding(end = 16.dp),
                    placeholder = { Text("") },
                    label = { Text(text = "Enter Image") },
                    leadingIcon = { Icon(Icons.Filled.Search, "Image") },
                )

                IconButton(onClick = {
                    text.let {
                        onTextSelected(it)
                    }
                }
                ) {
                    Icon(Icons.Default.Done,null)
                }
            }
        }
        else -> {}
    }
}