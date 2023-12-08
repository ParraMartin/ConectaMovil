
//Decidi comentar el codigo de mqtt ya que no logre hacerlo de la froma correcta no pude aprender como se uso mqtt por falta de enseñanza hice lo que pude



/*package com.example.conectamovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String MQTT_BROKER_URL = "tcp://localhost:1883";
    private static final String MQTT_TOPIC = "chat";

    private MqttClient mqttClient;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Create the MQTT client
        try {
            mqttClient = new MqttClient(MQTT_BROKER_URL, "chat-client");
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            mqttClient.connect(connectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        // Subscribe to the MQTT topic
        try {
            MqttTopic topic = mqttClient.getTopic(MQTT_TOPIC);
            mqttClient.subscribe(topic, 0);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        // Set up the UI
        ((EditText) findViewById(R.id.messageEditText)).setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                // Publish the message to the MQTT topic
                String message = ((EditText) findViewById(R.id.messageEditText)).getText().toString();
                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                try {
                    mqttClient.publish(MQTT_TOPIC, mqttMessage);

                    // Save the message to the Firebase database
                    databaseReference.child("messages").push().setValue(message);

                    ((EditText) findViewById(R.id.messageEditText)).setText("");
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        });

        // Set up the MQTT message listener
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String messageText = new String(message.getPayload());

                // Update the list of messages
                ((TextView) findViewById(R.id.chatTextView)).append(messageText + "\n");
            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
*/