package com.test.siana.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

val PrimaryDark = Color(0xFF132635)

@Composable
fun ProfileScreen(
    navController: NavController
) {

    val name = remember {
        mutableStateOf("Gardiono")
    }

    val email = remember {
        mutableStateOf("gardiono@gmail.com")
    }

    val password = remember {
        mutableStateOf("12345678")
    }

    val key = remember {
        mutableStateOf("SIANA-DEVICE-001")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F8FB))
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = PrimaryDark
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Profile",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryDark
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Box(
                contentAlignment = Alignment.BottomEnd
            ) {

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CC9F0)),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                OutlinedTextField(
                    value = name.value,
                    onValueChange = {
                        name.value = it
                    },
                    label = {
                        Text("Nama")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    label = {
                        Text("Email")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                    },
                    label = {
                        Text("Password")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = key.value,
                    onValueChange = {
                        key.value = it
                    },
                    label = {
                        Text("Device Key")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CC9F0)
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                ) {

                    Text(
                        text = "Save Changes",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

}
