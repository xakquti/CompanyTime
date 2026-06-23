package com.example.companytime.presentation.screen.invitation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.companytime.R
import com.example.companytime.presentation.screen.invitation.component.InvitationItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun InvitationScreen(
    viewModel: InvitationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

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
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).padding(top = 24.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.panda_inv),
                modifier = Modifier.size(140.dp).align(Alignment.End),
                contentDescription = "invitation",
            )

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Приглашения",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.invitations) { invitation ->
                        InvitationItem(
                            invitation,
                            onAgree = {
                                viewModel.onIntent(
                                    InvitationContract.Intent.ClickAcceptButton(
                                        invitation.id
                                    )
                                )
                            },
                            onDecline = {
                                viewModel.onIntent(
                                    InvitationContract.Intent.ClickDeclineButton(
                                        invitation.id
                                    )
                                )
                            }
                        )
                    }
                }
            }

            if (state.error != null) {
                Text(
                    state.error!!
                )
            }
        }
    }
}