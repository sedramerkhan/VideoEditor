import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

@Composable
fun SimpleRadioButtonComponent(
    selectedOption: WaterMarkState,
    modifier: Modifier,
    onOptionSelected: (WaterMarkState) -> Unit,
) {
    val radioOptions = WaterMarkState.values()
//   var (selectedOption,onOptionSelected) = remember { mutableStateOf(radioOptions[3]) }
    Row(modifier) {
        radioOptions.forEach { text ->
            Row(
               Modifier.padding(top = 16.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            println(text)
                        }
                    )

            ) {
                RadioButton(
                    selected = (text == selectedOption), modifier = Modifier.size(20.dp),
                    onClick = {
                        onOptionSelected(text)
                        println(text)
                    }
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = text.name.toLowerCase(),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(Modifier.width(8.dp))
            }
        }

    }
}
