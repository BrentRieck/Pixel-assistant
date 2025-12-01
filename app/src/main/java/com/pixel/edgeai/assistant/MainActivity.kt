package com.pixel.edgeai.assistant

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.pixel.edgeai.assistant.ui.screen.AssistantScreen
import com.pixel.edgeai.assistant.ui.screen.PermissionsScreen
import com.pixel.edgeai.assistant.ui.theme.PixelAssistantTheme
import com.pixel.edgeai.assistant.viewmodel.AssistantViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            PixelAssistantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val permissionsState = rememberMultiplePermissionsState(
                        permissions = listOf(
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.CAMERA
                        )
                    )
                    
                    if (permissionsState.allPermissionsGranted) {
                        val viewModel: AssistantViewModel = viewModel()
                        AssistantScreen(viewModel = viewModel)
                    } else {
                        PermissionsScreen(permissionsState = permissionsState)
                    }
                }
            }
        }
    }
}
