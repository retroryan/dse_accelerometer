Credit to the Original Authors
=================================

The idea and original application comes from the following sources:

Amira LAKHAL -
[Predict your activity using your Android smartphone, Cassandra and Spark](http://www.duchess-france.org/accelerometer-time-series-and-prediction-with-android-cassandra-and-spark/)

Ludwine Probst -
[Analyze accelerometer data with Apache Spark and MLlib](http://www.duchess-france.org/analyze-accelerometer-data-with-apache-spark-and-mllib/)


# AccelerometerAndroidApp
A basic accelerometer app which sends data to a REST API based on Cassandra :
https://github.com/pvardanega/accelerometer-rest-to-cassandra

Each acceleration contains:

    date when it has been captured as a timestamp (eg, 1428773040488)
    acceleration force along the x axis (unit is m/s²)
    acceleration force along the y axis (unit is m/s²)
    acceleration force along the z axis (unit is m/s²)

# Prerequisites
Android Studio https://developer.android.com/sdk/index.html

# Start the application
You can install the app into you Android phone (4.0.3 version and above).
Before installing the app, you'll have to start the REST API.
You will need the REST API URL to post data.

# Building  a deployable app that can be run on your phone  / Building an APK

1 -  Modify editURL in activity_start.xml to match the IP of where the SensorRestServer is deployed.  The default it is deployed to EC2 that only had port 10000 open so the url is 52.8.36.105:10000
1 - In the android studio go to `Build -> Generate Singed APK`
1 -
