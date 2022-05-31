import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomTextField(
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
) {
    TextField(
        value = text,
        onValueChange = { onTextChanged(it) },
        modifier = modifier,
        placeholder = { Text(text = placeholder) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            focusedIndicatorColor = MaterialTheme.colors.onPrimary,
            unfocusedIndicatorColor = MaterialTheme.colors.onPrimary,
            )
    )

}