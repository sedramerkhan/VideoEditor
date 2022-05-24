import androidx.compose.runtime.*
import org.opencv.core.Mat

@Composable
fun VideoEditorScreen() {
//    var vCapture = VideoCapture()
//    vCapture.open("C:\\Users\\Sedra\\Videos\\Captures\\sph3.mp4")
    var path = "C:\\Users\\Sedra\\Desktop\\Files\\Montage Course\\RBCs_V6\\Render\\sph.mp4"
    var player by remember { mutableStateOf<VideoPlayerJava?>(null) }
    val matList = remember { mutableStateListOf<Mat>() }
    var showVideo by remember { mutableStateOf(false) }

    if(!showVideo) {
            EnterVideo(
                path = path,
                onPathChanged = { path = it }
            ) {
                if (path.isNotEmpty()) {
                    player = VideoPlayerJava(path)
                    showVideo = true
                }
            }
    }
    else {
        LaunchedEffect(showVideo) {
            if (showVideo) {
                do {
                    val mat: Mat? = player?.play()
                    mat?.let {
                        matList.add(it)
                    }
                } while (mat != null)
            }
        }

        if (matList.isNotEmpty())
            ImageSlider(matList)
    }

}