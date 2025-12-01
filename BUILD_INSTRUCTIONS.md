# Building Pixel Assistant APK

## Prerequisites

- **Android Studio**: Latest version (Hedgehog 2023.1.1+)
- **Java JDK**: 17 or higher
- **Android SDK**: API 34 minimum
- **Device**: Google Pixel 8+ (for testing on-device AI)

## Quick Build Steps

### Option 1: Build in Android Studio (Recommended)

1. **Open Project**
   ```
   File → Open → Select `pixel_assistant` folder
   ```

2. **Sync Gradle**
   - Android Studio will automatically sync dependencies
   - Wait for "Gradle Sync Finished" notification

3. **Build APK**
   ```
   Build → Build Bundle(s) / APK(s) → Build APK(s)
   ```
   
4. **Find APK**
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

### Option 2: Command Line Build

```bash
cd pixel_assistant

# On Linux/Mac
./gradlew assembleDebug

# On Windows
gradlew.bat assembleDebug
```

APK Location: `app/build/outputs/apk/debug/app-debug.apk`

## Build Configurations

### Debug Build (Default)
- Includes debug symbols
- Not optimized
- Faster build time
- File: `app-debug.apk`

### Release Build
```bash
./gradlew assembleRelease
```
- Optimized and minimized
- Requires signing configuration
- Production-ready

## Troubleshooting

### Issue: Gradle Sync Failed
**Solution**: 
- Ensure internet connection (for dependency download)
- Update Android Gradle Plugin to latest
- File → Invalidate Caches / Restart

### Issue: SDK Not Found
**Solution**:
- Copy `local.properties.example` to `local.properties` in the project root and set `sdk.dir` to your installed SDK path (for
  example, `/home/<user>/Android/Sdk` on Linux/macOS or `C:\\Users\\<user>\\AppData\\Local\\Android\\Sdk` on Windows).

### Issue: Memory Error During Build
**Solution**:
- Edit `gradle.properties`:
  ```
  org.gradle.jvmargs=-Xmx4096m
  ```

### Issue: Gemini Nano Not Available
**Solution**:
- Only works on Pixel 8+ devices
- Ensure Google Play Services is updated
- May need to wait for broader rollout

## Testing on Device

1. **Enable Developer Options**
   - Settings → About Phone → Tap "Build number" 7 times

2. **Enable USB Debugging**
   - Settings → System → Developer Options → USB Debugging

3. **Connect Device & Install**
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

4. **Or use Android Studio**
   - Click "Run" button (Shift + F10)
   - Select your device

## System Requirements for Build

- **RAM**: 8GB minimum (16GB recommended)
- **Storage**: 10GB free space
- **OS**: Windows 10+, macOS 10.14+, Linux (Ubuntu 18.04+)

## Dependencies Size

- Total download: ~500MB (first build)
- Build time: 5-15 minutes (first build)
- Subsequent builds: 1-3 minutes

## Build Variants

```bash
# Debug (includes logs, not optimized)
./gradlew assembleDebug

# Release (optimized, requires signing)
./gradlew assembleRelease

# Clean build
./gradlew clean assembleDebug
```

## APK Information

- **Package Name**: com.pixel.edgeai.assistant
- **Min SDK**: API 34 (Android 14)
- **Target SDK**: API 34
- **Size**: ~15-20MB (debug), ~8-12MB (release)

## Signing for Release

Create `keystore.properties` in project root:
```properties
storePassword=your_store_password
keyPassword=your_key_password
keyAlias=your_key_alias
storeFile=path/to/keystore.jks
```

Update `app/build.gradle.kts` with signing config.

## Common Build Commands

```bash
# List all tasks
./gradlew tasks

# Check dependencies
./gradlew :app:dependencies

# Run lint checks
./gradlew lint

# Run unit tests
./gradlew test

# Generate coverage report
./gradlew jacocoTestReport
```

## Need Help?

- Check [Android Developer Documentation](https://developer.android.com/)
- Visit [Gradle Build Documentation](https://docs.gradle.org/)
- Review [Kotlin Android Guide](https://developer.android.com/kotlin)

---

**Note**: Building APK requires resources not available in this limited environment. 
Please build on your local machine using Android Studio or command line tools.
