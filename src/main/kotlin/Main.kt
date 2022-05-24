// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.File

@Composable
@Preview
fun App() {

    MaterialTheme {
            VideoEditorScreen()

    }
}

fun main() = application {
    val openCvPath = File("src/main/resources/java/x64/opencv_java455.dll").absolutePath
    println(openCvPath)
    System.load(openCvPath)

    val state = rememberWindowState()
//    val position = state.position
//    if (position is WindowPosition.Absolute) {
//        state.position = position.copy(x= 150.dp,y = 10.dp)
//    }

    val screenWidth = 1000.dp
    val screenHeight = 700.dp
    state.size = DpSize(screenWidth, screenHeight)
    Window(onCloseRequest = ::exitApplication, state, title = "MultiMedia") {
        App()
    }
}

//Desktop.getDesktop().open(File("C:\\Users\\Sedra\\Videos\\sph.m4v"))