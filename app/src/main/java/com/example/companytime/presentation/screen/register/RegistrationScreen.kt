package com.example.companytime.presentation.screen.register

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.companytime.R
import com.example.companytime.presentation.navigation.Screen
import com.example.companytime.presentation.screen.component.DepartmentField
import com.example.companytime.presentation.screen.component.InputField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RegistrationScreen(
    navigateToApp: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: RegistrationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.actionFlow.collect { effect ->
            when (effect) {
                is RegistrationContract.Action.NavigateToApp -> {
                    navigateToApp()
                }
                is RegistrationContract.Action.NavigateBack -> navigateBack()
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
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)

            ) {
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
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Создайте аккаунт",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )

                    InputField(
                        value = state.name,
                        onValueChange = { viewModel.onIntent(RegistrationContract.Intent.ChangeName(it)) },
                        label = "ФИО",
                        hint = "Введите ФИО",
                        modifier = Modifier.fillMaxWidth(),
                        error = state.errorName
                    )

                    InputField(
                        value = state.email,
                        onValueChange = { viewModel.onIntent(RegistrationContract.Intent.ChangeEmail(it)) },
                        label = "Электронная почта",
                        hint = "Введите электронную почту",
                        modifier = Modifier.fillMaxWidth(),
                        error = state.errorEmail
                    )

                    InputField(
                        value = state.phoneNumber,
                        onValueChange = {
                            viewModel.onIntent(
                                RegistrationContract.Intent.ChangePhoneNumber(
                                    it
                                )
                            )
                        },
                        label = "Номер телефона",
                        hint = "Введите номер телефона",
                        modifier = Modifier.fillMaxWidth(),
                        error = state.errorPhoneNumber,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    DepartmentField(
                        value = state.department,
                        onValueChange = {
                            viewModel.onIntent(
                                RegistrationContract.Intent.SelectDepartment(
                                    it
                                )
                            )
                        },
                        label = "Департамент",
                        hint = "Введите департамент",
                        departments = state.departments,
                        error = state.errorDepartment,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        )
                    )

                    InputField(
                        value = state.password,
                        onValueChange = {
                            viewModel.onIntent(
                                RegistrationContract.Intent.ChangePassword(
                                    it
                                )
                            )
                        },
                        label = "Пароль",
                        hint = "Введите пароль",
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (state.isButtonPasswordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    viewModel.onIntent(RegistrationContract.Intent.ClickPasswordVisible)
                                },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    painter = painterResource(
                                        if (state.isButtonPasswordVisible)
                                            R.drawable.eye_v else R.drawable.eye_nv
                                    ),
                                    contentDescription = "",
                                    tint = if(state.errorPassword != null)MaterialTheme.colorScheme.error
                                    else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        error = state.errorPassword,
                    )
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
                        viewModel.onIntent(RegistrationContract.Intent.ClickRegisterButton)
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
                        text = "Зарегистрироваться",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}
