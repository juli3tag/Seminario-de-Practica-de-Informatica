package com.mantispm;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.mantispm.modelo.Lectura;
import com.mantispm.servicio.ProcesadorAlertas;
import com.mantispm.servicio.ServicioIngesta;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServicioIngesta servicio = new ServicioIngesta();
        ProcesadorAlertas procesador = new ProcesadorAlertas();

        boolean salir = false;
        while (!salir) {
            System.out.println("=== MENÚ MANTIS-PM ===");
            System.out.println("1. Insertar lectura");
            System.out.println("2. Procesar alertas");
            System.out.println("3. Salir");
            System.out.print("Seleccione opción: ");

            int opcion = 0;
            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                sc.nextLine(); // limpiar buffer
                continue;
            }
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("UID Sensor: ");
                        String uid = sc.nextLine().trim();
                        if (uid.isEmpty()) {
                            System.out.println("El UID del sensor no puede estar vacío.");
                            break;
                        }

                        System.out.print("Valor: ");
                        double valor;
                        try {
                            valor = sc.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Valor inválido, ingrese un número.");
                            sc.nextLine();
                            break;
                        }
                        sc.nextLine();

                        System.out.print("Unidad: ");
                        String unidad = sc.nextLine().trim();
                        if (unidad.isEmpty()) unidad = "N/A";

                        Lectura lectura = new Lectura(0, uid, valor, unidad, LocalDateTime.now(), false);
                        servicio.procesarNuevaLectura(lectura);
                        System.out.println("Lectura insertada correctamente.\n");

                    } catch (SQLException e) {
                        System.out.println("Error al insertar la lectura: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Ingrese umbral crítico: ");
                        double umbral;
                        try {
                            umbral = sc.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Umbral inválido, ingrese un número.");
                            sc.nextLine();
                            break;
                        }
                        sc.nextLine();

                        procesador.generarAlertas(umbral);
                        System.out.println("Alertas procesadas correctamente.\n");

                    } catch (SQLException e) {
                        System.out.println("Error al procesar alertas: " + e.getMessage());
                    }
                    break;

                case 3:
                    salir = true;
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción inválida.\n");
            }
        }

        sc.close();
    }
}
