package com.example.asistentefinancieropreactivo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLite extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "FinanzasPreactivas.db";
    private static final int VERSION_BD = 1;

    public ConexionSQLite(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creamos las tablas nativas para el celular
        db.execSQL("CREATE TABLE IF NOT EXISTS Cuentas_Bancarias (id_cuenta INTEGER PRIMARY KEY, nombre_banco TEXT, saldo_real REAL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Categorias_Gasto (id_categoria INTEGER PRIMARY KEY, nombre TEXT, meta_mensual_requerida REAL, acumulado_futuro REAL, disponible_presente REAL, id_cuenta_asociada INTEGER)");

        // Cuentas básicas de tu onboarding
        db.execSQL("INSERT INTO Cuentas_Bancarias (id_cuenta, nombre_banco, saldo_real) VALUES (1, 'Banco A (Uso Diario)', 0)");
        db.execSQL("INSERT INTO Cuentas_Bancarias (id_cuenta, nombre_banco, saldo_real) VALUES (2, 'Banco B (Ahorros)', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Cuentas_Bancarias");
        db.execSQL("DROP TABLE IF EXISTS Categorias_Gasto");
        onCreate(db);
    }
}