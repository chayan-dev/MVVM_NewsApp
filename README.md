# News Peer
A news app with MVVM architecture pattern

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)
![Minimum API Level](https://img.shields.io/badge/Min%20API%20Level-23-green)
![Maximum API Level](https://img.shields.io/badge/Max%20API%20Level-31-orange)

## Features:

- Swipe down through a feed of breaking news along with pagination functionality.
- Click on your desired news article and read the whole article -> implemented through WebView.
- Save the article for later (using RoomDB).
- Under save news tab, you can read saved news and also delete them by swiping left/right. UNDO option pops up, to prevent accidental swipes.
- Under search news tab, search for any news using keywords/sentences.

<h1 align="center">-: 📷SCREEN SHOTS📷 :-</h1>

<p align="center">
  <img src=https://github.com/chayan-dev/MVVM_NewsApp/blob/master/assets/inCollage_20220305_174542851.jpg  />
</p>

<p align="center">
  <img src=https://github.com/chayan-dev/MVVM_NewsApp/blob/master/assets/inCollage_20220305_174720696.jpg  />
</p>

## Tech used 👨‍💻 :

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development. Our app is totally written in kotlin.
- [Different Layouts](https://developer.android.com/guide/topics/ui/declaring-layout) -  In this app we have used difrenet layouts to make the app UI responsive. The used layouts are LinearLayout, ConstraintLayout and FrameLayout .
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more. Speacially used at the time of networking calls and using database .
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. Highly used shared viewmodel in the app.
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding?authuser=2) - The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically. Used two data binding in each fragments.
  - [Navigation](https://developer.android.com/guide/navigation#:~:text=Navigation%20refers%20to%20the%20interactions,bars%20and%20the%20navigation%20drawer.) - Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app. In our app we followed single app architecture using navigation and also implemented an unique nav nav drawer.
  - [Room](https://developer.android.com/jetpack/androidx/releases/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite. To store notes.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. 
- [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and Java.
- [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview?authuser=2) - RecyclerView makes it easy to efficiently display large sets of data. To show large lists.
- [MVVM](https://developer.android.com/jetpack/guide) - MVVM architecture is a Model-View-ViewModel architecture that removes the tight coupling between each component. In the note taking section we used MVVM.
- [WebView](https://developer.android.com/guide/webapps/webview) -The WebView class is an extension of Android's View class that allows you to display web pages as a part of your activity layout.
 
 ## API used:
 
 ### [NewsAPI](https://newsapi.org/) - Locate articles and breaking news headlines from news sources and blogs across the web with a JSON API
