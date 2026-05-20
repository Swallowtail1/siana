package com.test.siana.ui.screens.auth

import android.app.Activity
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.test.siana.R

val LoginBlue = Color(0xFF156FCB)
val LoginDark = Color(0xFF132635)

enum class AuthMode {
    LOGIN,
    REGISTER,
    REGISTER_KEY
}

@Composable
fun AuthScreen(
    navController: NavController
) {

    var authMode by remember {
        mutableStateOf(AuthMode.LOGIN)
    }

    var username by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var key by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val window = (context as Activity).window

    SideEffect {

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.setNavigationBarContrastEnforced(false)

        window.navigationBarColor =
            android.graphics.Color.TRANSPARENT

        androidx.core.view.WindowInsetsControllerCompat(
            window,
            window.decorView
        ).isAppearanceLightNavigationBars = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Image(
            painter = painterResource(id = R.drawable.bg_login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.width(130.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 34.dp)
            ) {

                Text(
                    text = when(authMode) {

                        AuthMode.LOGIN ->
                            "Selamat Datang!"

                        AuthMode.REGISTER,
                        AuthMode.REGISTER_KEY ->
                            "Halo, Pengguna Baru"
                    },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = LoginDark
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = when(authMode) {

                        AuthMode.LOGIN ->
                            "Login kembali ke akun mu untuk memulai!"

                        AuthMode.REGISTER,
                        AuthMode.REGISTER_KEY ->
                            "Buat akun untuk memulai!"
                    },
                    fontSize = 13.sp,
                    color = Color(0xFF8A8A8A)
                )

                Spacer(modifier = Modifier.height(40.dp))

                when(authMode) {

                    AuthMode.LOGIN -> {

                        AuthTextField(
                            label = "Username",
                            value = username,
                            onValueChange = {
                                username = it
                            },
                            placeholder = "Masukan username"
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        AuthPasswordField(
                            label = "Password",
                            value = password,
                            onValueChange = {
                                password = it
                            },
                            placeholder = "Masukan password"
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Lupa Password?",
                            color = LoginBlue,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable {

                            }
                        )
                    }

                    AuthMode.REGISTER -> {

                        AuthTextField(
                            label = "Email",
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            placeholder = "Masukan email"
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        AuthPasswordField(
                            label = "Password",
                            value = password,
                            onValueChange = {
                                password = it
                            },
                            placeholder = "Masukan password"
                        )
                    }

                    AuthMode.REGISTER_KEY -> {

                        AuthTextField(
                            label = "Username",
                            value = username,
                            onValueChange = {
                                username = it
                            },
                            placeholder = "Masukan username"
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        AuthPasswordField(
                            label = "Key",
                            value = key,
                            onValueChange = {
                                key = it
                            },
                            placeholder = "Masukan key"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                Button(
                    onClick = {

                        when(authMode) {

                            AuthMode.LOGIN -> {

                                navController.navigate("dashboard") {

                                    popUpTo("login") {
                                        inclusive = true
                                    }
                                }
                            }

                            AuthMode.REGISTER -> {
                                authMode = AuthMode.REGISTER_KEY
                            }

                            AuthMode.REGISTER_KEY -> {

                                navController.navigate("dashboard") {

                                    popUpTo("login") {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LoginBlue
                    )
                ) {

                    Text(
                        text = when(authMode) {

                            AuthMode.LOGIN ->
                                "Login"

                            AuthMode.REGISTER ->
                                "Lanjut"

                            AuthMode.REGISTER_KEY ->
                                "Daftar"
                        },
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = when(authMode) {

                            AuthMode.LOGIN ->
                                "Tidak punya akun? "

                            AuthMode.REGISTER,
                            AuthMode.REGISTER_KEY ->
                                "Sudah punya akun? "
                        },
                        color = Color(0xFF666666),
                        fontSize = 13.sp
                    )

                    Text(
                        text = when(authMode) {

                            AuthMode.LOGIN ->
                                "Daftar"

                            AuthMode.REGISTER,
                            AuthMode.REGISTER_KEY ->
                                "Login"
                        },
                        color = LoginBlue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {

                            authMode =
                                if (authMode == AuthMode.LOGIN)
                                    AuthMode.REGISTER
                                else
                                    AuthMode.LOGIN
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun AuthTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {

    Text(
        text = label,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = LoginDark
    )

    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        placeholder = {

            Text(
                text = placeholder,
                color = Color(0xFFA0A0A0)
            )
        },
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color(0xFFDADADA),
            unfocusedBorderColor = Color(0xFFDADADA)
        ),
        textStyle = TextStyle(
            color = LoginDark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    )

}

@Composable
fun AuthPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {

    Text(
        text = label,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = LoginDark
    )

    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        placeholder = {

            Text(
                text = placeholder,
                color = Color(0xFFA0A0A0)
            )
        },
        trailingIcon = {

            Icon(
                imageVector = Icons.Default.Visibility,
                contentDescription = null,
                tint = Color(0xFFA0A0A0)
            )
        },
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color(0xFFDADADA),
            unfocusedBorderColor = Color(0xFFDADADA)
        ),
        textStyle = TextStyle(
            color = LoginDark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    )

}