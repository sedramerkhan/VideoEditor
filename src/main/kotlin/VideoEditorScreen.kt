import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.opencv.core.Mat

@Composable
fun VideoEditorScreen(matList: SnapshotStateList<Mat>) {
//    var vCapture = VideoCapture()
//    vCapture.open("C:\\Users\\Sedra\\Videos\\Captures\\sph3.mp4")

    ImageSlider(matList)


}