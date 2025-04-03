package com.app.composedemoapp.ui.theme.Screen

import androidx.compose.foundation.clickable
import com.app.composedemoapp.R


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.composedemoapp.ui.theme.Purple60
import com.app.composedemoapp.ui.theme.gredintDial_1

@Preview(showBackground = true)
@Composable
fun BottomNavigationScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Contacts", "Dialer", "Shop", "Account")
    val icons = listOf(
        R.drawable.baseline_house_24,   // Replace with your own icons
        R.drawable.baseline_perm_phone_msg_24,
        R.drawable.inactive,
        R.drawable.baseline_shopping_cart_24,
        R.drawable.baseline_settings_24
    )

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEachIndexed { index, item ->
                        if (index == 2) {  // Center FAB for Dialer
                            FloatingActionButton(
                                onClick = { selectedItem = index },
                                containerColor = gredintDial_1,
                                shape = CircleShape,
                                 //modifier = Modifier.size(56.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_dialpad_24),
                                    contentDescription = item,
                                    tint = Color.White
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable(
                                        onClick = { selectedItem = index },
                                        indication = null, // Disables ripple effect
                                        interactionSource = remember { MutableInteractionSource() } // Prevents interactions
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = icons[index]),
                                    contentDescription = item,
                                    tint = if (selectedItem == index) Purple60 else Color.Gray
                                )
                                Text(
                                    text = item,
                                    color = if (selectedItem == index) Purple60 else Color.Gray,
                                    fontSize = 14.sp,
                                    style = LocalTextStyle.current
                                )
                            }

                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> DashboardScreen()
                1 -> ContactsScreen()
                2 -> DialpadScreen()
                3 -> ShopScreen()
                4 -> AccountScreen()
            }
        }
    }
}

// Placeholder Composables for different screens
@Composable fun ShopScreen() { Text("Shop Screen", modifier = Modifier.fillMaxSize()) }
@Composable fun AccountScreen() { Text("Account Screen", modifier = Modifier.fillMaxSize()) }

