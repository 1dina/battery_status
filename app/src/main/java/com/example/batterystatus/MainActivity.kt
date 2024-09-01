package com.example.batterystatus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.batterystatus.ui.BatteryLevelViewModel
import com.example.batterystatus.ui.theme.BatteryStatusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BatteryStatusTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BatteryStatus(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BatteryStatus(modifier: Modifier = Modifier, viewModel: BatteryLevelViewModel = viewModel()) {
    val context = LocalContext.current
    viewModel.startBatteryLevelDetection(context)
    val resultImg by viewModel.batteryImageResource.collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = resultImg),
            contentDescription = "Battery Status"
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun BatteryStatusPreview() {
    BatteryStatus()

}


