package br.com.renanalencar.detectlauncher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.renanalencar.detectlauncher.models.LaunchOrigin
import br.com.renanalencar.detectlauncher.viewmodels.LaunchOriginViewModel

@Composable
fun MyApp(viewModel: LaunchOriginViewModel) {
    val metadata by viewModel.metadata

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Origem da abertura do app:", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                when (metadata.origin) {
                    LaunchOrigin.LAUNCHER -> "Aberto pelo ícone do app"
                    LaunchOrigin.EXTERNAL_APP -> "Aberto por outro app"
                    LaunchOrigin.RETURNED_FROM_RECENTS -> "Retornado via apps recentes"
                    LaunchOrigin.RETURNED_FROM_BACK -> "Retornado via botão voltar"
                    LaunchOrigin.UNKNOWN -> "Origem desconhecida"
                },
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            metadata.originName?.let {
                if (metadata.origin == LaunchOrigin.EXTERNAL_APP) {
                    Text("originName: $it", textAlign = TextAlign.Center)
                }
            }
        }
    }
}
