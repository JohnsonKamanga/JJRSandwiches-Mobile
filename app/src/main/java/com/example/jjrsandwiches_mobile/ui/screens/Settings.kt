package com.example.jjrsandwiches_mobile.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Settings(
    onSignOut: () -> Unit,
    onThemeChange: (String) -> Unit,
    currentTheme: String
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("English") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )

        Divider()

        // Theme Selector
        SettingSelectableItem(
            title = "Theme",
            description = "Choose your preferred theme",
            currentSelection = currentTheme,
            onSelect = onThemeChange,
            options = listOf("Light", "Dark", "System")
        )

        // Notification Toggle
        SettingToggleItem(
            title = "Notifications",
            description = "Receive alerts for important updates",
            checked = notificationsEnabled,
            onCheckedChange = { notificationsEnabled = it }
        )

        // Language Selector
        SettingSelectableItem(
            title = "Language",
            description = "Choose your preferred language",
            currentSelection = selectedLanguage,
            onSelect = { selectedLanguage = it },
            options = listOf("English", "French", "Spanish", "Chichewa")
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Sign out button
        Button(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Sign Out")
        }
    }
}

@Composable
fun SettingToggleItem(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingSelectableItem(
    title: String,
    description: String,
    currentSelection: String,
    onSelect: (String) -> Unit,
    options: List<String>
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        var tempSelection by remember { mutableStateOf(currentSelection) }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = title) },
            text = {
                Column {
                    options.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { tempSelection = option }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (option == tempSelection),
                                onClick = { tempSelection = option }
                            )
                            Spacer(Modifier.width(16.dp))
                            Text(text = option)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onSelect(tempSelection)
                        showDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = currentSelection,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
