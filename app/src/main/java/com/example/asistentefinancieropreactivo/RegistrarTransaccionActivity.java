package com.example.asistentefinancieropreactivo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrarTransaccionActivity extends AppCompatActivity {

    private EditText txtMonto, txtIdCategoria;
    private RadioButton radioGasto, radioIngreso;
    private Button btnGuardar;
    private LogicaFinanciera logica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_transaccion);

        // Inicializamos el cerebro de la app
        logica = new LogicaFinanciera(this);

        // Enlazamos con el diseño visual (XML)
        txtMonto = findViewById(R.id.txtMontoTransaccion);
        txtIdCategoria = findViewById(R.id.txtIdCategoriaTransaccion);
        radioGasto = findViewById(R.id.radioGasto);
        radioIngreso = findViewById(R.id.radioIngreso);
        btnGuardar = findViewById(R.id.btnGuardarTransaccion);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double monto = Double.parseDouble(txtMonto.getText().toString());
                    int idCategoria = Integer.parseInt(txtIdCategoria.getText().toString());

                    // Verificamos qué botón circular está seleccionado
                    if (radioGasto.isChecked()) {
                        logica.registrarGasto(monto, idCategoria);
                        Toast.makeText(RegistrarTransaccionActivity.this, "Gasto registrado (-$" + monto + ")", Toast.LENGTH_SHORT).show();
                    } else if (radioIngreso.isChecked()) {
                        logica.registrarIngresoDiario(monto, idCategoria);
                        Toast.makeText(RegistrarTransaccionActivity.this, "Ingreso registrado (+$" + monto + ")", Toast.LENGTH_SHORT).show();
                    }

                    // Cierra esta ventana y regresa al Tablero Principal automáticamente
                    finish();
                } catch (Exception e) {
                    Toast.makeText(RegistrarTransaccionActivity.this, "Por favor, llena los campos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}