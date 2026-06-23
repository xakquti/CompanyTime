package com.example.companytime.presentation.screen.profile

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicatorDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.example.companytime.R
import com.example.companytime.presentation.screen.meeting.MeetingContract
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    navigateToEditScreen: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(ProfileContract.Intent.LoadProfile)
    }

    LaunchedEffect(Unit) {
        viewModel.actionFlow.collect { it ->
            when (it) {
                is ProfileContract.Action.Logout -> onLogout()
                is ProfileContract.Action.NavigateToEditScreen -> navigateToEditScreen()
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
        if(state.isLoading) {
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

        } else if(state.error != null){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {

                Box(
                    Modifier.fillMaxWidth().heightIn(56.dp).clip(RoundedCornerShape(20.dp))
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
        }else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            viewModel.onIntent(ProfileContract.Intent.ClickEditButton)
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "edit",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top=8.dp)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center

                    ) {
                        Log.d("MY", state.photoUrl.toString())
                        AsyncImage(
                            model = state.photoUrl,
                            contentDescription = "Avatar",
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
                    }
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
                            text = state.name, color = MaterialTheme.colorScheme.onSurface, style =
                                MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "ФИО", color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)
                        Text(
                            text = state.phoneNumber,
                            color = MaterialTheme.colorScheme.onSurface,
                            style =
                                MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "мобильный телефон",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)
                        Text(
                            text = state.email, color = MaterialTheme.colorScheme.onSurface, style =
                                MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "электронная почта",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)
                        Text(
                            text = state.departmentName,
                            color = MaterialTheme.colorScheme.onSurface,
                            style =
                                MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "департамент", color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }

                    Button(
                        onClick = {
                            viewModel.onIntent(ProfileContract.Intent.ClickLogoutButton)
                            onLogout
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            text = "Выйти",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }
        }
    }
}