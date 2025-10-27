package com.mantispm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.mantispm.modelo.OrdenTrabajo;

public class OrdenTrabajoDAO {

    public void insertarOT(OrdenTrabajo ot) throws SQLException {
        String sql = "INSERT INTO orden_trabajo(alerta_id, equipo_id, prioridad, estado, descripcion, tecnico_asignado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, ot.getAlertaId(), Types.BIGINT);
            ps.setLong(2, ot.getEquipoId());
            ps.setString(3, ot.getPrioridad());
            ps.setString(4, ot.getEstado());
            ps.setString(5, ot.getDescripcion());
            ps.setString(6, ot.getTecnicoAsignado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) ot.setOtId(rs.getLong(1));
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar operación en DAO: " + e.getMessage());
        }
    }

    public OrdenTrabajo obtenerPorId(long otId) throws SQLException {
        String sql = "SELECT * FROM orden_trabajo WHERE ot_id = ?";
        try (Connection conn = FuenteDatos.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, otId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearOT(rs);
            }
        }
        return null;
    }

    private OrdenTrabajo mapearOT(ResultSet rs) throws SQLException {
        OrdenTrabajo ot = new OrdenTrabajo();
        ot.setOtId(rs.getLong("ot_id"));
        ot.setAlertaId(rs.getObject("alerta_id") != null ? rs.getLong("alerta_id") : null);
        ot.setEquipoId(rs.getLong("equipo_id"));
        ot.setPrioridad(rs.getString("prioridad"));
        ot.setEstado(rs.getString("estado"));
        ot.setDescripcion(rs.getString("descripcion"));
        ot.setTecnicoAsignado(rs.getString("tecnico_asignado"));
        ot.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        ot.setFechaCierre(rs.getTimestamp("fecha_cierre"));
        return ot;
    }
}
