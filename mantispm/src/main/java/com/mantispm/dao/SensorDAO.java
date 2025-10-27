package com.mantispm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mantispm.modelo.Sensor;

public class SensorDAO {

    public void insertarSensor(Sensor sensor) throws SQLException {
        String sql = "INSERT INTO sensor(equipo_id, uid_sensor, tipo_sensor, descripcion, registrado_en, activo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, sensor.getEquipoId());
            ps.setString(2, sensor.getUidSensor());
            ps.setString(3, sensor.getTipoSensor());
            ps.setString(4, sensor.getDescripcion());
            ps.setTimestamp(5, sensor.getRegistradoEn());
            ps.setBoolean(6, sensor.isActivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) sensor.setSensorId(rs.getLong(1));
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar operación en DAO: " + e.getMessage());
        }
    }

    public Sensor obtenerPorId(long sensorId) throws SQLException {
        String sql = "SELECT * FROM sensor WHERE sensor_id = ?";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, sensorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearSensor(rs);
            }
        }
        return null;
    }

    private Sensor mapearSensor(ResultSet rs) throws SQLException {
        Sensor s = new Sensor();
        s.setSensorId(rs.getLong("sensor_id"));
        s.setEquipoId(rs.getLong("equipo_id"));
        s.setUidSensor(rs.getString("uid_sensor"));
        s.setTipoSensor(rs.getString("tipo_sensor"));
        s.setDescripcion(rs.getString("descripcion"));
        s.setRegistradoEn(rs.getTimestamp("registrado_en"));
        s.setActivo(rs.getBoolean("activo"));
        s.setCreadoTs(rs.getTimestamp("creado_ts"));
        return s;
    }

    public List<Sensor> listarSensores() throws SQLException {
        List<Sensor> sensores = new ArrayList<>();
        String sql = "SELECT * FROM sensor";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) sensores.add(mapearSensor(rs));
        }
        return sensores;
    }
}
