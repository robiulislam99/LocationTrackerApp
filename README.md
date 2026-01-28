# 📍 Android Location Tracker

A simple Android application that demonstrates **background location tracking**, **local data persistence**, and **REST API integration** using modern Android development practices.

---

## ✨ Features

- **Background Location Tracking**
  - Location updates every 5 minutes
  - Runs as a foreground service
  - Continues after app closure and device reboot
  - Displays updates via Toast messages

- **Local Database Storage**
  - Room (SQLite) database
  - Stores latitude, longitude, and timestamp
  - Displays location history using RecyclerView

- **API Integration**
  - Fetches data from JSONPlaceholder API
  - Uses Retrofit + GSON
  - Handles network errors gracefully

---

## 🛠 Tech Stack

- **Language:** Java  
- **UI:** XML (Material Design 3)  
- **Database:** Room (SQLite)  
- **Location Services:** Google Play Services  
- **Networking:** Retrofit + GSON  
- **Architecture:** MVVM-inspired  

---
## 🛠 Project Structure
app/src/main/java/com/example/locationtracker/
├── MainActivity.java          # UI controller
├── LocationService.java       # Background service
├── LocationEntity.java        # Database model
├── LocationDao.java          # Database queries
├── AppDatabase.java          # Room database
├── LocationAdapter.java      # RecyclerView adapter
├── ApiModels.java           # API models
└── BootReceiver.java        # Auto-start receiver

app/src/main/res/
├── layout/
│   ├── activity_main.xml    # Main screen
│   └── item_location.xml    # List item
├── drawable/                # Background shapes
└── values/
    └── colors.xml           # Color palette

## 🚀 Getting Started

### Prerequisites
- Android Studio (2023.1+ recommended)
- JDK 11+
- Android SDK (API 24–34)
- Physical device or emulator with Google Play Services

---

### Installation

```bash
git clone https://github.com/yourusername/android-location-tracker.git
cd android-location-tracker
