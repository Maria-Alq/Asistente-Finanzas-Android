package com.example.asistentefinancieropreactivo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class LogicaFinanciera {

    private ConexionSQLite conexion;

    public LogicaFinanciera(Context context) {
        // En Android, el cerebro necesita saber en qué pantalla está (Context)
        conexion = new ConexionSQLite(context);
    }

    public void configurarOnboardingInicial(double saldoBancoA, double saldoBancoB) {
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valoresA = new ContentValues();
        valoresA.put("saldo_real", saldoBancoA);
        db.update("Cuentas_Bancarias", valoresA, "id_cuenta = ?", new String[]{"1"});

        ContentValues valoresB = new ContentValues();
        valoresB.put("saldo_real", saldoBancoB);
        db.update("Cuentas_Bancarias", valoresB, "id_cuenta = ?", new String[]{"2"});

        db.close();
    }

    // NUEVO MÉTODO: Leer los gastos reales de SQLite
    public List<Categoria> obtenerCategorias() {
        List<Categoria> lista = new ArrayList<>();
        SQLiteDatabase db = conexion.getReadableDatabase();

        // El "Cursor" es como un dedo que va apuntando fila por fila en tu base de datos
        Cursor cursor = db.rawQuery("SELECT * FROM Categorias_Gasto", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                double meta = cursor.getDouble(2);
                double futuro = cursor.getDouble(3);
                double presente = cursor.getDouble(4);
                int idCuenta = cursor.getInt(5);

                // Armamos la categoría y la agregamos a la lista
                lista.add(new Categoria(id, nombre, meta, futuro, presente, idCuenta));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    // NUEVO MÉTODO: Crear una nueva categoría de gasto desde cero
    public void agregarNuevaCategoria(String nombre, double metaMensual, int idCuenta) {
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("meta_mensual_requerida", metaMensual);
        valores.put("acumulado_futuro", 0.0); // Inicia en 0
        valores.put("disponible_presente", 0.0); // Inicia en 0
        valores.put("id_cuenta_asociada", idCuenta);

        db.insert("Categorias_Gasto", null, valores);
        db.close();
    }

    // NUEVO MÉTODO: Registrar un Gasto (Resta dinero del mes presente)
    public void registrarGasto(double monto, int idCategoria) {
        SQLiteDatabase db = conexion.getWritableDatabase();
        // Usamos execSQL para hacer la resta matemática directamente en la base de datos
        db.execSQL("UPDATE Categorias_Gasto SET disponible_presente = disponible_presente - " + monto + " WHERE id_categoria = " + idCategoria);
        db.close();
    }

    // NUEVO MÉTODO: Registrar un Ingreso (Aplica el Modo Contingencia)
    public void registrarIngresoDiario(double monto, int idCategoria) {
        SQLiteDatabase db = conexion.getWritableDatabase();

        // Primero, revisamos cómo está el dinero en el presente
        android.database.Cursor cursor = db.rawQuery("SELECT disponible_presente FROM Categorias_Gasto WHERE id_categoria = " + idCategoria, null);

        if (cursor.moveToFirst()) {
            double disponiblePresente = cursor.getDouble(0);

            // MODO CONTINGENCIA: Si el presente está en 0 o negativo, el ingreso salva el día de hoy
            if (disponiblePresente <= 0) {
                db.execSQL("UPDATE Categorias_Gasto SET disponible_presente = disponible_presente + " + monto + " WHERE id_categoria = " + idCategoria);
            } else {
                // MODO ACUMULADOR: Si el presente está bien, el dinero se congela para asegurar el mes futuro
                db.execSQL("UPDATE Categorias_Gasto SET acumulado_futuro = acumulado_futuro + " + monto + " WHERE id_categoria = " + idCategoria);
            }
        }
        cursor.close();
        db.close();
    }

    // NUEVO MÉTODO: Jefe Final - Procesar el salto de mes
    public double procesarCambioDeMes() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        double totalSobrante = 0;

        // 1. Sumar todo el dinero que no te gastaste en el mes presente (Los sobrantes)
        android.database.Cursor cursor = db.rawQuery("SELECT disponible_presente FROM Categorias_Gasto WHERE disponible_presente > 0", null);
        if (cursor.moveToFirst()) {
            do {
                totalSobrante += cursor.getDouble(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // 2. Hacer el salto en el tiempo: El dinero congelado del futuro se vuelve el dinero de hoy
        db.execSQL("UPDATE Categorias_Gasto SET disponible_presente = acumulado_futuro");

        // 3. Reiniciar el futuro a $0 para empezar a ahorrar de nuevo
        db.execSQL("UPDATE Categorias_Gasto SET acumulado_futuro = 0");

        db.close();

        // Devolvemos la cantidad que sobró para mostrarla en la alerta
        return totalSobrante;
    }
}