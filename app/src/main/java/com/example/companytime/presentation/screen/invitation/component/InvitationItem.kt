package com.example.companytime.presentation.screen.invitation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.companytime.domain.model.Meeting
import com.example.companytime.domain.model.MeetingParticipant
import com.example.companytime.presentation.screen.component.getDate
import com.example.companytime.presentation.screen.component.getTime
import com.example.companytime.presentation.screen.invitation.InvitationContract

@Composable
fun InvitationItem(
    invitation: Meeting,
    onAgree: () -> Unit,
    onDecline: () -> Unit
) {
    Box(
        modifier = Modifier
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .heightIn(100.dp),
    ) {
        Column(
            Modifier.fillMaxWidth()
                .heightIn(100.dp)
        ) {
            Row(
                modifier = Modifier
            ) {
                Column(
                    modifier = Modifier.weight(1.5f).padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = getDate(invitation.date),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = getTime(invitation.startTime, ""),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "-",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = getTime(invitation.endTime, ""),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Column(
                    modifier = Modifier.weight(3f).padding(8.dp)
                ) {
                    Text(
                        text = invitation.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = invitation.description,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )

                }
            }

            HorizontalDivider(thickness = 2.dp,
                color = MaterialTheme.colorScheme.background
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth().padding( 12.dp)
            ) {
                Button(
                    onClick = {
                        onDecline
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(30.dp)
                    ).weight(1f).height(48.dp),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Отклонить",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Button(
                    onClick = onAgree,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Принять",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}