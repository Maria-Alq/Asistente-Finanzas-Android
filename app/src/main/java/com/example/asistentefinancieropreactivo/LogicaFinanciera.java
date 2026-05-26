package com.example.asistentefinancieropreactivo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class LogicaFinanciera {

    private ConexionSQLite conexion;

    public LogicaFinanciera(Context context) {
        // En Android, el cerebro necesita saber en qué pantalla está (Context)
        conexion = new ConexionSQLite(context);
    }

    public void configurarOnboardingInicial(double saldoBancoA, double saldoBancoB) {
        SQLiteDatabase db = conexion.getWritableDatabase();

        // Guardar saldo del Banco A
        ContentValues valoresA = new ContentValues();
        valoresA.put("saldo_real", saldoBancoA);
        db.update("Cuentas_Bancarias", valoresA, "id_cuenta = ?", new String[]{"1"});

        // Guardar saldo del Banco B
        ContentValues valoresB = new ContentValues();
        valoresB.put("saldo_real", saldoBancoB);
        db.update("Cuentas_Bancarias", valoresB, "id_cuenta = ?", new String[]{"2"});

        db.close();
    }
}