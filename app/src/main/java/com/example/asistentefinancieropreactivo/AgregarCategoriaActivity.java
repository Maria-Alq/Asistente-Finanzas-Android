package com.example.asistentefinancieropreactivo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AgregarCategoriaActivity extends AppCompatActivity {

    private EditText txtNombreGasto, txtMetaMensual, txtIdCuenta;
    private Button btnGuardarCategoria;
    private LogicaFinanciera logica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categoria);

        logica = new LogicaFinanciera(this);

        txtNombreGasto = findViewById(R.id.txtNombreGasto);
        txtMetaMensual = findViewById(R.id.txtMetaMensual);
        txtIdCuenta = findViewById(R.id.txtIdCuenta);
        btnGuardarCategoria = findViewById(R.id.btnGuardarCategoria);

        btnGuardarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nombre = txtNombreGasto.getText().toString();
                    double meta = Double.parseDouble(txtMetaMensual.getText().toString());
                    int idCuenta = Integer.parseInt(txtIdCuenta.getText().toString());

                    // Mandar a guardar a la base de datos
                    logica.agregarNuevaCategoria(nombre, meta, idCuenta);

                    Toast.makeText(AgregarCategoriaActivity.this, "¡Categoría creada!", Toast.LENGTH_SHORT).show();

                    // Cerrar esta pantalla para volver al Tablero
                    finish();
                } catch (Exception e) {
                    Toast.makeText(AgregarCategoriaActivity.this, "Por favor llena todos los datos con números válidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}