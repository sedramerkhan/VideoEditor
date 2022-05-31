import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChooseFPS(
    fps: MutableState<Int>,
    onFPSChosen: ()-> Unit,
    modifier: Modifier
){
    Row(modifier) {
        Text(
            text = "Choose fps:",
            modifier = Modifier.align(Alignment.CenterVertically).padding(end = 10.dp)
        )
        NumberPicker(
            state = fps,
            range = 1..10,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        IconButton(
            modifier = Modifier.padding(start = 10.dp),
            onClick = onFPSChosen) {
            Icon(Icons.Default.Done, null, tint = MaterialTheme.colors.secondary)
        }
    }
}