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
import androidx.compose.foundation.shape.CircleShape
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
import android.app.Activity
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.filled.AccountCircle
import android.widget.Toast
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults


val PrimaryDark = Color(0xFF132635)

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = context as Activity

    var username by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var key by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        val uid =
            FirebaseAuth.getInstance()
                .currentUser?.uid

        if(uid != null){

            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener {

                    username =
                        it.getString("username")
                            ?: ""

                    key =
                        it.getString("apiKey")
                            ?: ""

                    email =
                        FirebaseAuth.getInstance()
                            .currentUser
                            ?.email
                            ?: ""
                }
        }
    }

    SideEffect {

        WindowInsetsControllerCompat(
            activity.window,
            activity.window.decorView
        ).isAppearanceLightStatusBars = true

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F8FB))
            .navigationBarsPadding()
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = PrimaryDark
                )
            }

            Text(
                text = "Profile",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark
            )

        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Box(
                contentAlignment = Alignment.BottomEnd
            ) {


                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
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
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = {
                        Text("Username")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        // 1. Warna teks yang diketik
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,

                        // 2. Warna label ("Username")
                        focusedLabelColor = Color.Black,       // Hitam pekat saat aktif
                        unfocusedLabelColor = Color.Gray,      // Abu-abu saat tidak aktif agar tidak membingungkan

                        // 3. Warna background dalam ruangan text field
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,

                        // 4. Warna garis tepi (Border)
                        focusedIndicatorColor = Color(0xFF4CC9F0),   // Garis menjadi hitam pekat saat diklik
                        unfocusedIndicatorColor = Color.LightGray // Garis abu-abu muda saat biasa
                    )
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                    },
                    label = {
                        Text("Email")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        // 1. Warna teks yang diketik
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,

                        // 2. Warna label ("Username")
                        focusedLabelColor = Color.Black,       // Hitam pekat saat aktif
                        unfocusedLabelColor = Color.Gray,      // Abu-abu saat tidak aktif agar tidak membingungkan

                        // 3. Warna background dalam ruangan text field
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,

                        // 4. Warna garis tepi (Border)
                        focusedIndicatorColor = Color(0xFF4CC9F0),   // Garis menjadi hitam pekat saat diklik
                        unfocusedIndicatorColor = Color.LightGray // Garis abu-abu muda saat biasa
                    )
                )

                OutlinedTextField(
                    value = key,
                    onValueChange = {
                        key = it
                    },
                    label = {
                        Text("Device Key")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        // 1. Warna teks yang diketik
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,

                        // 2. Warna label ("Username")
                        focusedLabelColor = Color.Black,       // Hitam pekat saat aktif
                        unfocusedLabelColor = Color.Gray,      // Abu-abu saat tidak aktif agar tidak membingungkan

                        // 3. Warna background dalam ruangan text field
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,

                        // 4. Warna garis tepi (Border)
                        focusedIndicatorColor = Color(0xFF4CC9F0),   // Garis menjadi hitam pekat saat diklik
                        unfocusedIndicatorColor = Color.LightGray // Garis abu-abu muda saat biasa
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {

                        val uid =
                            FirebaseAuth.getInstance()
                                .currentUser?.uid

                        if (uid != null) {

                            FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(uid)
                                .update(
                                    mapOf(
                                        "username" to username,
                                        "apiKey" to key
                                    )
                                )
                                .addOnSuccessListener {

                                    Toast.makeText(
                                        context,
                                        "Data berhasil disimpan",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                                .addOnFailureListener {

                                    Toast.makeText(
                                        context,
                                        "Gagal menyimpan data",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                        }

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
                        text = "Simpan",
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
