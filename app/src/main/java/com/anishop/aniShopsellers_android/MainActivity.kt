package com.anishop.aniShopsellers_android

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowInsetsControllerCompat
import com.anishop.aniShopsellers_android.presentation.navigation.AppNavigation
import com.anishop.aniShopsellers_android.ui.theme.AniShopSellersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // locks orientation to portrait as the app is only designed to work in portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //set the status bar icons to white colour
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = false

        setContent {
            window.navigationBarColor = Color(0xFF0E0E0E).toArgb()
            AniShopSellersTheme {
                AppNavigation()
            }
        }
    }
}