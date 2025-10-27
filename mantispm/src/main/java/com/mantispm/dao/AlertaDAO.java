package com.mantispm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mantispm.modelo.Alerta;

public class AlertaDAO {

    public void insertarAlerta(Alerta alerta) throws SQLException {
        String sql = "INSERT INTO alerta(lectura_id, sensor_id, equipo_id, tipo_alerta, severidad, mensaje, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, alerta.getLecturaId(), Types.BIGINT);
            ps.setObject(2, alerta.getSensorId(), Types.BIGINT);
            ps.setObject(3, alerta.getEquipoId(), Types.BIGINT);
            ps.setString(4, alerta.getTipoAlerta());
            ps.setString(5, alerta.getSeveridad());
            ps.setString(6, alerta.getMensaje());
            ps.setString(7, alerta.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    alerta.setAlertaId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
        System.err.println("Error al ejecutar operación en DAO: " + e.getMessage());
        }
    }

    public Alerta obtenerPorId(long alertaId) throws SQLException {
        String sql = "SELECT * FROM alerta WHERE alerta_id = ?";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, alertaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearAlerta(rs);
            }
        }
        return null;
    }

    public List<Alerta> listarAlertas() throws SQLException {
        List<Alerta> alertas = new ArrayList<>();
        String sql = "SELECT * FROM alerta";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) alertas.add(mapearAlerta(rs));
        }
        return alertas;
    }

    private Alerta mapearAlerta(ResultSet rs) throws SQLException {
        Alerta a = new Alerta();
        a.setAlertaId(rs.getLong("alerta_id"));
        a.setLecturaId(rs.getObject("lectura_id") != null ? rs.getLong("lectura_id") : null);
        a.setSensorId(rs.getObject("sensor_id") != null ? rs.getLong("sensor_id") : null);
        a.setEquipoId(rs.getObject("equipo_id") != null ? rs.getLong("equipo_id") : null);
        a.setTipoAlerta(rs.getString("tipo_alerta"));
        a.setSeveridad(rs.getString("severidad"));
        a.setMensaje(rs.getString("mensaje"));
        a.setEstado(rs.getString("estado"));
        a.setCreadaTs(rs.getTimestamp("creada_ts"));
        return a;
    }

    public void actualizarAlerta(Alerta alerta) throws SQLException {
        String sql = "UPDATE alerta SET lectura_id=?, sensor_id=?, equipo_id=?, tipo_alerta=?, severidad=?, mensaje=?, estado=? WHERE alerta_id=?";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, alerta.getLecturaId(), Types.BIGINT);
            ps.setObject(2, alerta.getSensorId(), Types.BIGINT);
            ps.setObject(3, alerta.getEquipoId(), Types.BIGINT);
            ps.setString(4, alerta.getTipoAlerta());
            ps.setString(5, alerta.getSeveridad());
            ps.setString(6, alerta.getMensaje());
            ps.setString(7, alerta.getEstado());
            ps.setLong(8, alerta.getAlertaId());
            ps.executeUpdate();
        }
    }
}
