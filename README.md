# Baby Care

This is an Android application that provides tips for baby care.

## Features

* Baby development milestones
* Tips for baby care
* Note taking feature

## Build

To build the project, open it in Android Studio and click the "Run" button.
# Baby Care Tips Android App

## Overview

The Baby Care Tips app is a helpful mobile guide for parents, providing weekly and monthly advice on their baby's development. The app tailors content based on the baby's age, which is calculated from their date of birth. It features a user-friendly interface that displays developmental milestones and relevant tips in an easy-to-read, expandable list format.

## Features

-   **User & Baby Profile:** Users can create a profile including their baby's name, date of birth, and gender.
-   **Age-Specific Content:** The app automatically calculates the baby's age (in years, months, and days) and displays relevant developmental information.
-   **Weekly/Monthly Development Guide:**
    -   For newborns (0-2 months), it provides week-by-week guidance.
    -   For infants (2-12 months), it offers month-by-month tips.
-   **Dynamic UI:** The home screen greets the user with the baby's name and current age.
-   **Rich Content Display:** Developmental tips are presented in a `RecyclerView` with custom icons and colors for better readability and engagement.
-   **Data Persistence:** User and baby information is saved locally on the device for a seamless experience.

## Technical Details & Libraries

This project is a native Android application written in Java. It leverages several modern Android libraries to provide a robust and efficient user experience.

### Key Dependencies:

*   **AndroidX Libraries:**
    *   `appcompat`: For backward compatibility of newer features on older Android versions.
    *   `constraintlayout`: For building flexible and responsive layouts.
    *   `recyclerview`: To efficiently display large sets of data.
*   **ViewBinding:** Used to more easily write code that interacts with views, replacing `findViewById`.
*   **Lottie for Android:** (`com.airbnb.android:lottie`) For rendering beautiful After Effects animations natively. [1]
*   **SDP - a scalable DP unit:** (`com.intuit.sdp:sdp-android`) A library that provides a scalable size unit for UI elements across different screen sizes. [2]
*   **ImagePicker:** (`com.github.dhaval2404:imagepicker`) A powerful library for capturing images from the camera or picking them from the gallery. [3]
*   **CircleImageView:** (`de.hdodenhof:circleimageview`) A fast and customizable circular `ImageView` for Android. [4]
*   **ExpandableTextView:** (`io.github.glailton.expandabletextview`) A simple library to create `TextViews` with expand/collapse functionality. [5]

## How It Works

The `HomeFragment` is the core of the application. Here's a brief overview of its logic:

1.  **Data Retrieval:** It retrieves the `UserModel`, which contains the baby's details (name, DOB, gender), from the arguments passed to the fragment.
2.  **Age Calculation:** It uses a utility function (`Utils.getAge()`) to calculate the baby's precise age in years, months, and days.
3.  **Dynamic Content Loading:**
    *   Based on the calculated age, the `checkAge()` method determines which text file to load from the app's assets (e.g., `first_week.txt`, `third_month.txt`).
    *   The `getFile()` method reads the content from the specified text file. The file is structured to contain a main description followed by pairs of titles and content for the developmental milestones.
4.  **UI Population:**
    *   The baby's name and age are displayed at the top of the screen.
    *   The developmental milestones are parsed and added to a `RecyclerView` using a custom `DevelopmentAdapter`. Each item is dynamically assigned a color and an icon based on its title.

## How to Set Up and Run

1.  Clone the repository to your local machine.
2.  Open the project in Android Studio.
3.  Let Gradle sync and download all the required dependencies.
4.  Run the app on an Android emulator or a physical device.

---

*This README was generated based on the provided project structure and code.*
