# SIANA (Smart IoT Natural Disaster Alert)

SIANA (Smart IoT Natural Disaster Alert) adalah aplikasi mobile berbasis Android yang dirancang untuk memantau kondisi lingkungan secara real-time menggunakan teknologi Internet of Things (IoT). Aplikasi ini menerima data sensor dari perangkat IoT yang terhubung ke Firebase Realtime Database dan memberikan peringatan dini terhadap potensi bencana seperti banjir, gempa bumi, serta kondisi lingkungan berbahaya.

## Features

### Real-Time Monitoring

* Monitoring data sensor secara langsung menggunakan Firebase Realtime Database.
* Menampilkan informasi:

  * Ketinggian air
  * Kualitas udara (MQ135)
  * Suhu lingkungan
  * Getaran

### Disaster Detection

* Sistem klasifikasi kondisi:

  * Aman
  * Waspada
  * Bahaya
* Status diperbarui secara otomatis berdasarkan threshold yang dikonfigurasi.

### Smart Notifications

* Notifikasi otomatis ketika kondisi memasuki level Waspada.
* Notifikasi prioritas tinggi ketika kondisi memasuki level Bahaya.
* Alarm dan getaran perangkat pada kondisi Bahaya.
* Tombol "Matikan Alarm" langsung dari notifikasi.

### User Authentication

* Login dan registrasi menggunakan Firebase Authentication.
* Session login otomatis.

### Device Management

* Setiap pengguna dapat menghubungkan perangkat IoT menggunakan Device Key.
* Monitoring data berdasarkan perangkat yang dimiliki pengguna.

### Threshold Calibration

* Kalibrasi threshold sensor langsung dari aplikasi.
* Threshold tersimpan di Firebase dan dapat diperbarui secara real-time.
* Mendukung konfigurasi:

  * Water Level Threshold
  * MQ135 Threshold
  * Temperature Threshold
  * Vibration Threshold

### Notification History

* Riwayat seluruh notifikasi tersimpan pada akun pengguna.
* Menampilkan tingkat peringatan dan waktu kejadian.

## Technology Stack

### Mobile Application

* Kotlin
* Jetpack Compose
* Material 3

### Backend & Cloud

* Firebase Authentication
* Firebase Firestore
* Firebase Realtime Database
* Firebase Cloud Messaging (FCM)

### IoT Device

* ESP32
* DHT22
* HC-SR04
* MQ135
* Vibration Sensor

## Firebase Structure

### User Data

```text
users
└── uid
    ├── username
    ├── email
    ├── apiKey
    ├── fcmToken
    └── notifications
```

### Device Data

```text
devices
└── device_001
    ├── data
    │   ├── air_quality
    │   ├── mq135
    │   ├── temperature
    │   ├── vibration
    │   ├── water_level
    │   └── timestamp
    │
    └── threshold
        ├── mq135_max
        ├── temp_max
        ├── vibration_max
        └── water_max
```

## Project Architecture

```text
SIANA
├── Authentication
├── Dashboard
├── Monitoring Service
├── Notification System
├── Profile Management
├── Threshold Calibration
└── Firebase Integration
```

## Future Development

* Multi-device support
* Interactive disaster maps
* BMKG integration
* Emergency contact system
* Data analytics dashboard
* Historical sensor visualization
* Cloud Functions automation
* AI-based disaster prediction
