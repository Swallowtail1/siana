package com.test.siana.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.siana.R
import com.test.siana.data.model.DisasterLevel
import com.test.siana.ui.components.SensorCard
import com.test.siana.ui.components.StatusCard
import androidx.compose.foundation.border
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


@Composable
fun DashboardScreen() {

    val level = DashboardState.currentLevel

    val background = when(level) {
        DisasterLevel.AMAN -> R.drawable.bg_dashboard_aman
        DisasterLevel.WASPADA -> R.drawable.bg_dashboard_waspada
        DisasterLevel.BAHAYA -> R.drawable.bg_dashboard_bahaya
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(15.dp))

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
                modifier = Modifier.fillMaxWidth(),
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
                            text = "Status",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFF4CC9F0))
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    StatusCard(
                        title = "Banjir",
                        status = "Aman",
                        description = "Tidak terdeteksi adanya banjir",
                        icon = R.drawable.icon_banjir
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    StatusCard(
                        title = "Gempa",
                        status = "Aman",
                        description = "Tidak terdeteksi adanya gempa",
                        icon = R.drawable.icon_gempa
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    StatusCard(
                        title = "Kebakaran",
                        status = "Aman",
                        description = "Tidak terdeteksi adanya kebakaran",
                        icon = R.drawable.icon_kebakaran
                    )

                }

            }

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