import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
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

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick, modifier = Modifier)
    { Icon(Icons.Default.Done, null, tint = MaterialTheme.colors.secondary) }
}