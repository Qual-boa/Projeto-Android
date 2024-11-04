package com.example.qualaboaapp.ui.theme.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.*
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import java.util.Locale

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SearchAndLocationBar()
            Spacer(modifier = Modifier.height(20.dp))
            CategorySection()
            Spacer(modifier = Modifier.height(20.dp))
            MostSearchedEstablishments()
            Spacer(modifier = Modifier.height(20.dp))
            RecommendedEstablishments()
            Spacer(modifier = Modifier.height(20.dp))
            PopularFoodsSection()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(80.dp)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color(0xFFFFF1D5))
        ) {
            BottomMenu()
        }
    }
}

@Composable
fun SearchAndLocationBar() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var address by remember { mutableStateOf("São Paulo, Vila Madalena") }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = address,
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        if (showDialog) {
            LocationSelectionDialog(
                onDismiss = { showDialog = false },
                onLocationSelected = { newAddress ->
                    address = newAddress
                    showDialog = false
                },
                fusedLocationClient = fusedLocationClient,
                context = context
            )
        }
    }
}

@Composable
fun LocationSelectionDialog(
    onDismiss: () -> Unit,
    onLocationSelected: (String) -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
    context: Context
) {
    var currentLocation by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            coroutineScope.launch {
                getCurrentAddress(context, fusedLocationClient) { address ->
                    currentLocation = address
                    onLocationSelected(address)
                }
            }
        } else {
            // Permissão negada
            onDismiss()
        }
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(R.string.select_location)) },
        text = {
            Column {
                Button(onClick = {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        coroutineScope.launch {
                            getCurrentAddress(context, fusedLocationClient) { address ->
                                currentLocation = address
                                onLocationSelected(address)
                            }
                        }
                    } else {
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                }) {
                    Text(stringResource(R.string.use_current_location))
                }

                currentLocation?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(R.string.current_location, it))
                }
            }
        },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text(stringResource(R.string.fechar))
            }
        }
    )
}

fun getCurrentAddress(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    callback: (String) -> Unit
) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val addressText = addresses?.firstOrNull()?.getAddressLine(0)
                    ?: "Endereço não encontrado"
                callback(addressText)
            }
            fusedLocationClient.removeLocationUpdates(this)
        }
    }, Looper.getMainLooper())
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}
