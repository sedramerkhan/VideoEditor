import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgcodecs.Imgcodecs.imwrite


@Composable
fun VideoEditorScreen() {
//    var vCapture = VideoCapture()
//    vCapture.open("C:\\Users\\Sedra\\Videos\\Captures\\sph3.mp4")
    var path ="C:\\Users\\Sedra\\Desktop\\legend.mp4" //"C:\\Users\\Sedra\\Desktop\\Files\\Montage Course\\RBCs_V6\\Render\\sph.mp4"
    var player by remember { mutableStateOf<VideoPlayerJava?>(null) }
    val matList = remember { mutableStateListOf<Mat>() }
    var showVideo by remember { mutableStateOf(false) }

    LaunchedEffect(Unit)
    {
        player = VideoPlayerJava(path)
        showVideo = true
    }

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

        if (matList.isNotEmpty()) {
            ImageLazyRow(matList)
//            player?.write(matList.take(60),20)
//            player?. addWaterMarkImage(matList.take(60),20)
            player?. addWaterMarkText(matList.take(60),20)

//            val source = matList[5].clone()
//           putText(
//                source,
//                "Tutorialspoint.com",
//                Point((source.rows() / 3).toDouble(), (source.cols() / 5).toDouble()),
//               FONT_ITALIC,
//               2.0,
//                Scalar(255.0, 150.0, 200.0,30.0)
//            )

//            val waterMark = Imgcodecs.imread("src/main/resources/tutorialspoint.jpg")
//            val ROI = Rect(0, 0, waterMark.cols(), waterMark.rows())
//            val source = matList[6].clone()
//            Core.addWeighted(source.submat(ROI), 0.8, waterMark, 0.2, 1.0, source.submat(ROI))
//
//            imwrite("watermarked.jpg", source)
            println("Done")
        }
    }

}