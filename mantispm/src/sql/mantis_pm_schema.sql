-- ============================================================
-- Gonzalez Julieta - mantis_pm_schema.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS mantis_pm
  CHARACTER SET = 'utf8mb4'
  COLLATE = 'utf8mb4_unicode_ci';

USE mantis_pm;

-- 1) Tablas maestras: empresa, planta, equipo, sensor
CREATE TABLE IF NOT EXISTS empresa (
  empresa_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(200) NOT NULL,
  cuit VARCHAR(20) DEFAULT NULL,
  direccion VARCHAR(300) DEFAULT NULL,
  creado_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS planta (
  planta_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  empresa_id BIGINT NOT NULL,
  nombre VARCHAR(200) NOT NULL,
  ubicacion VARCHAR(300) DEFAULT NULL,
  creado_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_planta_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(empresa_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS equipo (
  equipo_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  planta_id BIGINT NOT NULL,
  nombre VARCHAR(200) NOT NULL,
  codigo_equipamiento VARCHAR(100) DEFAULT NULL,
  tipo VARCHAR(80) DEFAULT NULL,
  creado_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_equipo_planta FOREIGN KEY (planta_id) REFERENCES planta(planta_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS sensor (
  sensor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  equipo_id BIGINT NOT NULL,
  uid_sensor VARCHAR(100) NOT NULL UNIQUE,
  tipo_sensor VARCHAR(50) DEFAULT NULL,
  descripcion VARCHAR(300) DEFAULT NULL,
  registrado_en DATETIME DEFAULT NULL,
  activo BOOLEAN DEFAULT TRUE,
  creado_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_sensor_equipo FOREIGN KEY (equipo_id) REFERENCES equipo(equipo_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 2) Roles y usuarios
CREATE TABLE IF NOT EXISTS rol (
  rol_id TINYINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE,
  descripcion VARCHAR(200) DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS usuario (
  usuario_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(200) NOT NULL,
  email VARCHAR(200) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  rol_id TINYINT NOT NULL,
  creado_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES rol(rol_id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- 3) Lecturas de sensores (serie temporal)
CREATE TABLE IF NOT EXISTS lectura_sensor (
  lectura_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  lectura_uid VARCHAR(100) NOT NULL,
  sensor_id BIGINT NOT NULL,
  valor DECIMAL(18,6) DEFAULT NULL,
  unidad VARCHAR(30) DEFAULT NULL,
  ingestion_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  registrado_en DATETIME DEFAULT NULL,
  payload_raw JSON DEFAULT NULL,
  procesada BOOLEAN DEFAULT FALSE,
  creado_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT ux_lectura_uid UNIQUE (lectura_uid),
  INDEX idx_sensor_registrado (sensor_id, registrado_en),
  CONSTRAINT fk_lectura_sensor FOREIGN KEY (sensor_id) REFERENCES sensor(sensor_id) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

-- 4) Alertas
CREATE TABLE IF NOT EXISTS alerta (
  alerta_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  lectura_id BIGINT DEFAULT NULL,
  sensor_id BIGINT DEFAULT NULL,
  equipo_id BIGINT DEFAULT NULL,
  tipo_alerta VARCHAR(100) DEFAULT NULL,
  severidad ENUM('BAJA','MEDIA','ALTA','CRITICA') DEFAULT 'MEDIA',
  mensaje VARCHAR(500) DEFAULT NULL,
  creada_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  estado ENUM('PENDIENTE','NOTIFICADA','RESUELTA') DEFAULT 'PENDIENTE',
  CONSTRAINT fk_alerta_lectura FOREIGN KEY (lectura_id) REFERENCES lectura_sensor(lectura_id) ON DELETE SET NULL,
  CONSTRAINT fk_alerta_sensor FOREIGN KEY (sensor_id) REFERENCES sensor(sensor_id) ON DELETE SET NULL,
  CONSTRAINT fk_alerta_equipo FOREIGN KEY (equipo_id) REFERENCES equipo(equipo_id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- 5) Ordenes de trabajo e intervenciones
CREATE TABLE IF NOT EXISTS orden_trabajo (
  ot_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  alerta_id BIGINT DEFAULT NULL,
  equipo_id BIGINT NOT NULL,
  prioridad ENUM('BAJA','MEDIA','ALTA','URGENTE') DEFAULT 'MEDIA',
  estado ENUM('PENDIENTE','EN_PROCESO','FINALIZADA','CANCELADA') DEFAULT 'PENDIENTE',
  descripcion TEXT DEFAULT NULL,
  tecnico_asignado VARCHAR(200) DEFAULT NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_cierre TIMESTAMP NULL,
  CONSTRAINT fk_ot_alerta FOREIGN KEY (alerta_id) REFERENCES alerta(alerta_id) ON DELETE SET NULL,
  CONSTRAINT fk_ot_equipo FOREIGN KEY (equipo_id) REFERENCES equipo(equipo_id) ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS intervencion (
  intervencion_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  ot_id BIGINT NOT NULL,
  usuario_id BIGINT DEFAULT NULL,
  fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_fin TIMESTAMP NULL,
  observaciones TEXT DEFAULT NULL,
  CONSTRAINT fk_intervencion_ot FOREIGN KEY (ot_id) REFERENCES orden_trabajo(ot_id) ON DELETE CASCADE,
  CONSTRAINT fk_intervencion_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Indices adicionales
CREATE INDEX idx_lectura_sensor_ing_ts ON lectura_sensor(sensor_id, ingestion_ts);
