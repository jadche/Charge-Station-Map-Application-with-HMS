# Charging Stations Locator App with HMS

This Android app demonstrates my proficiency in Android app development using Kotlin and showcases my ability to integrate APIs using Retrofit. The app helps users find nearby electric charging stations for their vehicles.

## Project Highlights

- Utilizes Kotlin programming language for Android app development.
- Demonstrates integration with external APIs using Retrofit.
- Displays charging station locations on a map using Huawei Map Kit.
- Provides information about charging stations including name, address, contact details, and more.
- Implements dynamic UI elements such as Google's CountryCodePicker.
- Uses clear comments to explain the purpose of different parts of the code.

## Activities
### LoginActivity

The `LoginActivity` handles user authentication using Huawei Account Kit. Users can log in with their Huawei ID, and successful authentication leads to access to the main features of the app. The activity showcases my skills in handling user authentication and session management.

### SearchActivity

The `SearchActivity` allows users to search for charging stations based on their location and preferences. Users can retrieve their current location, select a country code, enter a search distance, and initiate the search. The activity dynamically requests location permissions and utilizes Huawei's Fused Location Provider.

### MapActivity

The `MapActivity` displays charging station locations on a map using Huawei Map Kit. It utilizes Retrofit to fetch charging station data from the OpenChargeMap API. Each charging station is marked on the map with a custom icon, and users can click on markers to view detailed information.

### DetailActivity

The `DetailActivity` presents detailed information about a selected charging station. It extracts data passed through intents and dynamically populates UI elements with the charging station's name, address, location, contact details, and URL.

## Video Demonstration

As you can see in the video demonstration, the Search page toolbar has been successfully implemented and the menu layout is displayed as intended. However, please note that the sign out functionality is not demonstrated in the video. This feature has been implemented in the code, but was not shown in the demonstration for the sake of brevity. 

https://user-images.githubusercontent.com/50412448/212567823-ee8fc3d5-9598-4736-8366-56e55b07156b.mp4

 The video has been sped up to 1.25x due to performance issues with the Android emulator.
