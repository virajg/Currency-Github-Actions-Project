This is a Kotlin Multiplatform project targeting Android, iOS.


### 🪙 Currency Conversion App (CMP)
CMP is a Currency Conversion App built using Compose Multiplatform.
It allows users to convert currencies, view live exchange rates, and store their preferences locally.

This project is designed with clean architecture and modern Android/KMP best practices.
![Preview](https://camo.githubusercontent.com/1f9a2b66e9d397f644be80ed27d3b939d0afc059898f4afb00dc289168ba9d34/68747470733a2f2f692e706f7374696d672e63632f717654716b6851792f436f6d706f73652d4d756c7469706c6174666f726d2d322e6a7067)

### 🛠️ Tech Stack
Languages & Frameworks

* [Kotlin]

- [Compose Multiplatform]
- [Coroutines]

* [Architecture]

- [MVVM]
- [Clean Architecture]
- [Repository Pattern]

* [Libraries Used]

- [Room Database] – Local caching
- [DataStore Preferences] – Persist settings
- [Koin] – Dependency Injection
- [Feather Icons] – Lightweight vector icons
- [Ktor Client] – API requests

### 📚 Architecture Overview

composeApp/
│
├── data/
│   ├── local/
│   │   ├── datastore/
│   │   └── database/
│   ├── remote/
│   ├── model/
│   ├── mapper/
│   └── repository/
│
├── domain/
│   ├── model/
│   ├── repository/
│   ├── usecase/
│   └── utils/
│
├── presentation/
│   ├── ui/
│   ├── components/
│   ├── viewmodel/
│   └── navigation/
│
└── di/
├── dataModule
├── domainModule
└── presentationModule