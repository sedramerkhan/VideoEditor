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
    val fps = remember { mutableStateOf(2) }
    var state by remember { mutableStateOf(VideoState.Nothing) }
    var waterMarkState by remember { mutableStateOf(WaterMarkState.NoWaterMark) }
    var numberOfImages by remember { mutableStateOf("") }
    var width by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

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
        Column(Modifier.padding(25.dp)) {
            if (matList.isNotEmpty()) {
                println(matList.size)
                ImageLazyRow(matList)

                Button(onClick = { state = VideoState.WaterMarkAdding }, modifier = Modifier.padding(top = 8.dp))
                { Text(text = "    Add WaterMark     ") }
                if (state == VideoState.WaterMarkAdding) {
                    SimpleRadioButtonComponent(waterMarkState, modifier) { waterMarkState = it }

                    AddWaterMark(
                        waterMarkState,
                        onImageSelected = {
                            player.addImageWaterMark(matList, it)
                            waterMarkState = WaterMarkState.NoWaterMark
                        },
                        onVideoSelected = {
                            player.addVideoWaterMark(matList, it)
                            waterMarkState = WaterMarkState.NoWaterMark
                        },
                        onTextSelected = {
                            player.addTextWaterMark(matList, it)
                            waterMarkState = WaterMarkState.NoWaterMark
                        },
                        cancel = { waterMarkState = WaterMarkState.NoWaterMark },
                        modifier,
                    )
                }

                Button(onClick = {
                    state = VideoState.VideosMerging
                    val result = openLogFile(FileNameExtensionFilter("Videos", "avi", "mp4"))
                    result?.let {
                        player.merge2Videos(matList, it.path)
                    }
                }, modifier = Modifier.padding(top = 8.dp))
                { Text(text = "    Merge 2 Videos    ") }

                Button(onClick = { state = VideoState.VideoFromImages }, modifier = Modifier.padding(top = 8.dp))
                { Text(text = "get Video from images") }
                if (state == VideoState.VideoFromImages) {
                    GetVideoFromImages(
                        numberOfImages,
                        onNumberChanged = { numberOfImages = it },
                        modifier = modifier,
                        addImages = {
                            matList.clear()
                            matList.addAll(it.map { image -> player.resize(Size(350.0, 350.0), image) })
                        },
                    )
                }

                Button(onClick = { state = VideoState.VideoResizing }, modifier = Modifier.padding(top = 8.dp))
                { Text(text = "     Resize Video     ") }
                if (state == VideoState.VideoResizing) {
                 ResizeVideo(
                     modifier,width,height, onWidthChanged = {width = it}, onHeightChanged = {height = it},
                     resize = {
                         val size = Size(width.toDouble(), height.toDouble())
                         val list = matList.map {
                             player.resize(size, it)
                         }
                         println("resize video from ${matList[0].size()} to $size")
                         matList.clear()
                         matList.addAll(list)
                     }
                 )
                }

                Button(onClick = { state = VideoState.FPSChoosing }, modifier = Modifier.padding(top = 8.dp))
                { Text(text = "  Save Video to Disk  ") }
                if (state == VideoState.FPSChoosing) {
                    ChooseFPS(
                        fps,
                        modifier = modifier,
                        onFPSChosen = {
                            println(fps.value * 10)
                            player.write(matList, fps.value * 10)
                        }
                    )
                }


            }
        }

    }

}