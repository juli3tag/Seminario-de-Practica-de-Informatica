package com.mantispm.modelo;

import java.time.LocalDateTime;

public class OrdenTrabajo {
    private int ordenId;
    private String descripcion;
    private String prioridad;
    private int alertaId;
    private boolean completada;
    private LocalDateTime fechaCreacion;

    public OrdenTrabajo(int ordenId, String descripcion, String prioridad, int alertaId, boolean completada, LocalDateTime fechaCreacion) {
        this.ordenId = ordenId;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.alertaId = alertaId;
        this.completada = completada;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters
    public int getOrdenId() { return ordenId; }
    public void setOrdenId(int ordenId) { this.ordenId = ordenId; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public int getAlertaId() { return alertaId; }
    public void setAlertaId(int alertaId) { this.alertaId = alertaId; }
    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
