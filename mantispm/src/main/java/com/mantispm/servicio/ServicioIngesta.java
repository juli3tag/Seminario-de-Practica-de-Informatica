package com.mantispm.servicio;

import com.mantispm.dao.LecturaDAO;
import com.mantispm.modelo.Lectura;

public class ServicioIngesta {
    try {
        lecturaDAO.insertarLectura(lectura);
    } catch (SQLException e) {
        System.err.println("No se pudo procesar la lectura: " + e.getMessage());
    }
}
