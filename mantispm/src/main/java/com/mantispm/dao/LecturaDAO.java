package com.mantispm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mantispm.modelo.Lectura;

public class LecturaDAO {

    public void insertarLectura(Lectura lectura) throws SQLException {
        String sql = "INSERT INTO lectura_sensor(lectura_uid, sensor_id, valor, unidad, registrado_en, payload_raw, procesada) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, lectura.getLecturaUid());
            ps.setLong(2, lectura.getSensorId());
            ps.setBigDecimal(3, lectura.getValor());
            ps.setString(4, lectura.getUnidad());
            ps.setTimestamp(5, lectura.getRegistradoEn());
            ps.setString(6, lectura.getPayloadRaw());
            ps.setBoolean(7, lectura.isProcesada());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) lectura.setLecturaId(rs.getLong(1));
            }
        } catch (SQLException e) {
        System.err.println("Error al ejecutar operación en DAO: " + e.getMessage());
        }
    }

    public Lectura obtenerPorId(long lecturaId) throws SQLException {
        String sql = "SELECT * FROM lectura_sensor WHERE lectura_id = ?";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, lecturaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearLectura(rs);
            }
        }
        return null;
    }

    private Lectura mapearLectura(ResultSet rs) throws SQLException {
        Lectura l = new Lectura();
        l.setLecturaId(rs.getLong("lectura_id"));
        l.setLecturaUid(rs.getString("lectura_uid"));
        l.setSensorId(rs.getLong("sensor_id"));
        l.setValor(rs.getBigDecimal("valor"));
        l.setUnidad(rs.getString("unidad"));
        l.setRegistradoEn(rs.getTimestamp("registrado_en"));
        l.setProcesada(rs.getBoolean("procesada"));
        l.setPayloadRaw(rs.getString("payload_raw"));
        l.setIngestionTs(rs.getTimestamp("ingestion_ts"));
        return l;
    }

    public List<Lectura> listarLecturas() throws SQLException {
        List<Lectura> lecturas = new ArrayList<>();
        String sql = "SELECT * FROM lectura_sensor";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lecturas.add(mapearLectura(rs));
        }
        return lecturas;
    }
}
