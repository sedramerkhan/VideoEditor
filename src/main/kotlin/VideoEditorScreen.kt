import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs.imread
import javax.swing.filechooser.FileNameExtensionFilter


@Composable
fun VideoEditorScreen() {

    var path by remember { mutableStateOf("src/main/resources/test1.mp4") }
    val editor by remember { mutableStateOf(VideoEditor()) }
    val matList = remember { mutableStateListOf<Mat>() }
    var state by remember { mutableStateOf(VideoState.Nothing) }
    val matListPreviews = remember { mutableStateListOf<Mat>() }
    val fps = remember { mutableStateOf(3) }
    var videoPath by remember { mutableStateOf("C:\\") }
    var audioPath by remember { mutableStateOf("") }
    val videoName = "\\withoutSound1.mp4"
    //Todo: delete this
    LaunchedEffect(Unit)
    {
        matList.addAll(editor.getFrames(path))
        matListPreviews.clone(matList)

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
                matList.addAll(editor.getFrames(path))
                matListPreviews.clone(matList)
            }
        }
    } else {
        val modifier = Modifier.padding(horizontal = 5.dp)
        val buttonModifier = Modifier.padding(top = 8.dp).width(200.dp)
        val textFieldModifier = Modifier.width(80.dp)

        var i by remember { mutableStateOf(0) }

        LaunchedEffect(state) {
            i = 0
            while (state == VideoState.Nothing) {
                if (i < matList.size - 1) i++
                else i = 0
                delay((1000 / (fps.value * 10)).toLong())
            }
        }
        Row(
            Modifier.fillMaxSize().padding(vertical = 16.dp, horizontal = 25.dp)
        ) {
            Column {

                Row {
                    Button(onClick = { state = VideoState.WaterMarkAdding }, modifier = buttonModifier)
                    { Text(text = "Add WaterMark") }
                    Spacer(modifier.width(90.dp))
                }
                if (state == VideoState.WaterMarkAdding) {
                    AddWaterMark(
                        modifier,
                        onImageSelected = { path, alpha ->
                            matListPreviews.clone(matList)
                            editor.addImageWaterMark(matList, path, alpha)
                        },
                        onVideoSelected = { path, alpha ->
                            matListPreviews.clone(matList)
                            editor.addVideoWaterMark(matList, path, alpha)
                        },
                        onTextSelected = { path, alpha ->
                            matListPreviews.clone(matList)
                            editor.addTextWaterMark(matList, path, alpha.toFloat())
                        },
                        onFinish = { state = VideoState.Nothing }
                    )
                }

                Button(onClick = { state = VideoState.StickerAdding }, modifier = buttonModifier)
                { Text(text = "Add Sticker") }
                if (state == VideoState.StickerAdding) {

                    DisplayStickers(modifier = modifier.width(280.dp)){
                        matListPreviews.clone(matList)
                        editor.addSticker(matList,it)
                        state = VideoState.Nothing
                    }
                }

                Button(onClick = { state = VideoState.VideosMerging }, modifier = buttonModifier)
                { Text(text = "Merge Videos") }
                if (state == VideoState.VideosMerging) {
                    val result = openLogFile(FileNameExtensionFilter("Videos", "avi", "mp4"))
                    matListPreviews.clone(matList)
                    result.forEach {
                        it?.let { editor.merge2Videos(matList, it.path) }
                    }
                    state = VideoState.Nothing
                }

                Button(onClick = { state = VideoState.VideoFromImages }, modifier = buttonModifier)
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
                    if (result.isNotEmpty()) {
                        matList.clear()
                        matList.addAll(list.map { image -> editor.resize(Size(350.0, 350.0), image) })
                    }
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
                            val list = matList.map { editor.resize(size, it) }
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
                        editor.moveVideoFrames(matList, start, end, position)
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
                        editor.deleteVideoFrames(matList, start, end)
                        state = VideoState.Nothing
                    }
                }

                Button(onClick = { state = VideoState.AudioAdding }, modifier = buttonModifier)
                { Text(text = "Add Audio") }
                if (state == VideoState.AudioAdding) {
                    val result = openLogFile(FileNameExtensionFilter("Audio", "mp3"))
                    if (result.isNotEmpty()) {
                        result[0]?.let {
                            audioPath = it.path
                            state = VideoState.VideoSavingWithAudio
                        }
                    } else
                        state = VideoState.Nothing
                }
                if (state == VideoState.VideoSavingWithAudio) {
                    ChooseFPS(
                        fps = fps,
                        modifier = modifier,
                        onFPSChosen = {
                            println(fps.value * 10)
                            val result = openLogFileDir()
                            result?.let {
                                matListPreviews.clone(matList)
                                videoPath = it.path
                                editor.write(matList, videoPath + videoName, fps.value * 10)
                                Audio().createVideoWithAudioAndPhoto(audioPath, videoPath, videoName, fps.value)
                            }
                            state = VideoState.Nothing
                        }
                    )
                }

                Button(onClick = { state = VideoState.VideoSaving }, modifier = buttonModifier)
                { Text(text = "Save Video to Disk") }
                if (state == VideoState.VideoSaving) {
                    ChooseFPS(
                        fps = fps,
                        modifier = modifier,
                        onFPSChosen = {
                            println(fps.value * 10)
                            val result = openLogFileDir()
                            result?.let {
                                matListPreviews.clone(matList)
                                videoPath = it.path
                                editor.write(matList, videoPath + videoName, fps.value * 10)
                            }
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
                { Text(text = "Undo ") }

            }

            Spacer(Modifier.width(30.dp))
            Column {
//                println(matList.size)
                ImageLazyRow(matList, Modifier)

                Box(modifier.fillMaxSize()) {
                    Image(
                        bitmap = matList[i % matList.size].asImageAsset(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.height(400.dp).width(600.dp).align(Alignment.Center)
                    )
                }
            }
        }
    }
}

