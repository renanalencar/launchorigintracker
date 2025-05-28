package br.com.renanalencar.detectlauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import br.com.renanalencar.detectlauncher.ui.theme.DetectLauncherTheme
import br.com.renanalencar.detectlauncher.viewmodels.LaunchOriginViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: LaunchOriginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.detectInitialLaunch(intent, callingActivity?.packageName)

        setContent {
            DetectLauncherTheme {
                MyApp(viewModel)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.setReturnFromRecents()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.setReturnFromBack()
    }
}
