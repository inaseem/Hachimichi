# Hachimichi


## Client

- The client uses MVVM as architecture.
- Events are transported using EventBus(Pub/Sub)
- Most of the Sensors have been added

<img src="./images/main.jpeg" alt="main" width="200"/> &nbsp;&nbsp; <img src="./images/sensors.jpeg" alt="sensors" width="200"/>

### Technologies Used:
- Firebase Storage and Authentication(Dummy User) Client Library
- Glide
- Event Bus
- Android Sensor API's
- Google Services(Location for FusedLocation)
- JUnit (for Tests)

### UI
- Material Design

## Server
- Firebase (Storage and Auth)

**Create new project**
<img src="./images/create.png" alt="create"/>

**Select storage and create a new folder**
<img src="./images/createfolder.png" alt="Storage"/>

**Navigate to the folder and click upload**
<img src="./images/upload.png" alt="upload">

**Select Authentication and select Sign in method**
<img src="./images/method.png" alt="Auth"/>

**Create a dummy user**
<img src="./images/user.png" alt="User creation"/>

**Download the google-services.json file and add to project**
<img src="./images/download.png" alt="Download"/>

NOTE: The SHA fingerprint is optional (But have been included)

**Future Improvements**
- Storing images locally for minimzing network calls and save money(Which firebase charges)
- More tests can be written
- Event Bus use can be increased making things smooth. (Minimizes Hacky Solutions)

