# 🚀 Launch Origin Tracker – Android Kotlin + Jetpack Compose

Este projeto demonstra como identificar de maneira inteligente **como o usuário abriu seu app Android**, utilizando `Jetpack Compose`, `ViewModel`, e `DataStore Preferences`.

## 🔍 Funcionalidades

- Detecta **como o app foi iniciado**:
  - ✅ Pelo ícone do app (launcher)
  - ✅ Por outro aplicativo (via `Intent`)
  - ✅ Via botão de apps recentes (⬜)
  - ✅ Via botão de voltar (↩️)
- Exibe informações como `originName`, `userId`, `token` se fornecidos via `Intent`
- Persiste a origem e os extras usando `Jetpack DataStore`
- **Oculta** `originName` se o app for reaberto por métodos não relacionados a `EXTERNAL_APP`

---

## 🛠️ Tecnologias Utilizadas

- Kotlin
- Jetpack Compose
- ViewModel (Stateful)
- DataStore Preferences
- Android Lifecycle (ProcessLifecycleOwner)
- Intent Extras e análise de fluxo de abertura

---

## 🧱 Estrutura do Projeto

```text
├── MainActivity.kt
├── MyApp.kt
├── LaunchOrigin.kt               // Enum com os tipos de origem
├── LaunchMetadata.kt             // Data class com extras e origem
├── LaunchOriginViewModel.kt     // ViewModel que gerencia o estado
├── LaunchOriginDataStore.kt     // Persistência com DataStore

## 📦 Como Testar
### Via ADB:
```bash
adb shell am start -n com.seupacote/.MainActivity \
-a android.intent.action.VIEW \
--es originName "AppExterno" \
--es userId "42" \
--es token "secrettoken"
```

### Via outro app:
```kotlin
val intent = Intent(Intent.ACTION_VIEW).apply {
    setPackage("com.seupacote")
    putExtra("originName", "OutroApp")
    putExtra("userId", "123")
    putExtra("token", "abc123")
}
startActivity(intent)
```

## 🖼️ Interface
A tela principal exibe:
- Origem atual de abertura do app
- Informações recebidas por Intent, apenas se aplicável

## 📁 Persiste os seguintes dados com DataStore
- `origin`: tipo de origem (`LAUNCHER`, `EXTERNAL_APP`, etc.)
- `originName`: nome do app chamador (se `EXTERNAL_APP`)

## ❗ Observações
- O campo originName é limpado automaticamente se o app for aberto novamente via Launcher, Recents ou botão de voltar.
- A lógica está encapsulada e centralizada no LaunchOriginViewModel.

## 📌 Requisitos
- Android Studio Giraffe ou superior
- Kotlin 1.8+
- Compose BOM 2023.10+
- Target SDK 33 ou superior

## 📄 Licença
Este projeto é livre para uso educacional e demonstração de arquitetura limpa em apps Android com Jetpack Compose.
