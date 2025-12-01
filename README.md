# Pixel Assistant - On-Device AI App

A fully on-device AI assistant app built specifically for the **Google Pixel 10 Pro XL**, leveraging **Google Edge AI** technologies including Gemini Nano and the Tensor G5 chip.

## 🌟 Features

### Core Capabilities
- **100% On-Device Processing**: All AI inference happens locally on your Pixel device
- **Gemini Nano Integration**: Utilizes Google's latest on-device language model
- **Privacy-First**: No data sent to the cloud - complete privacy
- **Real-time Responses**: Streaming text generation for instant feedback
- **Image Analysis**: Vision capabilities using on-device models
- **Conversation Context**: Maintains chat history for contextual responses

### Technical Highlights
- **Optimized for Tensor G5**: Takes full advantage of Pixel 10 Pro XL hardware
- **Material You Design**: Beautiful, adaptive UI with dynamic theming
- **Jetpack Compose**: Modern, declarative UI framework
- **Coroutines & Flow**: Efficient async processing and streaming
- **MVVM Architecture**: Clean, maintainable code structure

## 🏗️ Architecture

```
app/
├── ai/
│   └── EdgeAIManager.kt          # Core AI engine (Gemini Nano)
├── ui/
│   ├── screen/
│   │   ├── AssistantScreen.kt    # Main chat interface
│   │   └── PermissionsScreen.kt  # Permission handling
│   └── theme/                     # Material You theming
├── viewmodel/
│   └── AssistantViewModel.kt     # State management
├── service/
│   └── EdgeAIService.kt          # Background AI processing
└── PixelAssistantApp.kt          # Application class
```

## 🚀 Getting Started

### Prerequisites
- **Device**: Google Pixel 10 Pro XL (or Pixel 8+ for testing)
- **Android Studio**: Hedgehog (2023.1.1) or later
- **Minimum SDK**: API 34 (Android 14)
- **Target SDK**: API 35 (Android 15)

### Installation

1. **Clone and Open**
   ```bash
   cd pixel_assistant
   # Open in Android Studio
   ```

2. **Sync Dependencies**
   - Android Studio will automatically sync Gradle dependencies
   - Ensure you have the latest Android SDK components

3. **Build and Run**
   - Connect your Pixel 10 Pro XL via USB
   - Click "Run" in Android Studio
   - Grant required permissions (Camera, Microphone)

### First-Time Setup

When you first launch the app:
1. Grant camera and microphone permissions (required for full functionality)
2. Wait for the AI model to initialize (shows "On-Device AI Ready")
3. Start chatting with your assistant!

## 📱 Key Components

### EdgeAIManager
The heart of the application - manages all AI operations:
- Model initialization and lifecycle
- Text generation with streaming
- Image analysis
- Context-aware conversations

```kotlin
// Example usage
val aiManager = EdgeAIManager(context)
aiManager.initialize()

// Generate response
aiManager.generateResponse("Hello!").collect { chunk ->
    // Handle streaming response
}
```

### AssistantViewModel
Manages UI state and coordinates AI operations:
- Message history
- Processing states
- Error handling
- Image analysis coordination

### UI Components
- **AssistantScreen**: Main chat interface with Material You design
- **MessageBubble**: Individual message rendering
- **TypingIndicator**: Visual feedback during AI processing
- **BottomInputBar**: Message input with image attachment support

## 🔒 Privacy & Security

- **Zero Cloud Dependency**: All processing on-device
- **No Data Collection**: Your conversations never leave your device
- **Secure Storage**: Local data stored securely on device
- **Permissions**: Only requests what's necessary (camera, microphone)

## 🛠️ Technologies Used

### Core Technologies
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit
- **Coroutines & Flow**: Async programming and streaming
- **Material 3**: Latest Material Design components

### AI/ML Stack
- **Google AI Edge**: Gemini Nano on-device model
- **TensorFlow Lite**: Additional ML capabilities
- **Google AI SDK**: Generative AI client library

### Android Jetpack
- **ViewModel**: Lifecycle-aware state management
- **Room**: Local database (for conversation history)
- **DataStore**: Preferences storage
- **CameraX**: Camera integration

## 📋 Use Cases

1. **Text Assistant**: Ask questions, get help with tasks
2. **Image Analysis**: Analyze photos, identify objects
3. **Text Summarization**: Condense long text
4. **Creative Writing**: Generate ideas, write content
5. **Information Extraction**: Pull key details from text

## 🎨 Customization

### Changing AI Behavior
Modify the system instruction in `EdgeAIManager.kt`:
```kotlin
systemInstruction = content {
    text("Your custom system prompt here...")
}
```

### Adjusting Generation Parameters
In `EdgeAIManager.kt`:
```kotlin
val config = generationConfig {
    temperature = 0.7f  // Creativity (0.0-1.0)
    topK = 40           // Diversity
    topP = 0.95f        // Nucleus sampling
    maxOutputTokens = 2048
}
```

### UI Theming
Colors are in `ui/theme/Color.kt` and automatically adapt to system theme.

## 🐛 Troubleshooting

### Model Not Initializing
- Ensure device is Pixel 8+ with latest software
- Check Google Play Services is updated
- Verify sufficient storage space

### Slow Performance
- Ensure app has unrestricted battery usage
- Close other resource-intensive apps
- Check device temperature (AI throttles when hot)

### Permissions Issues
- Go to Settings → Apps → Pixel Assistant → Permissions
- Grant Camera and Microphone permissions
- Restart the app

## 🔄 Future Enhancements

- [ ] Voice input/output integration
- [ ] Multi-modal conversations (text + images)
- [ ] Context from device sensors
- [ ] Smart suggestions based on device usage
- [ ] Offline capabilities indicator
- [ ] Export conversation history
- [ ] Custom AI personalities

## 📄 License

This project is provided as-is for educational and development purposes.

## 🤝 Contributing

This is a demonstration project optimized for Pixel 10 Pro XL. Feel free to fork and customize for your needs!

## 📞 Support

For issues specific to:
- **Gemini Nano**: Check [Google AI documentation](https://ai.google.dev/)
- **Pixel devices**: Visit [Google Pixel Help](https://support.google.com/pixelphone)

---

**Built with ❤️ for Pixel 10 Pro XL**

*Leveraging the power of on-device AI for privacy, speed, and intelligence*
