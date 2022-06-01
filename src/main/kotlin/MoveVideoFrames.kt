import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MoveVideoFrames(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier,
    move: (Int, Int, Int) -> Unit
) {
    var start by remember { mutableStateOf("") }
    var end by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }

    Row(modifier) {
        CustomTextField(text = start, placeholder = "Start", modifier =textFieldModifier) {
            start = it
        }
        CustomTextField(text = end, placeholder = "End", modifier = textFieldModifier) {
            end = it
        }
        CustomTextField(text = position, placeholder = "Position", modifier = Modifier.width(100.dp)) {
            position = it
        }
        CustomIconButton {
            if (start.isDigit() && end.isDigit() && position.isDigit()) {
                move(start.toInt(), end.toInt(), position.toInt())
            }
        }
    }
}