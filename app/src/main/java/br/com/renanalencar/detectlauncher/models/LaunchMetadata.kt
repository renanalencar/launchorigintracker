package br.com.renanalencar.detectlauncher.models

data class LaunchMetadata(
    val origin: LaunchOrigin = LaunchOrigin.UNKNOWN,
    val originName: String? = null,
)
