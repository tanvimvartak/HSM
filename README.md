# HSM — Housing Society Management

![Java](https://img.shields.io/badge/Java-11-orange?logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-SDK%2021%2B-3DDC84?logo=android&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-Auth%20%7C%20Realtime%20DB%20%7C%20Storage-FFCA28?logo=firebase&logoColor=black)

An Android app that moves the day-to-day running of a residential housing society onto residents' phones. It replaces paper notice boards, handwritten maintenance bills, and complaint registers. Residents and society admins log in to separate dashboards, with data synced in real time through Firebase.

## Features

### For residents
- **Account registration.** Sign up with name, email, wing, and flat number. Login is handled by Firebase Authentication, and the session persists between launches.
- **Maintenance bill.** See your monthly or quarterly maintenance amount, sinking fund, and parking charges.
- **Notice board.** Read society notices and tap an attached image to view it full screen.
- **Complaints.** Raise a complaint and follow its status from `pending` to `solved`.
- **Resident directory.** Browse residents by name, wing, and flat number.

### For admins
- **Role-based access.** After login, each account is routed to the resident or admin dashboard based on its role.
- **Maintenance management.** Add, edit, or remove a resident's maintenance record, looked up by their registered email.
- **Notice publishing.** Post notices with a title, description, and image uploaded to Firebase Storage. Delete notices when they're no longer needed.
- **Complaint handling.** See all pending complaints with the resident's name, flat, and phone number, and mark each one as solved.
- **Resident management.** View every registered resident and remove accounts.

## Tech stack

| Layer | Technology |
|---|---|
| Language | Java 11 |
| Platform | Android (min SDK 21, target SDK 32) |
| UI | AndroidX AppCompat, Material Components, ConstraintLayout |
| Authentication | Firebase Authentication (email/password) |
| Database | Firebase Realtime Database |
| File storage | Firebase Cloud Storage (notice images) |
| Image loading | Glide |
| Build | Gradle |

## Database structure

```
users/{uid}                    Name, Email, Wing, Flat No, type ("user" | "admin")
uid_email_mapping/{email}      uid, used by admins to find a resident by email
Maintenance/{uid}              name, email, wing, flat_no, maintenance, sinkingfund, charges, mq
notice/{timestamp}             title, desc, img (Storage download URL)
queries/{uid}/{timestamp}      name, phone, flat, complaint, status ("pending" | "solved")
```

Notice images are stored in Firebase Storage under `images/`.

## Project structure

```
app/src/main/java/com/example/hsm/
├── MainActivity.java              # Login and role-based routing
├── SignUp.java                    # Resident registration
├── HomeScreen.java                # Resident dashboard
├── AdminHomeScreen.java           # Admin dashboard
├── MaintenanceUser/Admin.java     # View / manage maintenance bills
├── NoticeBoardUser/Admin.java     # View / publish notices
├── ComplaintUser/Admin.java       # Raise / resolve complaints
├── Residents/ResidentsAdmin.java  # Resident directory
├── ViewImage.java                 # Full-screen notice image
├── CustomAdapter*.java            # List adapters for each screen
└── Notice, Query, MaintenanceQuery, ResidentQuery   # Data models
```

## Getting started

### Prerequisites
- Android Studio
- A Firebase project

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/tanvimvartak/HSM.git
   ```
2. Open the project in Android Studio.
3. In the [Firebase console](https://console.firebase.google.com/), add an Android app with the package name `com.example.hsm`. Then enable:
   - Authentication → Email/Password
   - Realtime Database
   - Storage
4. Download your `google-services.json` and put it in `app/`, replacing the existing file.
5. Sync Gradle and run the app on an emulator or a device.

### Creating an admin account
Register as usual through the app. Then, in the Realtime Database, change that user's `type` field under `users/{uid}` from `user` to `admin`. The next login opens the admin dashboard.

## Author

**Tanvi Vartak** · [GitHub](https://github.com/tanvimvartak)
