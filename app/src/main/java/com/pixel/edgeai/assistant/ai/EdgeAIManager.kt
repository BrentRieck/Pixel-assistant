package com.pixel.edgeai.assistant.ai

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/**
 * EdgeAIManager handles all on-device AI operations using Gemini Nano
 * Optimized for Pixel 10 Pro XL with Tensor G5 chip
 */
class EdgeAIManager(private val context: Context) {
    
    private var generativeModel: GenerativeModel? = null
    private var isInitialized = false
    private val systemPrompt = """You are a helpful AI assistant running completely on-device on a Pixel 10 Pro XL.\n""" +
        """You have access to the device's camera, microphone, and sensors.\n""" +
        """Provide concise, accurate, and contextual responses.\n""" +
        """Prioritize user privacy - all processing happens locally."""
    
    companion object {
        private const val TAG = "EdgeAIManager"
        private const val MODEL_NAME = "gemini-nano"
    }
    
    /**
     * Initialize the on-device Gemini Nano model
     */
    suspend fun initialize(): Boolean = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Initializing Gemini Nano for on-device inference...")
            
            val config = generationConfig {
                temperature = 0.7f
                topK = 40
                topP = 0.95f
                maxOutputTokens = 2048
            }

            generativeModel = GenerativeModel(
                modelName = MODEL_NAME,
                generationConfig = config
            )
            
            isInitialized = true
            Log.d(TAG, "Gemini Nano initialized successfully")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Gemini Nano: ${e.message}", e)
            isInitialized = false
            false
        }
    }
    
    /**
     * Generate text response from prompt
     */
    suspend fun generateResponse(prompt: String): Flow<String> = flow {
        if (!isInitialized) {
            val success = initialize()
            if (!success) {
                emit("Error: Unable to initialize AI model")
                return@flow
            }
        }
        
        try {
            Log.d(TAG, "Generating response for prompt: ${prompt.take(50)}...")

            val response = generativeModel?.generateContentStream(
                content {
                    text(systemPrompt)
                    text(prompt)
                }
            )
            
            response?.collect { chunk ->
                chunk.text?.let { text ->
                    emit(text)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error generating response: ${e.message}", e)
            emit("I apologize, but I encountered an error processing your request.")
        }
    }
    
    /**
     * Analyze image with optional text prompt
     */
    suspend fun analyzeImage(
        bitmap: Bitmap,
        prompt: String = "Describe this image in detail"
    ): Flow<String> = flow {
        if (!isInitialized) {
            val success = initialize()
            if (!success) {
                emit("Error: Unable to initialize AI model")
                return@flow
            }
        }
        
        try {
            Log.d(TAG, "Analyzing image with prompt: $prompt")

            val inputContent = content {
                text(systemPrompt)
                image(bitmap)
                text(prompt)
            }
            
            val response = generativeModel?.generateContentStream(inputContent)
            
            response?.collect { chunk ->
                chunk.text?.let { text ->
                    emit(text)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error analyzing image: ${e.message}", e)
            emit("I apologize, but I encountered an error analyzing the image.")
        }
    }
    
    /**
     * Chat with context (maintains conversation history)
     */
    suspend fun chatWithContext(
        messages: List<ChatMessage>,
        newMessage: String
    ): Flow<String> = flow {
        if (!isInitialized) {
            val success = initialize()
            if (!success) {
                emit("Error: Unable to initialize AI model")
                return@flow
            }
        }
        
        try {
            // Build context from previous messages
            val contextBuilder = StringBuilder()
            messages.takeLast(10).forEach { msg ->
                contextBuilder.append("${msg.role}: ${msg.content}\n")
            }
            contextBuilder.append("User: $newMessage")

            val response = generativeModel?.generateContentStream(
                content {
                    text(systemPrompt)
                    text(contextBuilder.toString())
                }
            )
            
            response?.collect { chunk ->
                chunk.text?.let { text ->
                    emit(text)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in chat: ${e.message}", e)
            emit("I apologize, but I encountered an error in our conversation.")
        }
    }
    
    /**
     * Summarize text
     */
    suspend fun summarizeText(text: String): Flow<String> = flow {
        val prompt = "Please provide a concise summary of the following text:\n\n$text"
        generateResponse(prompt).collect { chunk ->
            emit(chunk)
        }
    }
    
    /**
     * Extract key information from text
     */
    suspend fun extractKeyInfo(text: String): Flow<String> = flow {
        val prompt = """Extract the key information, entities, and important points from this text:
            
            $text
            
            Provide the extraction in a structured format."""
        
        generateResponse(prompt).collect { chunk ->
            emit(chunk)
        }
    }
    
    /**
     * Check if model is ready
     */
    fun isReady(): Boolean = isInitialized
    
    /**
     * Cleanup resources
     */
    fun cleanup() {
        generativeModel = null
        isInitialized = false
        Log.d(TAG, "EdgeAIManager cleaned up")
    }
}

data class ChatMessage(
    val role: String, // "user" or "assistant"
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
