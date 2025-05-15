# Kredily HRMS Mobile Platform Enhancement - Custom Attendance Flow
## Overview
This repository contains the implementation of a custom attendance feature for Kredily's HRMS mobile app, developed for the assignment. The solution addresses all key requirements including attendance marking with restrictions, offline support, and weekly summary views.

## Features Implemented
### 1. Attendance Marking System
- **Time-based validation:** Punch In allowed between 9:00 AM - 11:00 AM, Punch Out between 5:00 PM - 8:00 PM
- **Location validation:** Geofencing within 300m radius of Kredily office (WJ6R+XG Bengaluru)

### 2. Offline Support
- Local data persistence using Room Database
- Automatic sync when network becomes available using WorkManager

### 3. Weekly Summary
- Visual indicators for Present/Late/Absent statuses
- Daily working hours calculation
- Color-coded status display

## Tech Stack
- **Kotlin:** Primary programming language
- **Jetpack Compose:** Modern declarative UI toolkit
- **Room:** Local database persistence
- **WorkManager:** Background sync operations
- **Firebase:** Network operations
- **Hilt:** Dependency injection
- **Google Play Services:** Location and geofencing
