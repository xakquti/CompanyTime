package com.example.companytime.presentation.screen.profile.editinfo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.companytime.R
import com.example.companytime.presentation.screen.component.DepartmentField
import com.example.companytime.presentation.screen.profile.ProfileContract
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EditInfoScreen(
    navigateToProfile: () -> Unit,
    viewModel: EditInfoScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.actionFlow.collect { it ->
            when (it) {
                is EditInfoScreenContract.Action.NavigateToProfile -> navigateToProfile()
            }
        }
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(
                vertical = 24.dp
            )
    ) {
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

        } else if (state.error != null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {

                Box(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(56.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = "${state.error}", color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center

                ) {
                    PhotoPicker(
                        modifier = Modifier,
                        photoUrl = state.photoUrl,
                        onSelected = {
                            viewModel.onIntent(EditInfoScreenContract.Intent.ChangePhotoUri(it))
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2.5f)
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "ФИО", color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.titleSmall
                        )
                        TextField(
                            value = state.name,
                            onValueChange = {
                                viewModel.onIntent(
                                    EditInfoScreenContract.Intent.ChangeName(
                                        it
                                    )
                                )
                            },
                            textStyle = MaterialTheme.typography.titleMedium,
                            colors = TextFieldDefaults.colors(
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(56.dp)
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = "мобильный телефон",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.titleSmall
                        )
                        TextField(
                            value = state.phoneNumber,
                            onValueChange = {
                                viewModel.onIntent(
                                    EditInfoScreenContract.Intent.ChangePhoneNumber(
                                        it
                                    )
                                )
                            },
                            textStyle = MaterialTheme.typography.titleMedium,
                            colors = TextFieldDefaults.colors(
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(56.dp)
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.onIntent(EditInfoScreenContract.Intent.ClickSaveButton)
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
                            text = "Сохранить изменения",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun PhotoPicker(
    modifier: Modifier = Modifier,
    photoUrl: Any?,
    onSelected: (Uri) -> Unit
) {
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                onSelected(uri)
            }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        AsyncImage(
            model = photoUrl,
            contentDescription = "",
            modifier = Modifier
                .size(150.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            TextButton(
                onClick = {
                    photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Text(
                    text = "Изменить фото",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

    }
}