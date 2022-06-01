import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.opencv.core.Mat

@Composable
fun ImageSlider(matList: SnapshotStateList<Mat>){
    var index by remember { mutableStateOf(0) }
    Column(modifier = Modifier.padding(horizontal = 30.dp)) {
        Row{
            Slider(
                value = index.toFloat(),
                onValueChange = { index = it.toInt() },
                valueRange = 0f..matList.size.toFloat()-1,
//            steps = 1,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colors.secondary,
                    activeTrackColor = MaterialTheme.colors.secondary
                ),
                modifier = Modifier.weight(1f)

            )
            Text(index.toString(), modifier = Modifier.padding(start = 30.dp).align(Alignment.CenterVertically))
        }

        Image(
            bitmap = matList[index].asImageAsset(), contentDescription = null
        )

        Text(matList.size.toString())
    }
}