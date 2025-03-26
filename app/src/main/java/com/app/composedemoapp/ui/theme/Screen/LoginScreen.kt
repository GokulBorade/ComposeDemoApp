package com.app.smartchat2

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.composedemoapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen(navController: NavController = rememberNavController()) {
    // Fetch FCM Token
    var topic ="a"


    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(), color = Color.White

    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 10.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Login Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 5.dp),
            )
            Text(
                text = "Registration",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                color = Color.Blue,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )

            Text(
                text = "Please confirm your country code and enter your phone number",
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            )
            var text by remember { mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = { text = it },
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(40.dp))
                    .padding(horizontal = 10.dp),
                placeholder = { Text(text = "Enter your number") },
                textStyle = TextStyle(fontSize = 16.sp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),

                )

            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            Button(
                onClick = {

//                        val data = User("ghj","fghj")
//                        val userJson = Json.encodeToString(data)
//                      //  navController.navigate("HomeScreen/$text")
                        navController.navigate("DashboardScreen")

                },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonColors(Color.Blue, Color.White, Color.Gray, Color.White)
            ) {
                Text(
                    text = "Confirm",
                    modifier = Modifier.padding(vertical = 1.dp),

                    )
            }
            Text(
                text = "By creating account, you agree the term and condition and privacy policy",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                color = Color.Black,
                textAlign = TextAlign.Left,
                fontSize = 14.sp,
            )
        }
    }
}
//@kotlinx.serialization.Serializable
//data class User(val name:String,val number : String) : Serializable
