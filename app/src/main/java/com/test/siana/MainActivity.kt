package com.test.siana

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



// --- Palet Warna Sesuai Desain ---
val BlueGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF0081D5), Color(0xFF00BCFF))
)
val OrangeGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFFFF9800), Color(0xFFE65100))
)

// Gradient Merah
val RedGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFFFF5252), Color(0xFFD32F2F))
)
val BackgroundLight = Color(0xFFF5F7FA)
val StatusAmanColor = Color(0xFF4FC3F7)
val TextDark = Color(0xFF2D3436)
val TextLight = Color(0xFF636E72)

var suhu = 39

val PrimaryGradient =
    if (suhu > 42) {RedGradient}
else if (suhu > 38) {OrangeGradient}
else {BlueGradient}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                IoTDashboardScreen()
            }
        }
    }
}

@Composable
fun IoTDashboardScreen() {


    Scaffold(

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight)
                .padding(innerPadding)

        ) {
            // Latar Belakang Biru di Atas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(PrimaryGradient)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                HeaderSection()
                Spacer(modifier = Modifier.height(24.dp))
                GreetingSection()
                Spacer(modifier = Modifier.height(24.dp))
                TopSensorCards()
                Spacer(modifier = Modifier.height(32.dp))
                StatusListSection()
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
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Placeholder Foto Profil
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.3f))
                    .border(2.dp, Color.White, CircleShape)
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun GreetingSection() {
    Text(
        text = "Hai Gardiono! Semuanya aman terkendali.",
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        lineHeight = 28.sp,
        modifier = Modifier.width(250.dp)
    )
}

@Composable
fun TopSensorCards() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SensorSmallCard(
            modifier = Modifier.weight(1f),
            label = "Suhu",
            value = "${suhu}°C",
            icon = Icons.Default.DeviceThermostat,
            description = "Bagus! Suhunya ",
            status = "normal"
        )
        SensorSmallCard(
            modifier = Modifier.weight(1f),
            label = "Udara",
            value = "90%",
            icon = Icons.Default.Air,
            description = "Udaranya ",
            status = "bersih"
        )
    }
}

@Composable
fun SensorSmallCard(
    modifier: Modifier,
    label: String,
    value: String,
    icon: ImageVector,
    description: String,
    status: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = TextLight
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = label, fontSize = 14.sp, color = TextDark, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = buildAnnotatedString {
                    append(description)
                    withStyle(style = SpanStyle(color = StatusAmanColor, fontWeight = FontWeight.Bold)) {
                        append(status)
                    }
                },
                fontSize = 12.sp,
                color = TextLight
            )
        }
    }
}

@Composable
fun StatusListSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Text(text = "Status", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(36.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            ) {
                Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        StatusLargeCard("Banjir", "Aman", "Tidak terdeteksi adanya kemungkinan banjir", Icons.Default.Waves)
        Spacer(modifier = Modifier.height(16.dp))
        StatusLargeCard("Gempa", "Aman", "Tidak terdeteksi adanya kemungkinan gempa", Icons.Default.GraphicEq)
        Spacer(modifier = Modifier.height(16.dp))
        StatusLargeCard("Kebakaran", "Aman", "Tidak terdeteksi adanya kemungkinan kebakaran", Icons.Default.LocalFireDepartment)
    }
}

@Composable
fun StatusLargeCard(title: String, status: String, desc: String, icon: ImageVector) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, StatusAmanColor.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(StatusAmanColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = StatusAmanColor, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = status, color = StatusAmanColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = desc, color = TextLight, fontSize = 13.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIoTDashboard() {
    MaterialTheme {
        IoTDashboardScreen()
    }
}
