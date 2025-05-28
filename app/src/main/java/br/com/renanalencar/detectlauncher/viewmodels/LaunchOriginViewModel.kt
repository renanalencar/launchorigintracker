package br.com.renanalencar.detectlauncher.viewmodels

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.renanalencar.detectlauncher.LaunchOriginDataStore
import br.com.renanalencar.detectlauncher.models.LaunchMetadata
import br.com.renanalencar.detectlauncher.models.LaunchOrigin
import kotlinx.coroutines.launch

class LaunchOriginViewModel(application: Application) : AndroidViewModel(application) {

    private val context by lazy { application.applicationContext }
    private val _metadata = mutableStateOf(LaunchMetadata())
    val metadata: State<LaunchMetadata> = _metadata

    private var isFirstLaunch = true

    init {
        viewModelScope.launch {
            LaunchOriginDataStore.readMetadata(context)
                .collect { saved ->
                    _metadata.value = saved
                }
        }
    }

    fun detectInitialLaunch(intent: Intent?, callingPackage: String?) {
        if (!isFirstLaunch) return
        isFirstLaunch = false

        val origin = when {
            intent?.action == Intent.ACTION_MAIN &&
                    intent.hasCategory(Intent.CATEGORY_LAUNCHER) -> LaunchOrigin.LAUNCHER

            intent != null -> LaunchOrigin.EXTERNAL_APP
            else -> LaunchOrigin.UNKNOWN
        }

        val launchMetadata = LaunchMetadata(
            origin = origin,
            originName = intent?.getStringExtra("originName"),
        )

        _metadata.value = launchMetadata

        viewModelScope.launch {
            LaunchOriginDataStore.saveMetadata(context, launchMetadata)
        }
    }

    fun setReturnFromRecents() {
        updateOrigin(LaunchOrigin.RETURNED_FROM_RECENTS)
    }

    fun setReturnFromBack() {
        updateOrigin(LaunchOrigin.RETURNED_FROM_BACK)
    }

    //    private fun updateOrigin(newOrigin: LaunchOrigin) {
//        val updated = _metadata.value.copy(origin = newOrigin)
//        _metadata.value = updated
//        viewModelScope.launch {
//            LaunchOriginDataStore.saveMetadata(context, updated)
//        }
//    }
    private fun updateOrigin(newOrigin: LaunchOrigin) {
        val current = _metadata.value

        val updated = if (newOrigin == LaunchOrigin.EXTERNAL_APP) {
            current.copy(origin = newOrigin)
        } else {
            current.copy(
                origin = newOrigin,
                originName = null // Limpa o originName
            )
        }

        _metadata.value = updated

        viewModelScope.launch {
            LaunchOriginDataStore.saveMetadata(context, updated)
        }
    }

}

