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
<iframe width="560" height="315" src="https://www.youtube.com/embed/0OryIFgzt7M?si=DnE0bgsk7encwUzj" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>