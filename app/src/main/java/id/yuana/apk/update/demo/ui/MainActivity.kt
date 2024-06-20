package id.yuana.apk.update.demo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import id.yuana.apk.update.demo.App
import id.yuana.apk.update.demo.BuildConfig
import id.yuana.apk.update.demo.ui.theme.APKUpdateDemoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCheckAppLatest()
        enableEdgeToEdge()
        setContent {
            APKUpdateDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }

    private fun onCheckAppLatest() {
        val api = (application as App).appModule.provideApiClient()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = api.getAppLatest()

                Log.d("YUANA", response.toString())

                if (response["version_code"].asInt > BuildConfig.VERSION_CODE) {
                    Log.d("YUANA", "should download the apk")
                    val apkUrl = response["apk_url"].asString
                    val responseBody = api.apkDownload(apkUrl).body()
                    saveApk(
                        responseBody,
                        "${cacheDir.path}/latest.apk",
                        {
                            Log.d("YUANA", "PROGRESS: $it")
                        }
                    ) { apkPath ->
                        updateApk(apkPath)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateApk(apkPath: String) {
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(
                getUriFromPath(apkPath),
                "application/vnd.android.package-archive"
            )
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }.also {
            startActivity(it)
        }

    }

    private fun getUriFromPath(apkPath: String): Uri {
        return FileProvider.getUriForFile(
            this,
            "$packageName.provider",
            File(apkPath)
        )
    }

    private fun saveApk(
        body: ResponseBody?,
        outputPath: String,
        onProgressChanged: (progress: Int) -> Unit,
        nextAction: (apkPath: String) -> Unit
    ): String {
        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            val fos = FileOutputStream(outputPath)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int

                val totalBytes = body.contentLength()
                var bytesRead = 0L
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)

                    bytesRead += read
                    val progress = (bytesRead * 100 / totalBytes).toInt()
                    onProgressChanged(progress)
                }
                output.flush()
            }
            Log.d("YUANA", "SAVED AT: $outputPath")
            nextAction(outputPath)
            return outputPath
        } catch (e: Exception) {
            Log.e("YUANA", "ERROR: $e")
        } finally {
            input?.close()
        }
        return ""
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    APKUpdateDemoTheme {
        Greeting("Android")
    }
}