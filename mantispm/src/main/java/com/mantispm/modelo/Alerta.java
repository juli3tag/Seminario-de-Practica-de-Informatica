package com.mantispm.modelo;

import java.time.LocalDateTime;

public class Alerta {
    private int alertaId;
    private String mensaje;
    private String severidad;
    private int lecturaId;
    private boolean resuelta;
    private LocalDateTime timestamp;

    public Alerta(int alertaId, String mensaje, String severidad, int lecturaId, boolean resuelta, LocalDateTime timestamp) {
        this.alertaId = alertaId;
        this.mensaje = mensaje;
        this.severidad = severidad;
        this.lecturaId = lecturaId;
        this.resuelta = resuelta;
        this.timestamp = timestamp;
    }

    // Getters y setters
    public int getAlertaId() { return alertaId; }
    public void setAlertaId(int alertaId) { this.alertaId = alertaId; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getSeveridad() { return severidad; }
    public void setSeveridad(String severidad) { this.severidad = severidad; }
    public int getLecturaId() { return lecturaId; }
    public void setLecturaId(int lecturaId) { this.lecturaId = lecturaId; }
    public boolean isResuelta() { return resuelta; }
    public void setResuelta(boolean resuelta) { this.resuelta = resuelta; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
