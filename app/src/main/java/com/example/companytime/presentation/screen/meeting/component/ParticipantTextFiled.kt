package com.example.companytime.presentation.screen.meeting.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toLong
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.example.companytime.R
import com.example.companytime.domain.model.Person
import kotlin.text.lowercase

@Composable
fun ParticipantTextFiled(
    value: String,
    onValueChange: (String) -> Unit,
    persons: LazyPagingItems<Person>,
    selectedIds: Set<Long>,
    label: String,
    hint: String,
    cache: Map<Long, Person>,
    onSelectPerson: (Person) -> Unit,
    modifier: Modifier
) {
    var isDropDownVisible by remember { mutableStateOf(false) }

    val filteredUsers = remember(value, persons.itemCount) {
        val allLoadedPersons = persons.itemSnapshotList.items
        if (value.isBlank()) {
            allLoadedPersons.sortedBy {
                it.name
            }
        } else {
            allLoadedPersons.filter {
                it.username.lowercase().contains(value.lowercase())
            }.sortedBy {
                it.name
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .heightIn(56.dp)
                        .weight(5f),
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = {
                        Text(
                            text = hint,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    trailingIcon = {
                        IconButton(
                            onClick = { isDropDownVisible = !isDropDownVisible },
                            modifier = Modifier
                                .size(48.dp)
                                .weight(1f)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.arrow_down),
                                contentDescription = "arrow",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge,
                )
            }

            if (selectedIds.isNotEmpty()) {
                LazyRow {
                    items(selectedIds.toList(), key = { it }) { personId ->
                        val person = cache[personId]
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            AsyncImage(
                                model = person!!.photoUrl,
                                contentDescription = "url",
                                modifier = Modifier
                                    .size(40.dp)
                                    .shadow(
                                        elevation = 2.dp,
                                        shape = CircleShape
                                    )
                                    .clip(CircleShape),
                                fallback = painterResource(R.drawable.ellipse),
                                contentScale = ContentScale.Crop,
                            )

                            Text(
                                text = person.username,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visible = isDropDownVisible && filteredUsers.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 150.dp)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        items(
                            filteredUsers
                        ) { user ->
                            val isSelected = selectedIds.contains(user.id)
                            ParticipantItem(
                                imageUrl = user.photoUrl,
                                username = user.username,
                                onChecked = isSelected,
                                onCheckedChange = { onSelectPerson(user) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ParticipantItem(
    imageUrl: String?,
    username: String,
    onChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onCheckedChange(!onChecked)
            }
            .padding(4.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "url",
            modifier = Modifier
                .size(40.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape
                )
                .clip(CircleShape),
            fallback = painterResource(R.drawable.ellipse),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(2.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                username, color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .weight(4f)
                    .padding(start = 4.dp)
            )
            Checkbox(
                checked = onChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.weight(1f)
            )
        }
    }
}