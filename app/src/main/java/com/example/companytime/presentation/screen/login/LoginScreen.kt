package com.example.companytime.presentation.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.companytime.R
import com.example.companytime.presentation.screen.component.InputField
import com.example.companytime.presentation.screen.register.RegistrationContract
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginScreen(
    navigateToApp: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.actionFlow.collect { it ->
            when(it) {
                is LoginContract.Action.NavigateToApp -> navigateToApp()
                is LoginContract.Action.NavigateBack -> navigateBack()
            }
        }
    }

    if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            ContainedLoadingIndicator(
                modifier = Modifier.size(48.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.tertiary
            )
        }
    } else {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
                .fillMaxSize().padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.back),
                        contentDescription = "back"
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text="Войдите в аккаунт",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge
                )

                InputField(
                    value = state.email,
                    onValueChange = {viewModel.onIntent(LoginContract.Intent.ChangeEmail(it))},
                    label = "Электронная почта",
                    hint = "Введите электронную почту",
                    modifier = Modifier.fillMaxWidth(),
                    error = state.errorEmail
                )

                InputField(
                    value = state.password,
                    onValueChange = {viewModel.onIntent(LoginContract.Intent.ChangePassword(it))},
                    label = "Пароль",
                    hint = "Введите пароль",
                    modifier = Modifier.fillMaxWidth(),
                    error = state.errorPassword,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.onIntent(LoginContract.Intent.ClickPasswordVisible)
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (state.passwordVisible)
                                        R.drawable.eye_v else R.drawable.eye_nv
                                ),
                                contentDescription = "",
                                tint = if(state.errorPassword != null)MaterialTheme.colorScheme.error
                                else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )

                TextButton(
                    onClick = {},
                    modifier = Modifier.align(alignment = Alignment.End)
                ) {
                    Text(text="Забыли пароль?", style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary)
                }

                if (state.error != null) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center){
                        Text(
                            text = state.error!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                Button(
                    onClick = {
                        viewModel.onIntent(LoginContract.Intent.ClickLoginButton)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Войти",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}