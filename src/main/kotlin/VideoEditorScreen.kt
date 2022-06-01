import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.NonCancellable.cancel
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgcodecs.Imgcodecs.imread
import java.awt.SystemColor.text
import javax.swing.filechooser.FileNameExtensionFilter


@Composable
fun VideoEditorScreen() {

    var path =
        "src/main/resources/test2.mp4" //"C:\\Users\\Sedra\\Desktop\\Files\\Montage Course\\RBCs_V6\\Render\\sph.mp4"
    val player by remember { mutableStateOf(VideoPlayerJava()) }
    val matList = remember { mutableStateListOf<Mat>() }
    var showVideo by remember { mutableStateOf(false) }
    var state by remember { mutableStateOf(VideoState.Nothing) }


    //Todo: delete this
    LaunchedEffect(Unit)
    {
        showVideo = player.openVideo(path)
    }

    if (!showVideo) {
        EnterVideo(
            path = path,
            onPathChanged = { path = it }
        ) {
            if (path.isNotEmpty()) {
                showVideo = player.openVideo(path)
            }
        }
    } else {
        LaunchedEffect(showVideo) {
            if (showVideo) {
                do {
                    val mat: Mat? = player.getFrame()
                    mat?.let {
                        matList.add(it)
                    }
                } while (mat != null)
            }
        }

        val modifier = Modifier.padding(horizontal = 5.dp)
        val buttonModifier = Modifier.padding(top = 8.dp).width(200.dp)
        val textFieldModifier = Modifier.width(80.dp)
        Column(Modifier.padding(25.dp)) {
            if (matList.isNotEmpty()) {
                println(matList.size)
                ImageLazyRow(matList)

                Button(onClick = { state = VideoState.WaterMarkAdding }, modifier = buttonModifier)
                { Text(text = "Add WaterMark") }
                if (state == VideoState.WaterMarkAdding) {
                    AddWaterMark(
                        modifier,
                        onImageSelected = { player.addImageWaterMark(matList, it) },
                        onVideoSelected = { player.addVideoWaterMark(matList, it) },
                        onTextSelected = { player.addTextWaterMark(matList, it) },
                        onFinish = { state = VideoState.Nothing }
                    )
                }

                Button(onClick = {
                    state = VideoState.VideosMerging
                    val result = openLogFile(FileNameExtensionFilter("Videos", "avi", "mp4"))
                    result?.let {
                        player.merge2Videos(matList, it.path)
                    }
                }, modifier =buttonModifier)
                { Text(text = "Merge 2 Videos") }

                Button(onClick = { state = VideoState.VideoFromImages }, modifier =buttonModifier)
                { Text(text = "Get Video from images") }
                if (state == VideoState.VideoFromImages) {
                    GetVideoFromImages(
                        modifier = modifier,
                        addImages = {
                            matList.clear()
                            matList.addAll(it.map { image -> player.resize(Size(350.0, 350.0), image) })
                        },
                    )
                }

                Button(onClick = { state = VideoState.VideoResizing }, modifier = buttonModifier)
                { Text(text = "Resize Video") }
                if (state == VideoState.VideoResizing) {
                    ResizeVideo(
                        modifier,
                        textFieldModifier,
                        resize = { size ->
                            val list = matList.map { player.resize(size, it) }
                            println("resize video from ${matList[0].size()} to $size")
                            matList.clear()
                            matList.addAll(list)
                        }
                    )
                }

                Button(onClick = { state = VideoState.VideoMoving }, modifier = buttonModifier)
                { Text(text = "Move Frames") }
                if (state == VideoState.VideoMoving) {
                   MoveVideoFrames(
                       modifier = modifier,
                       textFieldModifier,
                       ){ start , end ,position ->
                        player.moveVideoFrames(matList,start , end ,position)
                   }
                }

                Button(onClick = { state = VideoState.VideoCutting }, modifier = buttonModifier)
                { Text(text = "Delete Frames") }
                if (state == VideoState.VideoCutting) {
                    DeleteVideoFrames(
                        modifier = modifier,
                        textFieldModifier,
                        ){ start , end->
                        player.deleteVideoFrames(matList,start , end)
                    }
                }

                Button(onClick = { state = VideoState.FPSChoosing }, modifier =buttonModifier)
                { Text(text = "Save Video to Disk") }
                if (state == VideoState.FPSChoosing) {
                    ChooseFPS(
                        modifier = modifier,
                        onFPSChosen = { fps ->
                            println(fps * 10)
                            player.write(matList, fps * 10)
                            state = VideoState.Nothing
                        }
                    )
                }
            }
        }

    }

}