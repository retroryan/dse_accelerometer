# DSE Accelerometer

The project currently represents the following data pipeline:

android -> play -> akka -> kafka -> spark streaming -> cassandra

It is comprised of the following modules:

1. AccelerometerAndroidApp - Basic Android App the collects accelerometer data and sends it in JSON format to a rest end point.
2. SensorRestServer - Play Server that does the following
  1. Rest endpoint to receive the accelerometer data and parse the json
  2. After receiving the data it sends it to Akka for asynchronous processing.  
  3. Akka currently is just storing the data in Kafka using a Kafka Producer
3. SensorAnalyzer - Currently this is just a Java Main that starts a local Spark Context.  It does the following
  1. Start Spark Streaming using a Kafka Receiver
  2. Parses the input from Spark Streaming
  3. Saves the input into Cassandra
