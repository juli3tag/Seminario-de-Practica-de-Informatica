package com.mantispm.modelo;

import java.time.LocalDateTime;

public class Lectura {
    private int lecturaId;
    private String uidSensor;
    private double valor;
    private String unidad;
    private LocalDateTime timestamp;
    private boolean procesada;

    public Lectura(int lecturaId, String uidSensor, double valor, String unidad, LocalDateTime timestamp, boolean procesada) {
        this.lecturaId = lecturaId;
        this.uidSensor = uidSensor;
        this.valor = valor;
        this.unidad = unidad;
        this.timestamp = timestamp;
        this.procesada = procesada;
    }

    // Getters y setters
    public int getLecturaId() { return lecturaId; }
    public void setLecturaId(int lecturaId) { this.lecturaId = lecturaId; }
    public String getUidSensor() { return uidSensor; }
    public void setUidSensor(String uidSensor) { this.uidSensor = uidSensor; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public boolean isProcesada() { return procesada; }
    public void setProcesada(boolean procesada) { this.procesada = procesada; }
}