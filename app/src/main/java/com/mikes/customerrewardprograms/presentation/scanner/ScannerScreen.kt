package com.mikes.customerrewardprograms.presentation.scanner


import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel,
    onNavigateToHome: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // ✅ Pedir permiso de cámara
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    LaunchedEffect(Unit) {
        // Pedir permiso al iniciar la pantalla
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    // Manejar estados de permiso
    when {
        cameraPermissionState.status.isGranted -> {
            // ✅ Permiso concedido - Mostrar cámara
            ScannerContent(
                state = state,
                onQRCodeScanned = { qrCode ->
                    viewModel.handleIntent(ScannerIntent.OnQrCodeScanned(qrCode))
                }
            )
        }

        cameraPermissionState.status.shouldShowRationale -> {
            // ⚠️ Usuario rechazó una vez - Explicar por qué necesitas el permiso
            PermissionRationaleScreen(
                onRequestPermission = {
                    cameraPermissionState.launchPermissionRequest()
                }
            )
        }

        else -> {
            // ❌ Permiso denegado permanentemente
            PermissionDeniedScreen()
        }
    }

    // Navigate on success
    LaunchedEffect(state.user) {
        if (state.user != null) {
            onNavigateToHome()
        }
    }
}

@Composable
private fun ScannerContent(
    state: ScannerState,
    onQRCodeScanned: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // ✅ Camera Preview
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            onQRCodeScanned = onQRCodeScanned
        )

        // Overlay UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                )
            ) {
                Text(
                    text = "Escanea tu código QR",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Indicador de escaneo
            if (!state.isLoading) {
                Card(
                    modifier = Modifier.size(250.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "📱",
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }
            }

            // Códigos de prueba
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Códigos de prueba:",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "USER001 • USER002 • MASTER001",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Loading
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Error
        state.error?.let { error ->
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(error)
            }
        }
    }
}

@Composable
private fun PermissionRationaleScreen(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📷",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Permiso de Cámara Requerido",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Necesitamos acceso a tu cámara para escanear códigos QR",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRequestPermission,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Conceder Permiso")
        }
    }
}

@Composable
private fun PermissionDeniedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🚫",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Permiso Denegado",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Por favor, habilita el permiso de cámara en la configuración de la app",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}