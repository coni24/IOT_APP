package com.example.iot_app;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    private CardView ventiladorCard;
    private Switch switchVentilador;
    private TextView textVentilador, temperatureText;
    private ImageView iconVentilador;
    private Handler handler = new Handler();

    // Configuración de ThingSpeak
    private static final String THINGSPEAK_API_URL = "https://api.thingspeak.com/channels/YOUR_CHANNEL_ID/fields/1/last?api_key=YOUR_API_KEY";
    private boolean autoControlEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializar vistas
        ventiladorCard = findViewById(R.id.ventilador_card);
        switchVentilador = findViewById(R.id.switch_ventilador);
        textVentilador = findViewById(R.id.text_ventilador);
        temperatureText = findViewById(R.id.temperature_text);
        iconVentilador = findViewById(R.id.icon_ventilador);

        // Listener del switch para encender/apagar manualmente
        switchVentilador.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            autoControlEnabled = !isChecked; // Desactiva el control automático si el usuario ajusta el switch manualmente
            updateFanUI(isChecked);
        });

        // Actualizar temperatura periódicamente
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchTemperature();
                handler.postDelayed(this, 5000); // Actualizar cada 5 segundos
            }
        }, 0);
    }

    /**
     * Recupera la temperatura actual desde ThingSpeak.
     */
    private void fetchTemperature() {
        new Thread(() -> {
            try {
                URL url = new URL(THINGSPEAK_API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                reader.close();

                // Procesar la temperatura y actualizar la interfaz
                float temperature = Float.parseFloat(response);
                runOnUiThread(() -> {
                    temperatureText.setText("Temperatura: " + temperature + " °C");

                    if (autoControlEnabled) {
                        if (temperature > 20) {
                            switchVentilador.setChecked(true); // Encender ventilador automáticamente
                        } else {
                            switchVentilador.setChecked(false); // Apagar ventilador automáticamente
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Actualiza la interfaz del ventilador según su estado.
     *
     * @param isOn Verdadero si el ventilador está encendido, falso si está apagado.
     */
    private void updateFanUI(boolean isOn) {
        if (isOn) {
            // Estado encendido
            ventiladorCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black));
            textVentilador.setText("ENCENDIDO");
            textVentilador.setTextColor(ContextCompat.getColor(this, R.color.white));
            iconVentilador.setColorFilter(ContextCompat.getColor(this, R.color.white));
            switchVentilador.setThumbTintList(ContextCompat.getColorStateList(this, R.color.green2));
            switchVentilador.setTrackTintList(ContextCompat.getColorStateList(this, R.color.white));
        } else {
            // Estado apagado
            ventiladorCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
            textVentilador.setText("APAGADO");
            textVentilador.setTextColor(ContextCompat.getColor(this, R.color.black));
            iconVentilador.setColorFilter(ContextCompat.getColor(this, R.color.black));
            switchVentilador.setThumbTintList(ContextCompat.getColorStateList(this, R.color.white));
            switchVentilador.setTrackTintList(ContextCompat.getColorStateList(this, R.color.dark_gray));
        }
    }
}
