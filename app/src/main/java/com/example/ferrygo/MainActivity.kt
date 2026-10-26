package com.example.ferrygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// Skema warna bertema laut: biru tua, hijau kebiruan, dan latar putih kebiruan
private val SkemaWarnaFerry = lightColorScheme(
    primary = Color(0xFF0B5C8E),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6ECF8),
    onPrimaryContainer = Color(0xFF062F49),
    secondary = Color(0xFF00897B),
    onSecondary = Color.White,
    background = Color(0xFFF3F8FB),
    onBackground = Color(0xFF10212B),
    surface = Color.White,
    onSurface = Color(0xFF10212B),
    outlineVariant = Color(0xFFB8CAD6)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = SkemaWarnaFerry) {
                Surface(
                    modifier = Modifier.fillMaxSize().safeDrawingPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TiketKapalScreen()
                }
            }
        }
    }
}
