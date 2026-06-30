package com.example.companytime

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.companytime.presentation.navigation.CompanyNavigationBar
import com.example.companytime.presentation.navigation.Navigator
import com.example.companytime.presentation.navigation.Screen
import com.example.companytime.presentation.screen.login.LoginScreen
import com.example.companytime.presentation.screen.register.RegistrationScreen
import com.example.companytime.presentation.screen.start.StartScreen
import com.example.companytime.ui.theme.CompanyTimeTheme
import org.koin.compose.koinInject
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    @OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompanyTimeTheme {
                val entryProvider = koinEntryProvider<Any>()
                val navigator = koinInject<Navigator>()
                val currentScreen = navigator.backStack.lastOrNull()
                Log.d("NAV_BUG", "ЭКРАН НА ЭКРАНЕ СЕЙЧАС: $currentScreen")

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentScreen is Screen.Main) {
                            CompanyNavigationBar(
                                selectedKey = currentScreen,
                                onSelectedKey = { tab ->
                                    navigator.switch(tab)
                                },
                                modifier = Modifier.windowInsetsPadding(NavigationBarDefaults.windowInsets)
                            )
                        }
                    },
                    contentColor = Color.Transparent
                ) { innerPadding ->
                    NavDisplay(
                        modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                        backStack = navigator.backStack,
                        onBack = {navigator.goBack()},
                        entryProvider = entryProvider,
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        )
                    )
                }

            }
        }
    }
}
