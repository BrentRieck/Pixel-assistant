package com.pixel.edgeai.assistant

import android.app.Application
import com.pixel.edgeai.assistant.ai.EdgeAIManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class PixelAssistantApp : Application() {
    
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    
    lateinit var edgeAIManager: EdgeAIManager
        private set
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        
        // Initialize Edge AI Manager
        edgeAIManager = EdgeAIManager(this)
    }
    
    companion object {
        lateinit var instance: PixelAssistantApp
            private set
    }
}
