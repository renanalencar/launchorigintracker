package br.com.renanalencar.detectlauncher

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.renanalencar.detectlauncher.models.LaunchMetadata
import br.com.renanalencar.detectlauncher.models.LaunchOrigin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "launch_origin_prefs")

object LaunchOriginDataStore {

    private val KEY_ORIGIN = stringPreferencesKey("launch_origin")
    private val KEY_ORIGIN_NAME = stringPreferencesKey("origin_name")

    suspend fun saveMetadata(context: Context, metadata: LaunchMetadata) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ORIGIN] = metadata.origin.name
            metadata.originName?.let { prefs[KEY_ORIGIN_NAME] = it }
        }
    }

    fun readMetadata(context: Context): Flow<LaunchMetadata> {
        return context.dataStore.data.map { prefs ->
            LaunchMetadata(
                origin = prefs[KEY_ORIGIN]?.let { LaunchOrigin.valueOf(it) } ?: LaunchOrigin.UNKNOWN,
                originName = prefs[KEY_ORIGIN_NAME],
            )
        }
    }
}
