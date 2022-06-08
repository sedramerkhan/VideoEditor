import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.opencv.core.Mat
import utils.CustomLazyRow

@Composable
fun ImageLazyRow(matList: SnapshotStateList<Mat>, modifier: Modifier) {

    CustomLazyRow(matList, modifier) { mat, index ->
        Column {
            Image(
                bitmap = mat.asImageAsset(),
                contentDescription = null,
                modifier = Modifier.size(100.dp).padding(vertical = 10.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                index.toString(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

}