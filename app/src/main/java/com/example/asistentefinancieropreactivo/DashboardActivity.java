package com.example.asistentefinancieropreactivo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerCategorias;
    private CategoriaAdapter adaptador;
    private LogicaFinanciera logica;
    private Button btnRegistrar, btnNuevaCategoria, btnSimularMes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerCategorias = findViewById(R.id.recyclerCategorias);
        recyclerCategorias.setLayoutManager(new LinearLayoutManager(this));

        logica = new LogicaFinanciera(this);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnNuevaCategoria = findViewById(R.id.btnNuevaCategoria);
        btnSimularMes = findViewById(R.id.btnSimularMes);

        // Botón GIGANTE: Transacciones
        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, RegistrarTransaccionActivity.class);
            startActivity(intent);
        });

        // Botón PEQUEÑO AZUL: Nueva categoría
        btnNuevaCategoria.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AgregarCategoriaActivity.class);
            startActivity(intent);
        });

        // BOTÓN ROJO: Simular el salto de mes
        btnSimularMes.setOnClickListener(v -> {
            double sobrantes = logica.procesarCambioDeMes();
            mostrarAlertaFinDeMes(sobrantes);
        });
    }

    // EL POP-UP DEL DÍA 1 (Diseño basado en Figma)
    private void mostrarAlertaFinDeMes(double sobrante) {
        new AlertDialog.Builder(this)
                .setTitle("🎉 ¡Mes completado!")
                .setMessage("¡Impresionante! Te sobraron $" + sobrante + " el mes pasado.\n\nVe a tu banca móvil real, transfiérelos a tu cuenta de Ahorros para ponerlos a salvo.")
                .setPositiveButton("Sí, ya lo transferí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Recarga la lista para mostrar que el futuro pasó al presente
                        onResume();
                    }
                })
                .setCancelable(false) // Obliga al usuario a presionar el botón
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Categoria> listaGastosReales = logica.obtenerCategorias();
        adaptador = new CategoriaAdapter(listaGastosReales);
        recyclerCategorias.setAdapter(adaptador);
    }
}