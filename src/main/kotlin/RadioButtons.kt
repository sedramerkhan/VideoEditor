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
    onOptionSelected: (WaterMarkState) -> Unit
) {
    val radioOptions = WaterMarkState.values()
    var selectedOption by remember { mutableStateOf(radioOptions[3]) }
//   var (selectedOption,onOptionSelected) = remember { mutableStateOf(radioOptions[3]) }
    Row(
    ) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            selectedOption = text
                            onOptionSelected(text)
                            println(text)
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                RadioButton(
                    selected = (text == selectedOption), modifier = Modifier.padding(all = Dp(value = 2F)),
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
