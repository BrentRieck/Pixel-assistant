package com.pixel.edgeai.assistant.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixel.edgeai.assistant.PixelAssistantApp
import com.pixel.edgeai.assistant.ai.ChatMessage
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AssistantViewModel : ViewModel() {
    
    private val edgeAIManager = PixelAssistantApp.instance.edgeAIManager
    
    val messages = mutableStateListOf<ChatMessage>()
    val isProcessing = mutableStateOf(false)
    val isModelReady = mutableStateOf(false)
    val currentResponse = mutableStateOf("")
    val errorMessage = mutableStateOf<String?>(null)
    
    init {
        initializeModel()
    }
    
    private fun initializeModel() {
        viewModelScope.launch {
            isProcessing.value = true
            val success = edgeAIManager.initialize()
            isModelReady.value = success
            isProcessing.value = false
            
            if (!success) {
                errorMessage.value = "Failed to initialize AI model. Please check your device compatibility."
            }
        }
    }
    
    fun sendMessage(text: String) {
        if (text.isBlank() || isProcessing.value) return
        
        // Add user message
        val userMessage = ChatMessage(role = "user", content = text)
        messages.add(userMessage)
        
        isProcessing.value = true
        currentResponse.value = ""
        errorMessage.value = null
        
        viewModelScope.launch {
            try {
                edgeAIManager.chatWithContext(
                    messages = messages.filter { it.role == "user" || it.role == "assistant" },
                    newMessage = text
                )
                    .catch { e ->
                        errorMessage.value = "Error: ${e.message}"
                        isProcessing.value = false
                    }
                    .collect { chunk ->
                        currentResponse.value += chunk
                    }
                
                // Add assistant response
                if (currentResponse.value.isNotEmpty()) {
                    val assistantMessage = ChatMessage(
                        role = "assistant",
                        content = currentResponse.value
                    )
                    messages.add(assistantMessage)
                }
                
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
            } finally {
                isProcessing.value = false
                currentResponse.value = ""
            }
        }
    }
    
    fun analyzeImage(bitmap: Bitmap, prompt: String = "What's in this image?") {
        if (isProcessing.value) return
        
        val userMessage = ChatMessage(role = "user", content = "[Image] $prompt")
        messages.add(userMessage)
        
        isProcessing.value = true
        currentResponse.value = ""
        errorMessage.value = null
        
        viewModelScope.launch {
            try {
                edgeAIManager.analyzeImage(bitmap, prompt)
                    .catch { e ->
                        errorMessage.value = "Error: ${e.message}"
                        isProcessing.value = false
                    }
                    .collect { chunk ->
                        currentResponse.value += chunk
                    }
                
                if (currentResponse.value.isNotEmpty()) {
                    val assistantMessage = ChatMessage(
                        role = "assistant",
                        content = currentResponse.value
                    )
                    messages.add(assistantMessage)
                }
                
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
            } finally {
                isProcessing.value = false
                currentResponse.value = ""
            }
        }
    }
    
    fun summarizeText(text: String) {
        if (text.isBlank() || isProcessing.value) return
        
        val userMessage = ChatMessage(role = "user", content = "Summarize: ${text.take(100)}...")
        messages.add(userMessage)
        
        isProcessing.value = true
        currentResponse.value = ""
        
        viewModelScope.launch {
            try {
                edgeAIManager.summarizeText(text)
                    .catch { e ->
                        errorMessage.value = "Error: ${e.message}"
                        isProcessing.value = false
                    }
                    .collect { chunk ->
                        currentResponse.value += chunk
                    }
                
                if (currentResponse.value.isNotEmpty()) {
                    val assistantMessage = ChatMessage(
                        role = "assistant",
                        content = currentResponse.value
                    )
                    messages.add(assistantMessage)
                }
                
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
            } finally {
                isProcessing.value = false
                currentResponse.value = ""
            }
        }
    }
    
    fun clearConversation() {
        messages.clear()
        currentResponse.value = ""
        errorMessage.value = null
    }
    
    override fun onCleared() {
        super.onCleared()
        // Cleanup will be handled by the app lifecycle
    }
}
