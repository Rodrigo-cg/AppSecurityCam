package com.example.securitycam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import android.os.Bundle;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    MqttAndroidClient client;
    String a="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";
    String host="tcp://broker.mqtt-dashboard.com:1883";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView simpleImageView=(ImageView) findViewById(R.id.cam);

        String clientId = MqttClient.generateClientId();
        client =
                new MqttAndroidClient(this.getApplicationContext(), host,
                        clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this,"Actualizando Contraseña Online si se requiere",Toast.LENGTH_SHORT).show();
                    sub();
                    // We are connected
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                a= new String(message.getPayload());
                byte[] decodedString = Base64.decode(a, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                simpleImageView.setImageBitmap(decodedByte);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });




    }






    private void sub(){
        try {
            client.subscribe("image_2021",1);
        }catch (MqttException e){
            Toast.makeText(getBaseContext(), "se ha conectado", Toast.LENGTH_SHORT).show();
        }


    }

}
