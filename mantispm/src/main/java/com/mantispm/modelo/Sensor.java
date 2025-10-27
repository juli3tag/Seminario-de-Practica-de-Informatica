package com.mantispm.modelo;

public class Sensor {
    private int sensorId;
    private String uidSensor;
    private String tipoSensor;
    private boolean activo;

    public Sensor(int sensorId, String uidSensor, String tipoSensor, boolean activo) {
        this.sensorId = sensorId;
        this.uidSensor = uidSensor;
        this.tipoSensor = tipoSensor;
        this.activo = activo;
    }

    // Getters y setters
    public int getSensorId() { return sensorId; }
    public void setSensorId(int sensorId) { this.sensorId = sensorId; }
    public String getUidSensor() { return uidSensor; }
    public void setUidSensor(String uidSensor) { this.uidSensor = uidSensor; }
    public String getTipoSensor() { return tipoSensor; }
    public void setTipoSensor(String tipoSensor) { this.tipoSensor = tipoSensor; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
