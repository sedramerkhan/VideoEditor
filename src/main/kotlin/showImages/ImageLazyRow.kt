import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.opencv.core.Mat

@Composable
fun ImageLazyRow(matList: SnapshotStateList<Mat>) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
//    val list = matList.filterIndexed { index, _ -> index % 3 == 0 }
    LazyRow(
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                },
            )
    ) {
        itemsIndexed(matList) { index, mat ->
            Column {
                Image(
                    bitmap = mat.asImageAsset(),
                    contentDescription = null,
                    modifier = Modifier.width(100.dp).padding(vertical = 10.dp)
                )
                Text(
                    index.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

//            Spacer(Modifier.width(16.dp))
        }
    }
}