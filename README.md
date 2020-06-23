# Restaurant Reservation System

## Setup

This application is designed specifically for the Samsung Galaxy S7. As the S7 is not available by default in Android Studio, in order to create an S7 virtual device you need to get [this skin](https://developer.samsung.com/galaxy-emulator-skin/galaxy_s_series.html) from  the Samsung Developers page. Then put the extracted contents in the directory **Android Studio > plugins > android > lib > device-art-resources**. When creating a virtual device in Android Studio, select "New Hardware Profile" and fill in the fields with the following data:

- Screen Size: 5.1 inches
- Resolution: 1440 x 2550
- RAM: 4 GB
- Hardware Buttons: Yes
- Supported device states: Portrait and Landscape
- Cameras: Front and Back

Select a skin and then click the adjacent button to browse your local directory. Navigate to the directory where you placed the Galaxy S7 skin and click OK. Lastly, this application was creating using Marshmallow (Android 6.0, API level 23), so to avoid any compatibilty issues it is recommended that you use the same.
