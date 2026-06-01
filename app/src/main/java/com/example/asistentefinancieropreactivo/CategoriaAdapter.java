package com.example.asistentefinancieropreactivo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    // Aquí guardamos la lista de gastos reales que viene de la base de datos
    private List<Categoria> listaCategorias;

    public CategoriaAdapter(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // El "obrero" toma el molde XML que creaste en el Paso 1
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        // El "obrero" rellena el molde con la información de un gasto específico
        Categoria categoria = listaCategorias.get(position);

        holder.txtNombreCategoria.setText(categoria.getNombre());
        holder.txtValoresCategoria.setText("Disponible: $" + categoria.getDisponiblePresente() + " / $" + categoria.getMetaMensual());

        // Calcular el porcentaje para que la barra naranja se mueva de verdad
        int progreso = 0;
        if (categoria.getMetaMensual() > 0) {
            progreso = (int) ((categoria.getDisponiblePresente() / categoria.getMetaMensual()) * 100);
        }
        holder.barraProgresoCategoria.setProgress(progreso);

        // Mostrar de qué cuenta sale el dinero
        holder.txtCuentaAsociada.setText("Origen: Banco ID " + categoria.getIdCuentaAsociada());
    }

    @Override
    public int getItemCount() {
        // Le dice al obrero cuántas tarjetas tiene que fabricar en total
        return listaCategorias.size();
    }

    // Esta clase interna sirve para "conectar" los elementos del XML con Java
    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreCategoria, txtValoresCategoria, txtCuentaAsociada;
        ProgressBar barraProgresoCategoria;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreCategoria = itemView.findViewById(R.id.txtNombreCategoria);
            txtValoresCategoria = itemView.findViewById(R.id.txtValoresCategoria);
            txtCuentaAsociada = itemView.findViewById(R.id.txtCuentaAsociada);
            barraProgresoCategoria = itemView.findViewById(R.id.barraProgresoCategoria);
        }
    }
}