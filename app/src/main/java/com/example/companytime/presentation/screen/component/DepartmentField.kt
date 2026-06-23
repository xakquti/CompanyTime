package com.example.companytime.presentation.screen.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.companytime.R
import com.example.companytime.domain.model.Department

@Composable
fun DepartmentField(
    value: String,
    onValueChange: (String) -> Unit,
    departments: List<String>,
    label: String,
    hint: String,
    error: String? = null,
    keyboardOptions: KeyboardOptions
){

    var isDropDownVisible by remember { mutableStateOf(false) }

    val filteredDepartments = remember(value, departments) {
        if(value.isBlank()) {
            departments.sorted()
        } else {
            departments.filter {
                it.lowercase().contains(value.lowercase())
            }.sorted()
        }
    }

    Box(modifier = Modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    modifier = Modifier.fillMaxWidth().heightIn(56.dp),
                    value = value,
                    onValueChange = {
                        onValueChange(it)
                        isDropDownVisible = true
                    },
                    placeholder = {
                        Text(
                            text = hint,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    keyboardOptions = keyboardOptions,
                    trailingIcon = {
                        IconButton(onClick = {
                            isDropDownVisible = !isDropDownVisible
                        },
                            modifier = Modifier.size(48.dp)) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.arrow_down),
                                contentDescription = "arrow",
                                tint = if(error != null)MaterialTheme.colorScheme.error
                                else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    shape = RoundedCornerShape(30.dp)
                )
            }

            AnimatedVisibility(visible = isDropDownVisible && filteredDepartments.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 150.dp)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        items(filteredDepartments) { item ->
                            DepartmentItem(item
                            ,
                                onSelect = { it ->
                                    onValueChange(it)
                                    isDropDownVisible = false
                                }
                            )
                        }
                    }
                }
            }

            if (error != null){
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
            }
        }

    }
}


@Composable
fun DepartmentItem(
    title: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable{
                onSelect(title)
            }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = title)
    }
}