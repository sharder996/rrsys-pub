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

A log file recording the activity of all group members can be found separately. The log file is updated periodically as each developer spends time on a task. The developer will record the amount of time they spent, rationale behind decisions, and what they did.

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

### 1st Iteration

#### Creating a Reservation

Selecting `CREATE A RESERVATION` from the home screen of the application will start the flow which guides the user through creating a reservation. Next, select a date, an arrival time, a departure time, and specify the number of people that will be coming. Once all the fields have been populated, the `CHECK AVAILABILITY` button will be enabled. Upon pressing the button, a list of suggested reservations with times will be shown to the user in the form of a list. Tapping a reservation and pressing the `DONE` button will allow you to proceed further through the flow. Next, details regarding the reservation will be shown and the user will be asked to fill in some information about themselves. Fill in the required information and proceed to the next page. Lastly, a final screen will be shown to the user will all the required details concerning the reservation that they made.

#### Reviewing a Reservation

Selecting `REVIEWING A RESERVATION` from the home screen of the application will guide the user to a screen where they can enter in either their reservation code or their name and date of their reservation. Pressing the `ENTER` key will search the database for the reservation and either return details about the reservation to the user or notify the user that no reservation was found.

#### Updating a Reservation

Selecting `UPDATING A RESERVATION` from the home screen of the application will guide the user to a screen where they can eneter in either their reservation code or their name and date of their reservation. Pressing the `ENTER` key will search the database for the reservation and either allow the user to proceed to the next screen or notify the user that no reservation was found. If a reservation was found, then the user will be able to select a new set of dates and times for a reservation. Selecting a new reservation will remove the user's previous reservation and replace with the selected one. Finally, the user will be shown details concerning their new reservation.

### 2nd Iteration

#### Pre-ordering before a Reservation

As of the second iteration, the ability to preorder food before your reservation has now been added. Ordering can be done either through the `CREATE A RESERVATION` flow or through the `UPDATING A RESERVATION` flow. Ordering is restricted by the amount of time between the current time and the start time of the reservation. To add an order to a reservation, navigate to the end of the `CREATE A RESERVATION` flow and before returning to the home menu press the `PRE-ORDER` button. Alternatively, proceeding through the `UPDATING A RESERVATION` flow will allow an identical method of ordering food for a reservation.

#### Viewing the restaurant's menu

A menu of the food offered by the restaurant can be viewed from the home screen by selecting the `MENU` button. Menu items are broken down into categories and the user can expand or collapse the categories to view the items. Below each item, the price along with a brief description of the item is displayed.

#### Database stub replaced with HSQLDB

The database stub has now been replaced with an HSQLDB.

## Overview of System Architecture

<pre>
/--------------\                /----------\                /-------------\                /--------\
| presentation | <------------> | business | <------------> | persistance | <------------> | HSQLDB |
\--------------/                \----------/                \-------------/                \--------/
       |                             |                             |
       |                             |                             |                   /----------------\
       |                            \ /                            |                   | DataAccessStub |
       |                        /---------\                        |                   \----------------/
       \----------------------> | objects | <----------------------/
                                \---------/
</pre>

## Running Tests

Unit, integration, and acceptance tests can be found within the `comp3350.rrsys.tests` and `comp3350.rrsys` packages. Since the database that is used for this application is generated upon installation of the application on a device, if for some reason while running the integration tests the database is not restored to its original state, simply reinstall the application on the device thereby forcing a new database to be generated.

## Team Members

Scott Harder  
Mckenzie Hayward  
Louis Manahan  
WooSik Moon  
Yan Wen
