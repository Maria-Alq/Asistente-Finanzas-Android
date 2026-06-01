package com.example.asistentefinancieropreactivo;

public class Categoria {
    private int idCategoria;
    private String nombre;
    private double metaMensual;
    private double acumuladoFuturo;
    private double disponiblePresente;
    private int idCuentaAsociada;

    // Constructor (Para armar el objeto)
    public Categoria(int idCategoria, String nombre, double metaMensual, double acumuladoFuturo, double disponiblePresente, int idCuentaAsociada) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.metaMensual = metaMensual;
        this.acumuladoFuturo = acumuladoFuturo;
        this.disponiblePresente = disponiblePresente;
        this.idCuentaAsociada = idCuentaAsociada;
    }

    // "Getters" para poder leer los datos desde otras pantallas
    public int getIdCategoria() { return idCategoria; }
    public String getNombre() { return nombre; }
    public double getMetaMensual() { return metaMensual; }
    public double getAcumuladoFuturo() { return acumuladoFuturo; }
    public double getDisponiblePresente() { return disponiblePresente; }
    public int getIdCuentaAsociada() { return idCuentaAsociada; }
}