# Pink Streak Keeper — Android Studio prototype

Prototype native Android (Kotlin + Jetpack Compose) for:
- list/notes per recipient (one per line)
- editable message template and copy-to-clipboard
- reminder interval configuration (UI-only in this starter)
- local preferences and simple log
- open TikTok manually

## Build
1. Open this folder in Android Studio (Ladybug or newer).
2. Allow Gradle sync; requires Android SDK 35 and internet for dependency resolution.
3. Run on an Android device or emulator.

This is a starter project, not a verified APK. Scheduler, notification permissions/implementation,
Room database, real history records, and automated UI tests remain future work.
No auto-sending or TikTok login/session automation is included.

## Build APK using phone only (GitHub Actions)
This project includes `.github/workflows/build-apk.yml`.
1. Upload the project files to a GitHub repository (root of repo).
2. Open repository Actions tab; select "Build Android APK".
3. Choose Run workflow (or push to main to trigger).
4. When complete, open the workflow run and download artifact `PinkStreakKeeper-debug`.
5. Extract artifact and install APK on Android (allow installation from this source if Android asks).
