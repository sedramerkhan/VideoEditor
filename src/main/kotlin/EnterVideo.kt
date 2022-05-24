import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun EnterVideo(
    path: String,
    onPathChanged: (String) ->Unit,
    getVideo: () -> Unit,

    ){
    Box(Modifier.fillMaxSize().padding(horizontal = 150.dp))
    {
        Column(
            modifier = Modifier
                .fillMaxWidth().align(Alignment.Center),

            ) {
            Row ( Modifier
                .fillMaxWidth()){
                androidx.compose.material.TextField(
                    value = path,
                    onValueChange = { onPathChanged(it) },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("") },
                    label = { Text(text = "Enter Video") },
                    leadingIcon = { Icon(Icons.Filled.Search, "Video") },
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = getVideo, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Get Video")
            }
        }
    }
}