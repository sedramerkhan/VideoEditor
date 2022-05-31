import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.withTimeout
import org.jetbrains.skia.Image
import org.opencv.core.*
import java.awt.SystemColor
import java.awt.SystemColor.text
import javax.swing.filechooser.FileNameExtensionFilter


@Composable
fun VideoEditorScreen() {
//    var vCapture = VideoCapture()
//    vCapture.open("C:\\Users\\Sedra\\Videos\\Captures\\sph3.mp4")
    var path =
        "C:\\Users\\Sedra\\Desktop\\legend.mp4" //"C:\\Users\\Sedra\\Desktop\\Files\\Montage Course\\RBCs_V6\\Render\\sph.mp4"
    val player by remember { mutableStateOf(VideoPlayerJava()) }
    val matList = remember { mutableStateListOf<Mat>() }
    var showVideo by remember { mutableStateOf(false) }
    val fps = remember { mutableStateOf(2) }
    var state by remember { mutableStateOf(VideoState.Nothing) }
    var waterMarkState by remember { mutableStateOf(WaterMarkState.NoWaterMark) }

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

                Button(onClick = { state = VideoState.WaterMarkAdding }) { Text(text = "  Add WaterMark  ") }
                if (state == VideoState.WaterMarkAdding) {
                    SimpleRadioButtonComponent(waterMarkState,modifier) { waterMarkState = it }

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
                        cancel = { waterMarkState = WaterMarkState.NoWaterMark},
                        modifier,
                    )
                }



                Button(onClick = { state = VideoState.FPSChoosing }) { Text(text = "Save Video to Disk") }
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


//                player.addWaterMarkImage(matList.take(60), 20)
//            player.write(matList.take(60),20)
//            player. addWaterMarkText(matList.take(60),20)
//                val waterMarkFrames = player.getFrames("C:\\Users\\Sedra\\Desktop\\undo.mp4")
//            player.addWaterMarkVideo(matList, waterMarkFrames, 20)

//            val source = matList[5].clone()
//           putText(
//                source,
//                "Tutorialspoint.com",
//                Point((source.rows() / 3).toDouble(), (source.cols() / 5).toDouble()),
//               FONT_ITALIC,
//               1.0,
//                Scalar(0.0, 150.0, 200.0,255.0),1
//            )

//            var waterMark = Imgcodecs.imread("src/main/resources/tutorialspoint.jpg")
//
//            val source = matList[6].clone()
//            waterMark= player?.resize(source,waterMark)
//            val ROI = Rect(0, 0, waterMark.cols(), waterMark.rows())
//            Core.addWeighted(source.submat(ROI), 0.8, waterMark, 0.2, 1.0, source.submat(ROI))
////
//            imwrite("watermarked4.jpg", source)
//                println("Done")
            }
        }

    }

}