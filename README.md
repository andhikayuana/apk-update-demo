# Demo APK Update

## Requirements
* JDK 17
* Android Studio Koala + SDK
* nodejs + npm
* ngrok for tunneling

## How to run (Local)

### Server
* after clone this repository
* cd to server directory
* `npm install`
* to run the server you can use `npm run serve` it will run on localhost:3000
* tunneling using `ngrok http 3000`, copy the base url from ngrok

### Android
* after clone this project
* open using android studio
* change base url inside `id.yuana.apk.update.demo.di.AppModule.kt` replace latest base url from the above step
* when running the app, it will automatically check and download silently
* you can check the logic inside `id.yuana.apk.update.demo.ui.MainActivity.kt`

## Demo
[![Watch the video](https://img.youtube.com/vi/0OryIFgzt7M/hqdefault.jpg)](https://www.youtube.com/watch?v=0OryIFgzt7M)
