package com.example.asistentefinancieropreactivo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText txtBancoA, txtBancoB, txtEfectivo;
    private Button btnIniciar;
    private LogicaFinanciera logica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el cerebro (Pasa "this" para que sepa que está en esta pantalla)
        logica = new LogicaFinanciera(this);

        txtBancoA = findViewById(R.id.txtBancoA);
        txtBancoB = findViewById(R.id.txtBancoB);
        txtEfectivo = findViewById(R.id.txtEfectivo);
        btnIniciar = findViewById(R.id.btnIniciar);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Si el usuario deja vacío, asume 0 para no romper la app
                    String textoA = txtBancoA.getText().toString();
                    String textoB = txtBancoB.getText().toString();

                    double saldoA = textoA.isEmpty() ? 0 : Double.parseDouble(textoA);
                    double saldoB = textoB.isEmpty() ? 0 : Double.parseDouble(textoB);

                    logica.configurarOnboardingInicial(saldoA, saldoB);

                    Toast.makeText(MainActivity.this, "¡Saldos iniciales guardados con éxito!", Toast.LENGTH_LONG).show();
                    btnIniciar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                String textoA = txtBancoA.getText().toString();
                                String textoB = txtBancoB.getText().toString();

                                double saldoA = textoA.isEmpty() ? 0 : Double.parseDouble(textoA);
                                double saldoB = textoB.isEmpty() ? 0 : Double.parseDouble(textoB);

                                logica.configurarOnboardingInicial(saldoA, saldoB);

                                Toast.makeText(MainActivity.this, "¡Saldos iniciales guardados con éxito!", Toast.LENGTH_SHORT).show();

                                // NUEVO: Código para viajar a la pantalla del Tablero
                                android.content.Intent intencion = new android.content.Intent(MainActivity.this, DashboardActivity.class);
                                startActivity(intencion);
                                finish(); // Cierra la pantalla de Onboarding para que el usuario no pueda regresar a ella

                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Ocurrió un error al guardar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Ocurrió un error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}