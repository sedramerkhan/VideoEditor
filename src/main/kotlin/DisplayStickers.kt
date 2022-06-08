import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs.imread
import utils.CustomLazyRow

@Composable
fun DisplayStickers(modifier: Modifier, onSelectedEmoji: (Mat)-> Unit){
    val emojis = mutableStateListOf<Mat>()
    for(i in 0..18){
        emojis.add(imread("src/main/resources/emojis/img_$i.png"))
    }
    CustomLazyRow(emojis,modifier){ mat,_ ->
        Image(
            bitmap = mat.asImageAsset(),
            contentDescription = null,
            modifier = Modifier.size(50.dp).background(Color.Unspecified).padding(vertical = 10.dp, horizontal = 2.dp).clickable {
               onSelectedEmoji(mat)
            },
            contentScale = ContentScale.FillBounds
        )
    }
}