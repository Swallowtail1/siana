package com.test.siana.ui.screens.dashboard

import androidx.compose.material.icons.filled.Logout
import androidx.navigation.NavController
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.test.siana.R
import com.test.siana.data.model.DisasterLevel
import com.test.siana.ui.components.SensorCard
import com.test.siana.ui.components.StatusCard

val PrimaryDark = Color(0xFF132635)

@Composable
fun DashboardScreen(
    navController: NavController
) {

    val context = LocalContext.current
    val activity = context as Activity

    var isProfileMenuOpen by remember {
        mutableStateOf(false)
    }

    SideEffect {

        WindowCompat.setDecorFitsSystemWindows(
            activity.window,
            false
        )

        activity.window.navigationBarColor =
            android.graphics.Color.WHITE

        WindowInsetsControllerCompat(
            activity.window,
            activity.window.decorView
        ).isAppearanceLightNavigationBars = true
    }

    var isCalibrationMode by remember {
        mutableStateOf(false)
    }

    val suhuThreshold = remember {
        mutableStateOf("10")
    }

    val udaraThreshold = remember {
        mutableStateOf("10")
    }

    val gempaThreshold = remember {
        mutableStateOf("10")
    }

    val level = DashboardState.currentLevel

    val background = when(level) {

        DisasterLevel.AMAN ->
            R.drawable.bg_dashboard_aman

        DisasterLevel.WASPADA ->
            R.drawable.bg_dashboard_waspada

        DisasterLevel.BAHAYA ->
            R.drawable.bg_dashboard_bahaya
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = background),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(25.dp))

            HeaderSection(
                navController = navController,
                isProfileMenuOpen = isProfileMenuOpen,
                onProfileMenuChange = {
                    isProfileMenuOpen = it
                }
            )

            Spacer(modifier = Modifier.height(45.dp))

            GreetingSection(level)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                SensorCard(
                    modifier = Modifier.weight(1f),
                    title = "Suhu",
                    value = "39°C",
                    description = buildAnnotatedString {

                        append("Bagus! Suhunya ")

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF4CC9F0),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("normal")
                        }
                    },
                    icon = Icons.Default.DeviceThermostat
                )

                SensorCard(
                    modifier = Modifier.weight(1f),
                    title = "Udara",
                    value = "87%",
                    description = buildAnnotatedString {

                        append("Udara cukup ")

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF4CC9F0),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("bersih")
                        }
                    },
                    icon = Icons.Default.Air
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = if (isCalibrationMode)
                                "Kalibrasi"
                            else
                                "Status",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryDark
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .border(
                                    width = 0.5.dp,
                                    color = PrimaryDark,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    isCalibrationMode = !isCalibrationMode
                                }
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 8.dp
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector =
                                    if (isCalibrationMode)
                                        Icons.AutoMirrored.Filled.ArrowBack
                                    else
                                        Icons.Default.Build,
                                contentDescription = null,
                                tint = PrimaryDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {

                        if (!isCalibrationMode) {

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(14.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                item {

                                    StatusCard(
                                        title = "Banjir",
                                        status = "Aman",
                                        description = "Tidak terdeteksi adanya banjir",
                                        icon = R.drawable.icon_banjir
                                    )
                                }

                                item {

                                    StatusCard(
                                        title = "Gempa",
                                        status = "Aman",
                                        description = "Tidak terdeteksi adanya gempa",
                                        icon = R.drawable.icon_gempa
                                    )
                                }

                                item {

                                    StatusCard(
                                        title = "Kebakaran",
                                        status = "Aman",
                                        description = "Tidak terdeteksi adanya kebakaran",
                                        icon = R.drawable.icon_kebakaran
                                    )
                                }
                            }

                        } else {

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(30.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                item {

                                    CalibrationCard(
                                        title = "Banjir",
                                        sensor = "Ultrasonik HC-SR04",
                                        value = suhuThreshold.value,
                                        onValueChange = {
                                            suhuThreshold.value = it
                                        },
                                        unit = "cm"
                                    )
                                }

                                item {

                                    CalibrationCard(
                                        title = "Gempa",
                                        sensor = "Gyro MPU-6050",
                                        value = udaraThreshold.value,
                                        onValueChange = {
                                            udaraThreshold.value = it
                                        },
                                        unit = "M"
                                    )
                                }

                                item {

                                    CalibrationCard(
                                        title = "Kebakaran",
                                        sensor = "Suhu DHT-22",
                                        value = gempaThreshold.value,
                                        onValueChange = {
                                            gempaThreshold.value = it
                                        },
                                        unit = "°C"
                                    )
                                }

                                item {

                                    CalibrationCard(
                                        title = "Kebakaran",
                                        sensor = "Asap MQ-135",
                                        value = gempaThreshold.value,
                                        onValueChange = {
                                            gempaThreshold.value = it
                                        },
                                        unit = "ppm"
                                    )
                                }

                                item {

                                    Button(
                                        onClick = {

                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(56.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF4CC9F0)
                                        )
                                    ) {

                                        Text(
                                            text = "Simpan",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun HeaderSection(
    navController: NavController,
    isProfileMenuOpen: Boolean,
    onProfileMenuChange: (Boolean) -> Unit
)
 {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Dashboard",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Box {

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Color.White.copy(alpha = 0.3f))
                        .clickable {

                            onProfileMenuChange(true)
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                DropdownMenu(
                    expanded = isProfileMenuOpen,
                    onDismissRequest = {
                        onProfileMenuChange(false)
                    },
                    shape = RoundedCornerShape(10.dp),
                    containerColor = Color.White,
                    shadowElevation = 4.dp,
                    tonalElevation = 0.dp
                )
                {

                    DropdownMenuItem(
                        text = {

                            Text(
                                text = "Profile",
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        leadingIcon = {

                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = PrimaryDark,
                            leadingIconColor = PrimaryDark
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 6.dp
                        ),
                        onClick = {

                            onProfileMenuChange(false)

                            navController.navigate("profile")

                        }


                    )

                    DropdownMenuItem(
                        text = {

                            Text(
                                text = "Logout",
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        leadingIcon = {

                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = null
                            )
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = PrimaryDark,
                            leadingIconColor = PrimaryDark
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 6.dp
                        ),
                        onClick = {

                            onProfileMenuChange(false)

                            navController.navigate("login") {

                                launchSingleTop = true

                                popUpTo("dashboard") {
                                    inclusive = true
                                }

                            }

                        }

                    )
                }

            }
        }
    }

}

@Composable
fun GreetingSection(level: DisasterLevel) {

    val text = when(level) {

        DisasterLevel.AMAN ->
            "Hai Gardiono! Semua sensor dalam kondisi aman"

        DisasterLevel.WASPADA ->
            "Terdeteksi adanya anomali sensor"

        DisasterLevel.BAHAYA ->
            "BAHAYA! Segera lakukan evakuasi"
    }

    Text(
        text = text,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

}

@Composable
fun CalibrationCard(
    title: String,
    sensor: String,
    value: String,
    onValueChange: (String) -> Unit,
    unit: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryDark
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = sensor,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.width(58.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                textStyle = TextStyle(
                    color = PrimaryDark,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = unit,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark
            )
        }
    }

}