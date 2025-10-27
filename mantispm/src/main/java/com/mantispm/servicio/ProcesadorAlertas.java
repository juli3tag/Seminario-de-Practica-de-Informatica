package com.mantispm.servicio;

import com.mantispm.dao.LecturaDAO;
import com.mantispm.dao.AlertaDAO;
import com.mantispm.modelo.Lectura;
import com.mantispm.modelo.Alerta;

import java.time.LocalDateTime;
import java.util.List;

public class ProcesadorAlertas {

    private LecturaDAO lecturaDAO = new LecturaDAO();
    private AlertaDAO alertaDAO = new AlertaDAO();

    public void generarAlertas(double umbral) {
        List<Lectura> lecturas = lecturaDAO.obtenerLecturasNoProcesadas();
        for (Lectura l : lecturas) {
            if (l.getValor() > umbral) {
                Alerta alerta = new Alerta(0,
                        "Valor crítico: " + l.getValor(),
                        "ALTA",
                        l.getLecturaId(),
                        false,
                        LocalDateTime.now());
                alertaDAO.insertarAlerta(alerta);
            }
            lecturaDAO.marcarProcesada(l.getLecturaId());
        }
    }
}
