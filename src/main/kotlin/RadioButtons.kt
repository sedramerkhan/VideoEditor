import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SimpleRadioButtonComponent(
    selectedOption: WaterMarkState,
    modifier: Modifier,
    onOptionSelected: (WaterMarkState) -> Unit,
) {
    val radioOptions = WaterMarkState.values()
//   var (selectedOption,onOptionSelected) = remember { mutableStateOf(radioOptions[3]) }
    Row(
    ) {
        radioOptions.forEach { text ->
            Row(
                modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            println(text)
                        }
                    )

            ) {
                RadioButton(
                    selected = (text == selectedOption), modifier = Modifier,
                    onClick = {
                        onOptionSelected(text)
                        println(text)
                    }
                )
                Text(
                    text = text.name,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(Modifier.width(5.dp))
            }
        }

    }
}
