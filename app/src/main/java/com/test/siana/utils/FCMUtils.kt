package com.test.siana.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log
import com.google.firebase.firestore.SetOptions

object FCMUtils {

    fun saveFcmToken() {

        FirebaseMessaging.getInstance()
            .token
            .addOnSuccessListener { token ->

                val uid =
                    FirebaseAuth.getInstance()
                        .currentUser?.uid

                if (uid != null) {

                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(uid)
                        .set(
                            mapOf(
                                "fcmToken" to token
                            ),
                            SetOptions.merge()
                        )
                }
            }
    }
}