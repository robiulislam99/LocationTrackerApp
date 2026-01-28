# 📍 Android Location Tracker

A simple Android application that demonstrates **background location tracking**, **local data persistence**, and **REST API integration** using modern Android development practices.

---

## 🖼️ UI Preview

<table>
  <tr>
    <td align="center">
      <img src=";ocationUI.jpeg" width="250"/>
      <br/>
      <b>Home Screen</b>
    </td>
    <td align="center">
      <img src="locationUI1.jpeg" width="250"/>
      <br/>
      <b>Location History</b>
    </td>
  </tr>
</table>

> 📌 Screenshots taken from a real Android device.

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
## 📂 Project Structure
```text

app/
├── src/
│   └── main/
│       ├── java/com/example/locationtracker/
│       │   ├── MainActivity.java        # UI controller
│       │   ├── LocationService.java     # Background service
│       │   ├── LocationEntity.java      # Database model
│       │   ├── LocationDao.java         # Database queries
│       │   ├── AppDatabase.java         # Room database
│       │   ├── LocationAdapter.java     # RecyclerView adapter
│       │   ├── ApiModels.java           # API models
│       │   └── BootReceiver.java        # Auto-start receiver
│       │
│       └── res/
│           ├── layout/
│           │   ├── activity_main.xml    # Main screen
│           │   └── item_location.xml    # List item
│           ├── drawable/                # Background shapes
│           └── values/
│               └── colors.xml           # Color palette

```
## 🚀 Getting Started

### Prerequisites
- Android Studio (2023.1+ recommended)
- JDK 11+
- Android SDK (API 24–34)
- Physical device or emulator 

---

### Installation
1. **Clone the repository:**

```bash
git clone https://github.com/robiulislam99/LocationTrackerApp.git
cd LocationTrackerApp
```
2. Open the project in Android Studio
3. Let Gradle sync and download dependencies
4. Connect a device or start an emulator
5. Run the app

---
## 🔐 Permissions Required
-ACCESS_FINE_LOCATION – precise GPS coordinates

-ACCESS_COARSE_LOCATION – approximate location

-ACCESS_BACKGROUND_LOCATION – track location when app is closed

-FOREGROUND_SERVICE – keep service running

-RECEIVE_BOOT_COMPLETED – restart service on reboot

⚠️ Background location must be allowed manually on some Android versions.

---

## 📍 Background Location Flow
-LocationService runs as a foreground service

-Fetches location every 5 minutes using FusedLocationProvider

-Saves data in Room database (latitude, longitude, timestamp)

-Displays stored locations in RecyclerView

-BootReceiver restarts service after device reboot

---

## 🌐 API Integration
-Fetches posts from JSONPlaceholder API

-Retrofit handles HTTP requests

-GSON parses JSON responses into model classes

-Network errors handled gracefully

---
## 👤 Author
Md Robiul Islam  
CSE Graduate | Android & Backend Developer  
GitHub: https://github.com/robiulislam99



