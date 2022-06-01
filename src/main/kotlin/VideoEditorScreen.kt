import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs.imread
import javax.swing.filechooser.FileNameExtensionFilter


@Composable
fun VideoEditorScreen() {

    var path =
        "src/main/resources/test1.mp4" //"C:\\Users\\Sedra\\Desktop\\Files\\Montage Course\\RBCs_V6\\Render\\sph.mp4"
    val player by remember { mutableStateOf(VideoPlayerJava()) }
    var matList = remember { mutableStateListOf<Mat>() }
    var state by remember { mutableStateOf(VideoState.Nothing) }
    var matListPreviews = remember { mutableStateListOf<Mat>() }

    //Todo: delete this
    LaunchedEffect(Unit)
    {
        matList.addAll(player.getFrames(path))
//        player.openVideo(path)
//        do {
//            val mat: Mat? = player.getFrame()
//            mat?.let {
//                matList.add(it)
//            }
//        } while (mat != null)
    }

    if (matList.isEmpty()) {
        EnterVideo(
            path = path,
            onPathChanged = { path = it }
        ) {
            if (path.isNotEmpty()) {
                matList.addAll(player.getFrames(path))
            }
        }
    } else {
        val modifier = Modifier.padding(horizontal = 5.dp)
        val buttonModifier = Modifier.padding(top = 8.dp).width(200.dp)
        val textFieldModifier = Modifier.width(80.dp)

        Column(Modifier.padding(25.dp)) {

            println(matList.size)
            ImageLazyRow(matList)

            Button(onClick = { state = VideoState.WaterMarkAdding }, modifier = buttonModifier)
            { Text(text = "Add WaterMark") }
            if (state == VideoState.WaterMarkAdding) {
                AddWaterMark(
                    modifier,
                    onImageSelected = {
                        matListPreviews.clone(matList)
                        player.addImageWaterMark(matList, it)
                    },
                    onVideoSelected = {
                        matListPreviews.clone(matList)
                        player.addVideoWaterMark(matList, it)
                    },
                    onTextSelected = {
                        matListPreviews.clone(matList)
                        player.addTextWaterMark(matList, it)
                    },
                    onFinish = { state = VideoState.Nothing }
                )
            }

            Button(onClick = { state = VideoState.VideosMerging }, modifier = buttonModifier)
            { Text(text = "Merge 2 Videos") }
            if (state == VideoState.VideosMerging) {
                val result = openLogFile(FileNameExtensionFilter("Videos", "avi", "mp4"))
                matListPreviews.clone(matList)
                result.forEach {
                    it?.let { player.merge2Videos(matList, it.path) }
                }
                state = VideoState.Nothing
            }

            Button(onClick = {
                state = VideoState.VideoFromImages
            }, modifier = buttonModifier)
            { Text(text = "Get video from images") }
            if (state == VideoState.VideoFromImages) {
                val list = mutableListOf<Mat>()
                val result = openLogFile(FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"))
                result.forEach {
                    it?.let {
                        val image = imread(it.path)
                        list.add(image)
                    }
                }
                matList.clear()
                matList.addAll(list.map { image -> player.resize(Size(350.0, 350.0), image) })
                state = VideoState.Nothing
            }

            Button(onClick = { state = VideoState.VideoResizing }, modifier = buttonModifier)
            { Text(text = "Resize Video") }
            if (state == VideoState.VideoResizing) {
                ResizeVideo(
                    modifier,
                    textFieldModifier,
                    resize = { size ->
                        matListPreviews.clone(matList)
                        val list = matList.map { player.resize(size, it) }
                        println("resize video from ${matList[0].size()} to $size")
                        matList.clear()
                        matList.addAll(list)
                        state = VideoState.Nothing
                    }
                )
            }

            Button(onClick = { state = VideoState.VideoMoving }, modifier = buttonModifier)
            { Text(text = "Move Frames") }
            if (state == VideoState.VideoMoving) {
                MoveVideoFrames(
                    modifier = modifier,
                    textFieldModifier,
                ) { start, end, position ->
                    matListPreviews.clone(matList)
                    player.moveVideoFrames(matList, start, end, position)
                    state = VideoState.Nothing
                }
            }

            Button(onClick = { state = VideoState.VideoCutting }, modifier = buttonModifier)
            { Text(text = "Delete Frames") }
            if (state == VideoState.VideoCutting) {
                DeleteVideoFrames(
                    modifier = modifier,
                    textFieldModifier,
                ) { start, end ->
                    matListPreviews.clone(matList)
                    player.deleteVideoFrames(matList, start, end)
                    state = VideoState.Nothing
                }
            }

            Button(onClick = { state = VideoState.FPSChoosing }, modifier = buttonModifier)
            { Text(text = "Save Video to Disk") }
            if (state == VideoState.FPSChoosing) {
                ChooseFPS(
                    modifier = modifier,
                    onFPSChosen = { fps ->
                        println(fps * 10)
                        matListPreviews.clone(matList)
                        player.write(matList, fps * 10)
                        state = VideoState.Nothing
                    }
                )
            }
            Button(onClick = {
                println(matListPreviews.size)
                state = VideoState.Nothing
                matList.clear()
                matList.addAll(matListPreviews)
            }, modifier = buttonModifier)
            { Text(text = "Go Back") }

        }
    }
}

