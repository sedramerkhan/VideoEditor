package utils

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.opencv.core.Mat

@Composable
fun CustomLazyRow(
    matList: SnapshotStateList<Mat>, modifier: Modifier, showData: @Composable()(Mat,Int) -> Unit
){
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
//    val list = matList.filterIndexed { index, _ -> index % 3 == 0 }
    LazyRow(
        state = scrollState,
        modifier = modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                },
            )
    ) {
        itemsIndexed(matList){ index, mat ->
            showData(mat,index)
        }
    }
}