package com.example.iot_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Obtener las vistas
        CardView cardTemperatura = findViewById(R.id.card_temperatura);
        CardView cardVentilador = findViewById(R.id.card_ventilador);

        Switch switchTemperatura = findViewById(R.id.switch_ventilado);
        Switch switchVentilador = findViewById(R.id.switch_ventilador);


        TextView textTemperatura = findViewById(R.id.text_temperatura);
        ImageView iconTemperatura = findViewById(R.id.icon_temperatura);

        TextView textVentilador = findViewById(R.id.text_ventilador);
        ImageView iconVentilador = findViewById(R.id.icon_ventilador);

        // Listener para el switch de temperatura
        switchTemperatura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Cambiar fondo a negro y texto e icono a blanco
                    cardTemperatura.setCardBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                    textTemperatura.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.white));
                    iconTemperatura.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.white));
                } else {
                    // Cambiar fondo a blanco y texto e icono a negro
                    cardTemperatura.setCardBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.white));
                    textTemperatura.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                    iconTemperatura.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.black));
                }
            }
        });

        // Listener para el switch de ventilador
        switchVentilador.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardVentilador.setCardBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                    textVentilador.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.white));
                    iconVentilador.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.white));
                } else {
                    cardVentilador.setCardBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.white));
                    textVentilador.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.black));
                    iconVentilador.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.black));
                }
            }
        });

        // Enlazar la tarjeta de temperatura con la actividad Temperatura
        cardTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Temperatura.class);
                startActivity(intent);
            }
        });
    }
}
