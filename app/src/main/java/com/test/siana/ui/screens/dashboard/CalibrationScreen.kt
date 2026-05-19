package com.test.siana.ui.screens.calibration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.navigation.NavController
import com.test.siana.R
import com.test.siana.data.model.DisasterLevel
import com.test.siana.ui.components.SensorCard

@Composable
fun CalibrationScreen(
    navController: NavController
) {

    val level = DisasterLevel.AMAN

    val suhuThreshold = remember { mutableStateOf("40") }
    val udaraThreshold = remember { mutableStateOf("70") }
    val gempaThreshold = remember { mutableStateOf("8") }

    val background = R.drawable.bg_dashboard_aman

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
                .padding(horizontal = 22.dp)
        ) {

            Spacer(modifier = Modifier.height(25.dp))

            HeaderSection()

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
                    .weight(1f),
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
                            text = "Calibration",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .border(
                                    width = 0.5.dp,
                                    color = Color.Black,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable(enabled = true) {
                                    navController.popBackStack()

                                }

                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    {

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                            modifier = Modifier.fillMaxWidth()

                        ) {

                            item {

                                CalibrationCard(
                                    title = "Threshold Suhu",
                                    value = suhuThreshold.value,
                                    onValueChange = {
                                        suhuThreshold.value = it
                                    },
                                    unit = "°C"
                                )
                            }

                            item {

                                CalibrationCard(
                                    title = "Threshold Udara",
                                    value = udaraThreshold.value,
                                    onValueChange = {
                                        udaraThreshold.value = it
                                    },
                                    unit = "%"
                                )
                            }

                            item {

                                CalibrationCard(
                                    title = "Threshold Gempa",
                                    value = gempaThreshold.value,
                                    onValueChange = {
                                        gempaThreshold.value = it
                                    },
                                    unit = "SR"
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.White
                                        )
                                    )
                                )
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun CalibrationCard(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    unit: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 0.5.dp,
                color = Color(0xFF4CC9F0),
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                trailingIcon = {

                    Text(
                        text = unit,
                        color = Color(0xFF4CC9F0),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }

}
@Composable
fun HeaderSection() {


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

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(Color.White.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }


}

@Composable
fun GreetingSection(level: DisasterLevel) {

    val text = when(level) {
        DisasterLevel.AMAN -> "Hai Gardiono! Semua sensor dalam kondisi aman"
        DisasterLevel.WASPADA -> "Terdeteksi adanya anomali sensor"
        DisasterLevel.BAHAYA -> "BAHAYA! Segera lakukan evakuasi"
    }

    Text(
        text = text,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

}
