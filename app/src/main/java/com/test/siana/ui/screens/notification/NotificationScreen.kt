package com.test.siana.ui.screens.notification

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import com.test.siana.data.model.NotificationModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.LaunchedEffect
import com.test.siana.utils.formatRelativeTime

val PrimaryDark = Color(0xFF132635)

data class NotificationItem(
    val title: String,
    val description: String,
    val level: String,
    val timestamp: Long,
    var isCompleted: Boolean = false
)

@Composable
fun NotificationScreen(
    navController: NavController
) {

    val notifications = remember {

        mutableStateListOf<NotificationItem>()
    }

    val context = LocalContext.current
    val activity = context as Activity

    SideEffect {

        WindowInsetsControllerCompat(
            activity.window,
            activity.window.decorView
        ).isAppearanceLightStatusBars = true
    }


    LaunchedEffect(Unit) {

        val uid =
            FirebaseAuth.getInstance()
                .currentUser?.uid
                ?: return@LaunchedEffect

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .collection("notifications")
            .orderBy(
                "timestamp",
                Query.Direction.DESCENDING
            )
            .addSnapshotListener { value, error ->

                if (
                    error != null ||
                    value == null
                ) return@addSnapshotListener

                notifications.clear()

                value.documents.forEach {

                    val notif =
                        it.toObject(
                            NotificationModel::class.java
                        )

                    if (notif != null) {

                        notifications.add(
                            NotificationItem(
                                title = notif.title,
                                description = notif.description,
                                level = notif.level ,
                                timestamp = notif.timestamp
                            )
                        )
                    }
                }
            }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
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
                text = "Notifikasi",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            itemsIndexed(notifications) { index, item ->

                val backgroundColor =
                    when {

                        item.isCompleted ->
                            Color(0xFFE9ECEF)

                        item.level == "danger" ->
                            Color(0xFFFFE5E5)

                        item.level == "warning" ->
                            Color(0xFFFFF6CC)

                        else ->
                            Color(0xFFE9ECEF)
                    }

                val titleColor =
                    when {

                        item.isCompleted ->
                            Color(0xFF8E9AA6)

                        item.level == "danger" ->
                            Color(0xFFFF3B30)

                        item.level == "warning" ->
                            Color(0xFFE6A700)

                        else ->
                            Color(0xFF8E9AA6)
                    }

                val dotColor =
                    when {

                        item.isCompleted ->
                            Color(0xFFB0B7C3)

                        item.level == "danger" ->
                            Color(0xFFFF3B30)

                        item.level == "warning" ->
                            Color(0xFFE6A700)

                        else ->
                            Color(0xFFB0B7C3)
                    }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            notifications[index] =
                                notifications[index].copy(
                                    isCompleted = true
                                )
                        },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = backgroundColor
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 14.dp,
                                vertical = 16.dp
                            ),
                        verticalAlignment = Alignment.Top
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .size(8.dp)
                                .background(
                                    dotColor,
                                    RoundedCornerShape(100.dp)
                                )
                        )

                        Spacer(modifier = Modifier.size(14.dp))

                        Column {

                            Text(
                                text = item.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = titleColor
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = item.description,
                                fontSize = 13.sp,
                                color =
                                    if (item.isCompleted)
                                        Color(0xFF9AA4AF)
                                    else
                                        PrimaryDark
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = formatRelativeTime(
                                    item.timestamp
                                ),
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }

}
