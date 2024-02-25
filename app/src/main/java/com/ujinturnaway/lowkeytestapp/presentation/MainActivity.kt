package com.ujinturnaway.lowkeytestapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ujinturnaway.lowkeytestapp.presentation.ui.screen.FullImageScreen
import com.ujinturnaway.lowkeytestapp.presentation.ui.screen.HomeScreen
import com.ujinturnaway.lowkeytestapp.presentation.ui.theme.LowkeyTestAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(50)
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isDark = isSystemInDarkTheme()
            LowkeyTestAppTheme(darkTheme = isDark) {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setNavigationBarColor(
                        color = Color.Transparent, darkIcons = !isDark
                    )
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent, darkIcons = !isDark
                    )
                }
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(navController)
                    }

                    composable(
                        "photo/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType }),
                    ) {
                        FullImageScreen(navController)
                    }
                }
            }
        }
    }
}