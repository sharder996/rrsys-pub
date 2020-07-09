# Restaurant Reservation System

The Github repository for this system can be found [here](https://github.com/sharder996/rrsys). Additionally, a development log  that will be updated regularly can be found on google docs and will be provided separately.

## Setup

This application is designed specifically for the Samsung Galaxy S7. As the S7 is not available by default in Android Studio, in order to create an S7 virtual device you need to get [this skin](https://developer.samsung.com/galaxy-emulator-skin/galaxy_s_series.html) from  the Samsung Developers page. Then put the extracted contents in the directory **Android Studio > plugins > android > lib > device-art-resources**. When creating a virtual device in Android Studio, select "New Hardware Profile" and fill in the fields with the following data:

- Screen Size: 5.1 inches
- Resolution: 1440 x 2550
- RAM: 4 GB
- Hardware Buttons: Yes
- Supported device states: Portrait and Landscape
- Cameras: Front and Back

Select a skin and then click the adjacent button to browse your local directory. Navigate to the directory where you placed the Galaxy S7 skin and click OK. Lastly, this application was creating using Marshmallow (Android 6.0, API level 23), so to avoid any compatibilty issues it is recommended that you use the same.

If you have trouble with running the application on the above device, the application should also work on a Google Nexus 7 running the same version of OS.

## Log File

A log file recording the activity of all group members can be found separately. The log file is updated periodically as each developer spends time on a task. The developer will record the amount of time they spent, rationale behind decisions, and 

## Package Details

### application

The application package contains two classes that handle starting up the application and loading the database. Currently, the database is as a stub database and any new information created through one run of the application will not persist to the next.

### business

The business package contains classes that provide accesses to the various "tables" that are stored in the database (one for customers, reservations, and tables). Additionally, the business class also provides the logic required to suggest reservations to a user based on date, time, and number of people.

### objects

The objects package contains all the custom objects that are used throughout the application.

### persistence

The persistence package currently consists of one class that implements the stub database.

### presentation

The presentation package contains all the class that handle displaying the GUI and interactions with the user.

### tests

The test package contains all of the test classes which test the implemented methods in each of the classes in the objects package and the business package. All of the tests can be run through the `AllTests.java` class.

## Major Features

### Creating a Reservation

Selecting `CREATE A RESERVATION` from the home screen of the application will start the flow which guides the user through creating a reservation. Next, select a date, an arrival time, a departure time, and specify the number of people that will be coming. Once all the fields have been populated, the `CHECK AVAILABILITY` button will be enabled. Upon pressing the button, a list of suggested reservations with times will be shown to the user in the form of a list. Tapping a reservation and pressing the `DONE` button will allow you to proceed further through the flow. Next, details regarding the reservation will be shown and the user will be asked to fill in some information about themselves. Fill in the required information and proceed to the next page. Lastly, a final screen will be shown to the user will all the required details concerning the reservation that they made.

### Reviewing a Reservation

Selecting `REVIEWING A RESERVATION` from the home screen of the application will guide the user to a screen where they can enter in either their reservation code or their name and date of their reservation. Pressing the `ENTER` key will search the database for the reservation and either return details about the reservation to the user or notify the user that no reservation was found.

### Updating a Reservation

Selecting `UPDATING A RESERVATION` from the home screen of the application will guide the user to a screen where they can eneter in either their reservation code or their name and date of their reservation. Pressing the `ENTER` key will search the database for the reservation and either allow the user to proceed to the next screen or notify the user that no reservation was found. If a reservation was found, then the user will be able to select a new set of dates and times for a reservation. Selecting a new reservation will remove the user's previous reservation and replace with the selected one. Finally, the user will be shown details concerning their new reservation.

## Overview of System Architecture

<pre>
/--------------\                /----------\                /-------------\             /----------------\
| presentation | <------------> | business | <------------> | persistance | <---------> | DataAccessStub |
\--------------/                \----------/                \-------------/             \----------------/
       |                             |                             |
       |                             |                             |
       |                            \ /                            |
       |                        /---------\                        |
       \----------------------> | objects | <----------------------/
                                \---------/
</pre>

## Team Members

Scott Harder  
Mckenzie Hayward  
Louis Manahan  
WooSik Moon  
Yan Wen
