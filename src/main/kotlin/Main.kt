// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.File


fun main() = application {
    val state = rememberWindowState()
    val screenWidth = 1200.dp
    val screenHeight = 700.dp
    state.size = DpSize(screenWidth, screenHeight)
    val openCvPath = File("src/main/resources/java/x64/opencv_java455.dll").absolutePath
    println(openCvPath)
    System.load(openCvPath)

    Window(
        onCloseRequest = ::exitApplication,
        state = state, title = "MultiMedia"
    ) {
        MaterialTheme {
            VideoEditorScreen()
        }
    }

}

//Desktop.getDesktop().open(File("C:\\Users\\Sedra\\Videos\\sph.m4v"))